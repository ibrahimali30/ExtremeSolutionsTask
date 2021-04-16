package com.ibrahim.extremesolutionstask.marvel.data.source.remote

import io.reactivex.Single
import com.ibrahim.extremesolutionstask.marvel.data.model.character.MarvelResponse
import com.ibrahim.extremesolutionstask.marvel.domain.entity.MarvelParams
import javax.inject.Inject

class MarvelRemoteDataSource @Inject constructor(
    private val marvelApiService: MarvelApiService
) {

     fun fetchMarvel(params: MarvelParams): Single<MarvelResponse> {
       return marvelApiService.getMarvel()
     }

}