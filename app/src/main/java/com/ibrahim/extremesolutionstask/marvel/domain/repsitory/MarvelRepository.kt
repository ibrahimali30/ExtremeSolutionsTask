package com.ibrahim.extremesolutionstask.marvel.domain.repsitory


import com.ibrahim.extremesolutionstask.marvel.data.model.character.MarvelResponse
import com.ibrahim.extremesolutionstask.marvel.domain.entity.MarvelParams
import io.reactivex.Single

interface MarvelRepository {
    fun fetchMarvel(cityName: MarvelParams): Single<MarvelResponse>
    fun fetchMarvelSubCategories(cityName: MarvelParams): Single<MarvelResponse>
}