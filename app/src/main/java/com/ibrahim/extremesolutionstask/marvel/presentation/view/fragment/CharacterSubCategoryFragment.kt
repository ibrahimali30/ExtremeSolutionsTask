package com.ibrahim.extremesolutionstask.marvel.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibrahim.extremesolutionstask.R
import com.ibrahim.extremesolutionstask.marvel.data.model.character.Character
import com.ibrahim.extremesolutionstask.marvel.presentation.view.activity.ImageFullScreenActivity
import com.ibrahim.extremesolutionstask.marvel.presentation.view.adapter.CharactersHorizontalAdapter
import com.ibrahim.extremesolutionstask.marvel.presentation.viewmodel.MarvelCharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_sub_category.*
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class CharacterSubCategoryFragment: Fragment() {

    @Inject
    lateinit var viewModel : MarvelCharactersViewModel
    private lateinit var adapter: CharactersHorizontalAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sub_category, container!!, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeScreenState()
        initRecyclerView()
        init()
    }

    private fun init() {
        val id = arguments?.getString(EXTRA_CATEGORY_ID) ?: ""
        val name = arguments?.getString(EXTRA_CATEGORY_NAME) ?: ""
        tvListTitle.text = name
        viewModel.getSubMarvelCharachters(id, name)
    }

    private fun initRecyclerView() {
        adapter = CharactersHorizontalAdapter(ArrayList(), ::onComicClicked)
        rvCharacterList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvCharacterList.adapter = adapter
    }

    private fun onComicClicked(imageurl: String, view: View) {
        ImageFullScreenActivity.startCallingIntent(imageurl, requireActivity(), view)
    }

    private fun observeScreenState() {
        viewModel.screenState.observe(viewLifecycleOwner , Observer {
            onScreenStateChanged(it)
        })
    }

    private fun onScreenStateChanged(state: MarvelCharactersViewModel.ScreenState?) {
        when (state) {
            is MarvelCharactersViewModel.ScreenState.Loading -> handleLoadingVisibility(true)
            is MarvelCharactersViewModel.ScreenState.SuccessAPIResponse -> handleSuccess(state.data)
            is MarvelCharactersViewModel.ScreenState.ErrorLoadingFromApi -> removeCurrentFragment()
            else -> {}
        }
    }

    private fun handleLoadingVisibility(showLoading: Boolean) {
        progressBar.visibility = if (showLoading) View.VISIBLE else View.GONE
    }

    private fun removeCurrentFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .remove(this)
            .commit()
    }

    private fun handleSuccess(data: List<Character>) {
        if (data.isEmpty()){
            removeCurrentFragment()
        }else{
            handleLoadingVisibility(false)
            adapter.setList(data)
        }
    }

    companion object{

        val EXTRA_CATEGORY_ID = "id"
        val EXTRA_CATEGORY_NAME = "name"

        fun newInstance(id: String, name: String): CharacterSubCategoryFragment =
            CharacterSubCategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_CATEGORY_NAME, name)
                    putString(EXTRA_CATEGORY_ID, id)
                }
            }
    }
    
}