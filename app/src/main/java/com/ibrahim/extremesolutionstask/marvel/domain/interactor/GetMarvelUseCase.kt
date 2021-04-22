package com.ibrahim.extremesolutionstask.marvel.domain.interactor

import com.ibrahim.extremesolutionstask.marvel.domain.entity.MarvelParams
import com.ibrahim.extremesolutionstask.marvel.domain.repsitory.MarvelRepository
import javax.inject.Inject

class GetMarvelUseCase @Inject constructor(private val marvelRepository: MarvelRepository) {

    fun fetchMarvel(params: MarvelParams) = marvelRepository.fetchMarvel(params)

    fun fetchMarvelSubCategories(params: MarvelParams) = marvelRepository.fetchMarvelSubCategories(params)

}