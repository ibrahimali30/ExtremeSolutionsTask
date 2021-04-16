package com.ibrahim.extremesolutionstask.marvel.data.repository

import com.ibrahim.extremesolutionstask.marvel.data.model.character.MarvelResponse
import io.reactivex.Single
import com.ibrahim.extremesolutionstask.marvel.data.source.remote.MarvelRemoteDataSource
import com.ibrahim.extremesolutionstask.marvel.domain.entity.MarvelParams
import com.ibrahim.extremesolutionstask.marvel.domain.repsitory.MarvelRepository


import javax.inject.Inject


class MarvelRepositoryImpl @Inject constructor(
    private val marvelRemoteDataSource: MarvelRemoteDataSource
) : MarvelRepository {

    override fun fetchMarvel(cityName: MarvelParams): Single<MarvelResponse> {
        return marvelRemoteDataSource.fetchMarvel(cityName)
    }


}
