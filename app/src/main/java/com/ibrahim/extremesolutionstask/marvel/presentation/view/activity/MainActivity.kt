package com.ibrahim.extremesolutionstask.marvel.presentation.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibrahim.extremesolutionstask.R
import com.ibrahim.extremesolutionstask.marvel.data.model.character.Result
import com.ibrahim.extremesolutionstask.marvel.presentation.view.adapter.FooterLoadingAdapter
import com.ibrahim.extremesolutionstask.marvel.presentation.view.adapter.ForecastAdapter
import com.ibrahim.extremesolutionstask.marvel.presentation.viewmodel.MarvelCharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val TAG = "Looog"

    lateinit var adapter: ForecastAdapter

    private lateinit var concatAdapter: ConcatAdapter
    private var footerAdapter = FooterLoadingAdapter()

    @Inject
    lateinit var viewModel : MarvelCharactersViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observeScreenState()
        initSearchView()
        initRecyclerView()

        viewModel.getMarvelCharachters()

    }

    private fun initRecyclerView() {
        adapter = ForecastAdapter(ArrayList())
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

            override fun onQueryTextChange(newText: String?): Boolean {return false}
        })

    }

    private fun observeScreenState() {
        viewModel.screenState.observe(this , Observer {
            onScreenStateChanged(it)
        })
    }

    private fun onScreenStateChanged(state: MarvelCharactersViewModel.ForecastScreenState?) {
        Log.d(TAG, "onScreenStateChanged: ${state.toString()}")

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

    private fun handleSuccess(data: List<Result>) {
        adapter.setList(data)
    }

    private fun handleLoadingVisibility(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE

        footerAdapter.setLoading(show)
    }


}