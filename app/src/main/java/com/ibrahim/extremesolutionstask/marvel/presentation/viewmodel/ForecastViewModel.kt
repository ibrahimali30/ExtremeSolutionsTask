package com.ibrahim.extremesolutionstask.marvel.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ibrahim.extremesolutionstask.marvel.data.model.character.MarvelResponse
import com.ibrahim.extremesolutionstask.marvel.data.model.character.Result
import com.ibrahim.extremesolutionstask.marvel.domain.entity.MarvelParams
import com.ibrahim.extremesolutionstask.marvel.domain.interactor.GetMarvelUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class ForecastViewModel @Inject constructor(
        private val refreshForecastUseCase: GetMarvelUseCase
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val screenState by lazy { MutableLiveData<ForecastScreenState>() }

    fun getForecast(cityName: String) {
        screenState.value = ForecastScreenState.Loading

        refreshForecastUseCase.fetchMarvel(MarvelParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    handleSuccessResponse(it)
                }, {
                    handleErrorResponse(it)
                }).addTo(compositeDisposable)
    }


    fun handleErrorResponse(it: Throwable) {
        screenState.value = ForecastScreenState.ErrorLoadingFromApi(it)
    }

    private fun handleSuccessResponse(it: MarvelResponse) {
        screenState.value = ForecastScreenState.SuccessAPIResponse(it.data.results)
    }


    sealed class ForecastScreenState {
        object Loading : ForecastScreenState()
        class SuccessAPIResponse(val data: List<Result>) : ForecastScreenState()
        class ErrorLoadingFromApi(val error: Throwable) : ForecastScreenState()
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}