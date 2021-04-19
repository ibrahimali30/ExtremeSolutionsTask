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

class MarvelCharactersViewModel @Inject constructor(
    private val refreshForecastUseCase: GetMarvelUseCase
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val screenState by lazy { MutableLiveData<ScreenState>() }

    var offset: Int = -1
    fun getMarvelCharachters( offset: Int = 0) {
        if (this.offset == offset) return
        this.offset = offset

        screenState.value = ScreenState.Loading
        val params = MarvelParams(offset = offset)
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
        offset = -1
        screenState.value = ScreenState.ErrorLoadingFromApi(it)
    }

    private fun handleSuccessResponse(it: MarvelResponse) {
        screenState.value = ScreenState.SuccessAPIResponse(it.data.results)
    }

    fun getSubMarvelCharachters(id: String, name: String) {
        screenState.value = ScreenState.Loading
        val params = MarvelParams(id = id, category = name)
        refreshForecastUseCase.fetchMarvelSubCategories(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                handleSuccessResponse(it)
            }, {
                handleErrorResponse(it)
            }).addTo(compositeDisposable)
    }

    sealed class ScreenState {
        object Loading : ScreenState()
        class SuccessAPIResponse(val data: List<Character>) : ScreenState()
        class ErrorLoadingFromApi(val error: Throwable) : ScreenState()
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}