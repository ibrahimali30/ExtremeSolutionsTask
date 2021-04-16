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
import com.ibrahim.extremesolutionstask.marvel.presentation.view.adapter.SearchAdapter
import com.ibrahim.extremesolutionstask.marvel.presentation.viewmodel.CharactersSharedViewModel
import com.ibrahim.extremesolutionstask.marvel.presentation.viewmodel.SearchMarvelCharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*
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
            adapter.clear()
            viewModel.getMarvelCharachters(it)
        })
    }

    private fun initRecyclerView() {
        adapter = SearchAdapter(ArrayList())
        rvSearchResult.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvSearchResult.adapter = adapter
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


    }

    private fun handleErrorLoadingFromApi(error: Throwable) {

    }


    private fun handleSuccess(data: List<Character>) {
        adapter.setList(data)
    }




    
    
}