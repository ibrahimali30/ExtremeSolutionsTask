package com.ibrahim.extremesolutionstask.marvel.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibrahim.extremesolutionstask.R
import com.ibrahim.extremesolutionstask.marvel.data.model.character.Character
import com.ibrahim.extremesolutionstask.marvel.presentation.view.activity.HomeActivity
import com.ibrahim.extremesolutionstask.marvel.presentation.view.activity.MarvelCharacterDetailsActivity
import com.ibrahim.extremesolutionstask.marvel.presentation.view.adapter.SearchAdapter
import com.ibrahim.extremesolutionstask.marvel.presentation.view.extensions.closeSearch
import com.ibrahim.extremesolutionstask.marvel.presentation.viewmodel.CharactersSharedViewModel
import com.ibrahim.extremesolutionstask.marvel.presentation.viewmodel.SearchMarvelCharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.progressBar
import java.util.ArrayList
import javax.inject.Inject


@AndroidEntryPoint
class SearchFragment: Fragment() {

    @Inject
    lateinit var viewModel : SearchMarvelCharactersViewModel

    val charactersSharedViewModel by lazy {
        ViewModelProvider(requireActivity()).get(CharactersSharedViewModel::class.java)
    }

    private lateinit var adapter: SearchAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container!!, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeScreenState()
        initSearchView()
        initRecyclerView()

    }

    private fun initSearchView() {
        charactersSharedViewModel.searchQueryLiveData.observe(viewLifecycleOwner , Observer {
            onSearchQueryChanged(it)
        })
    }

    private fun onSearchQueryChanged(it: String?) {
        it ?: return
        adapter.clear()
        adapter.searchQuery = it
        if (it.isNotEmpty())
            viewModel.getMarvelCharachters(it)
    }

    private fun initRecyclerView() {
        adapter = SearchAdapter(ArrayList(), ::onItemClicked)
        rvSearchResult.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvSearchResult.adapter = adapter
    }

    private fun onItemClicked(character: Character, viewToTransition: View, viewToTransition2: View) {
        MarvelCharacterDetailsActivity.startCallingIntent(character, requireActivity(), viewToTransition, viewToTransition2)
    }


    private fun observeScreenState() {
        viewModel.screenState.observe(viewLifecycleOwner , Observer {
            onScreenStateChanged(it)
        })
    }


    private fun onScreenStateChanged(state: SearchMarvelCharactersViewModel.SearchScreenState?) {
        when (state) {
            is SearchMarvelCharactersViewModel.SearchScreenState.SuccessAPIResponse -> handleSuccess(state.data)
            is SearchMarvelCharactersViewModel.SearchScreenState.ErrorLoadingFromApi -> handleErrorLoadingFromApi(state.error)
            else -> {}
        }

        handleLoadingVisibility(state == SearchMarvelCharactersViewModel.SearchScreenState.Loading)
    }

    private fun handleErrorLoadingFromApi(error: Throwable) {

    }

    private fun handleLoadingVisibility(showLoading: Boolean) {
        progressBar.visibility = if (showLoading) View.VISIBLE else View.GONE
    }

    private fun handleSuccess(data: List<Character>) {
        adapter.setList(data)
    }


    override fun onDestroy() {
        (activity as HomeActivity).closeSearch()
        charactersSharedViewModel.searchQueryLiveData.value = ""
        super.onDestroy()
    }
    
    
}