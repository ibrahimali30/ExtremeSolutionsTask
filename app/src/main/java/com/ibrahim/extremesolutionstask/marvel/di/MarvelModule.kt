package com.ibrahim.orcastask.forcast.presentation.di

import android.util.Log
import com.ibrahim.extremesolutionstask.marvel.data.repository.MarvelRepositoryImpl
import com.ibrahim.extremesolutionstask.marvel.data.source.remote.MarvelApiService
import com.ibrahim.extremesolutionstask.marvel.domain.repsitory.MarvelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
class MarvelModule {

    @Provides
    fun providesMarvelRepository(marvelRepositoryImpl: MarvelRepositoryImpl): MarvelRepository = marvelRepositoryImpl

    @Provides
    fun providesMarvelApiService(builder: Retrofit.Builder): MarvelApiService {
        return builder.build().create(MarvelApiService::class.java)
    }


}