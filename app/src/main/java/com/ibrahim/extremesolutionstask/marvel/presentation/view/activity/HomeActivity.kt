package com.ibrahim.extremesolutionstask.marvel.presentation.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibrahim.extremesolutionstask.R
import com.ibrahim.extremesolutionstask.marvel.data.model.character.Character
import com.ibrahim.extremesolutionstask.marvel.presentation.view.adapter.FooterLoadingAdapter
import com.ibrahim.extremesolutionstask.marvel.presentation.view.adapter.HomeCharacterAdapter
import com.ibrahim.extremesolutionstask.marvel.presentation.view.extensions.closeSearch
import com.ibrahim.extremesolutionstask.marvel.presentation.view.extensions.openSearch
import com.ibrahim.extremesolutionstask.marvel.presentation.view.extensions.setOnTextChanged
import com.ibrahim.extremesolutionstask.marvel.presentation.viewmodel.CharactersSharedViewModel
import com.ibrahim.extremesolutionstask.marvel.presentation.viewmodel.MarvelCharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    val TAG = "Looog"

    private lateinit var adapter: HomeCharacterAdapter
    private lateinit var concatAdapter: ConcatAdapter
    private var footerAdapter = FooterLoadingAdapter()
    private val searchQueryRx by lazy { BehaviorSubject.create<String>() }

    @Inject
    lateinit var viewModel : MarvelCharactersViewModel

    private val charactersSharedViewModel by lazy {
        ViewModelProvider(this).get(CharactersSharedViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_home)

        observeScreenState()
        initSearchView()
        initRecyclerView()
        initViews()

        viewModel.getMarvelCharachters()

    }

    private fun initViews() {
        textView.setOnClickListener {
            closeSearch()
        }

        open_search_button.setOnClickListener {
            openSearch()
        }
    }

    private fun onItemClicked(character: Character, viewToTransition: View, viewToTransition2: View) {
        MarvelCharacterDetailsActivity.startCallingIntent(character, this, viewToTransition, viewToTransition2)
    }

    private fun onScrollToEnd(offSet: Int) {
        //load next page
        viewModel.getMarvelCharachters(offSet)
    }

    private fun initRecyclerView() {
        adapter = HomeCharacterAdapter(ArrayList() , ::onScrollToEnd, ::onItemClicked)
        concatAdapter = ConcatAdapter(adapter, footerAdapter)
        rvForecast.layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.VERTICAL, false)
        rvForecast.adapter = concatAdapter
    }

    @SuppressLint("CheckResult")
    private fun initSearchView() {
        searchQueryRx
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d(TAG, "initSearchView: $it")
                charactersSharedViewModel.searchQueryLiveData.postValue(it)
            }

        searchView.setOnTextChanged{newText ->
            searchQueryRx.onNext(newText)
        }
    }

    private fun observeScreenState() {
        viewModel.screenState.observe(this , Observer {
            onScreenStateChanged(it)
        })
    }

    private fun onScreenStateChanged(state: MarvelCharactersViewModel.ScreenState?) {
        when (state) {
            is MarvelCharactersViewModel.ScreenState.Loading -> handleLoadingVisibility()
            is MarvelCharactersViewModel.ScreenState.SuccessAPIResponse -> handleSuccess(state.data)
            is MarvelCharactersViewModel.ScreenState.ErrorLoadingFromApi -> handleErrorLoadingFromApi(state.error)
            else -> {}
        }
    }

    private fun handleErrorLoadingFromApi(error: Throwable) {
        footerAdapter.showError{
            viewModel.getMarvelCharachters(adapter.data.size)
        }
    }

    private fun handleSuccess(data: List<Character>) {
        adapter.setList(data)
    }

    private fun handleLoadingVisibility() {
        footerAdapter.setLoading()
    }


}