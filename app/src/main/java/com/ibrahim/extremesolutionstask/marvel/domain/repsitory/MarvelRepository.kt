package com.ibrahim.extremesolutionstask.marvel.domain.repsitory


import com.ibrahim.extremesolutionstask.marvel.data.model.character.MarvelResponse
import com.ibrahim.extremesolutionstask.marvel.domain.entity.MarvelParams
import io.reactivex.Single

interface MarvelRepository {
    fun fetchMarvel(params: MarvelParams): Single<MarvelResponse>
    fun fetchMarvelSubCategories(params: MarvelParams): Single<MarvelResponse>
}