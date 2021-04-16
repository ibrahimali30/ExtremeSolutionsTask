package com.ibrahim.extremesolutionstask.marvel.presentation.view.activity

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibrahim.extremesolutionstask.R
import com.ibrahim.extremesolutionstask.marvel.data.model.character.Character
import com.ibrahim.extremesolutionstask.marvel.presentation.view.adapter.FooterLoadingAdapter
import com.ibrahim.extremesolutionstask.marvel.presentation.view.adapter.ForecastAdapter
import com.ibrahim.extremesolutionstask.marvel.presentation.view.fragment.SearchFragment
import com.ibrahim.extremesolutionstask.marvel.presentation.viewmodel.CharactersSharedViewModel
import com.ibrahim.extremesolutionstask.marvel.presentation.viewmodel.MarvelCharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList
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

    private fun openSearch() {
        searchView.setQuery("" , false)
        cardView.visibility = View.VISIBLE
        blurView.visibility = View.VISIBLE
        toolBar.visibility = View.INVISIBLE
        val circularReveal = ViewAnimationUtils.createCircularReveal(
                cardView,
                (open_search_button.right + open_search_button.left) / 2,
                (open_search_button.top + open_search_button.bottom) / 2,
                0f, searchView.width.toFloat()
        )
        circularReveal.duration = 500
        circularReveal.start()

        supportFragmentManager.beginTransaction()
                .replace(R.id.flSearchResult , SearchFragment())
                .addToBackStack("")
                .commit()
    }

    private fun closeSearch() {
        supportFragmentManager.popBackStack()
        blurView.visibility = View.INVISIBLE

        val circularConceal = ViewAnimationUtils.createCircularReveal(
                cardView,
                (open_search_button.right + open_search_button.left) / 2,
                (open_search_button.top + open_search_button.bottom) / 2,
                searchView.width.toFloat(), 0f
        )

        circularConceal.duration = 500
        circularConceal.start()
        circularConceal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) = Unit
            override fun onAnimationCancel(animation: Animator?) = Unit
            override fun onAnimationStart(animation: Animator?) = Unit
            override fun onAnimationEnd(animation: Animator?) {
                cardView.visibility = View.INVISIBLE
                toolBar.visibility = View.VISIBLE
                circularConceal.removeAllListeners()
            }
        })
    }

    private fun onScrollToEnd(offSet: Int) {
        //load next page
        viewModel.getMarvelCharachters(offSet)
    }

    private fun initRecyclerView() {
        adapter = ForecastAdapter(ArrayList() , ::onScrollToEnd)
        concatAdapter = ConcatAdapter(adapter, footerAdapter)

        rvForecast.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        rvForecast.adapter = concatAdapter
    }

    private fun initSearchView() {

        searchView.isActivated = true
        searchView.setIconifiedByDefault(false)
        searchView.isIconified = false
        
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.clear()
                viewModel.getMarvelCharachters()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                charactersSharedViewModel.searchQueryLiveData.value = newText
                return false
            }
        })

    }

    private fun observeScreenState() {
        viewModel.screenState.observe(this , Observer {
            onScreenStateChanged(it)
        })
    }

    private fun onScreenStateChanged(state: MarvelCharactersViewModel.ForecastScreenState?) {
        when (state) {
            is MarvelCharactersViewModel.ForecastScreenState.SuccessAPIResponse -> handleSuccess(state.data)
            is MarvelCharactersViewModel.ForecastScreenState.ErrorLoadingFromApi -> handleErrorLoadingFromApi(state.error)
            else -> {}
        }

        handleLoadingVisibility(state == MarvelCharactersViewModel.ForecastScreenState.Loading)
        handleErrorViewsVisibility(state)

    }

    private fun handleErrorViewsVisibility(state: MarvelCharactersViewModel.ForecastScreenState?) {
        if (state is MarvelCharactersViewModel.ForecastScreenState.ErrorLoadingFromApi)
            errorViewLayout.visibility = View.VISIBLE
        else
            errorViewLayout.visibility = View.GONE

    }


    private fun handleErrorLoadingFromApi(error: Throwable) {
        Log.d(TAG, "handleErrorLoadingFromApi: ")
    }

    private fun handleSuccess(data: List<Character>) {
        adapter.setList(data)
    }

    private fun handleLoadingVisibility(show: Boolean) {
        footerAdapter.setLoading(show)
    }


}