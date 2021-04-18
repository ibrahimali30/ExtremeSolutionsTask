package com.ibrahim.extremesolutionstask.marvel.domain.interactor

import com.ibrahim.extremesolutionstask.marvel.domain.entity.MarvelParams
import com.ibrahim.extremesolutionstask.marvel.domain.repsitory.MarvelRepository
import javax.inject.Inject

class GetMarvelUseCase @Inject constructor(private val marvelRepository: MarvelRepository) {

    fun fetchMarvel(cityName: MarvelParams) = marvelRepository.fetchMarvel(cityName)

    fun fetchMarvelSubCategories(cityName: MarvelParams) = marvelRepository.fetchMarvelSubCategories(cityName)

}