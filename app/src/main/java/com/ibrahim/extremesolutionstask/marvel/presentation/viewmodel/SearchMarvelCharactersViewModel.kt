package com.ibrahim.extremesolutionstask.marvel.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ibrahim.extremesolutionstask.marvel.data.model.character.Character
import com.ibrahim.extremesolutionstask.marvel.data.model.character.MarvelResponse
import com.ibrahim.extremesolutionstask.marvel.domain.entity.MarvelParams
import com.ibrahim.extremesolutionstask.marvel.domain.interactor.GetMarvelUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class SearchMarvelCharactersViewModel @Inject constructor(
        private val refreshForecastUseCase: GetMarvelUseCase
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val screenState by lazy { MutableLiveData<SearchScreenState>() }

    fun getMarvelCharachters(name: String = "") {

        screenState.value = SearchScreenState.Loading
        val params = MarvelParams(nameStartsWith = name)

        refreshForecastUseCase.fetchMarvel(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    handleSuccessResponse(it)
                }, {
                    handleErrorResponse(it)
                }).addTo(compositeDisposable)
    }


    fun handleErrorResponse(it: Throwable) {
        screenState.value = SearchScreenState.ErrorLoadingFromApi(it)
    }

    private fun handleSuccessResponse(it: MarvelResponse) {
        screenState.value = SearchScreenState.SuccessAPIResponse(it.data.results)
    }


    sealed class SearchScreenState {
        object Loading : SearchScreenState()
        class SuccessAPIResponse(val data: List<Character>) : SearchScreenState()
        class ErrorLoadingFromApi(val error: Throwable) : SearchScreenState()
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        screenState.value = SearchScreenState.Loading
        super.onCleared()
    }
}