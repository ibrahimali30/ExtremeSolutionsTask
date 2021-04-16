package com.ibrahim.extremesolutionstask.marvel.data.source.remote

import com.ibrahim.extremesolutionstask.base.PUBLIC_KEY
import com.ibrahim.extremesolutionstask.marvel.data.model.character.MarvelResponse
import io.reactivex.Single
import com.ibrahim.extremesolutionstask.marvel.data.model.details.DetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApiService {

    @GET("characters")
    fun getMarvel(
        @Query("ts") ts:String = "",
        @Query("apikey") apikey:String = PUBLIC_KEY,
        @Query("hash") hash:String = "",
        @Query("nameStartsWith") nameStartsWith:String? = null,
        @Query("offset") offset:Int = 0,
        @Query("limit") limit:Int = 5
    ): Single<MarvelResponse>


    @GET("characters/{id}/{category}")
    fun getMarvelDetails(
        @Path("id") id:String = "1011334",
        @Path("category") category:String = "comics",
        @Query("ts") ts:String = "",
        @Query("apikey") apikey:String = PUBLIC_KEY,
        @Query("hash") hash:String = "",
        @Query("limit") limit:Int = 5,


    ): Single<DetailsResponse>

}


