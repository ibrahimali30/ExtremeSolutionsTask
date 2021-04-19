package com.ibrahim.extremesolutionstask.marvel.presentation.view.activity

import android.os.Bundle
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
import com.ibrahim.extremesolutionstask.marvel.presentation.view.adapter.ForecastAdapter
import com.ibrahim.extremesolutionstask.marvel.presentation.view.extensions.closeSearch
import com.ibrahim.extremesolutionstask.marvel.presentation.view.extensions.openSearch
import com.ibrahim.extremesolutionstask.marvel.presentation.view.extensions.setOnTextChanged
import com.ibrahim.extremesolutionstask.marvel.presentation.viewmodel.CharactersSharedViewModel
import com.ibrahim.extremesolutionstask.marvel.presentation.viewmodel.MarvelCharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val TAG = "Looog"

    private lateinit var adapter: ForecastAdapter
    private lateinit var concatAdapter: ConcatAdapter
    private var footerAdapter = FooterLoadingAdapter()

    @Inject
    lateinit var viewModel : MarvelCharactersViewModel

    val charactersSharedViewModel by lazy {
        ViewModelProvider(this).get(CharactersSharedViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_main)

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
        adapter = ForecastAdapter(ArrayList() , ::onScrollToEnd, ::onItemClicked)
        concatAdapter = ConcatAdapter(adapter, footerAdapter)
        rvForecast.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        rvForecast.adapter = concatAdapter
    }

    private fun initSearchView() {
        searchView.setOnTextChanged{newText ->
            charactersSharedViewModel.searchQueryLiveData.value = newText
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

//        handleLoadingVisibility(state == MarvelCharactersViewModel.ScreenState.Loading)
//        handleErrorViewsVisibility(state)

    }

    private fun handleErrorViewsVisibility(state: MarvelCharactersViewModel.ScreenState?) {
//        if (state is MarvelCharactersViewModel.ScreenState.ErrorLoadingFromApi)
//            errorViewLayout.visibility = View.VISIBLE
//        else
//            errorViewLayout.visibility = View.GONE

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