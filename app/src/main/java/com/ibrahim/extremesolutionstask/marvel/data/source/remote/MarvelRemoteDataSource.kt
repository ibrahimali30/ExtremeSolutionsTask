package com.ibrahim.extremesolutionstask.marvel.data.source.remote

import com.ibrahim.extremesolutionstask.base.PRIVATE_KEY
import com.ibrahim.extremesolutionstask.base.PUBLIC_KEY
import io.reactivex.Single
import com.ibrahim.extremesolutionstask.marvel.data.model.character.MarvelResponse
import com.ibrahim.extremesolutionstask.marvel.domain.entity.MarvelParams
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.inject.Inject

class MarvelRemoteDataSource @Inject constructor(
    private val marvelApiService: MarvelApiService
) {

     fun fetchMarvel(params: MarvelParams): Single<MarvelResponse> {
         val ts = getTimestamp()
         return marvelApiService.getMarvel(
             ts = ts,
             hash = hash(ts + PRIVATE_KEY + PUBLIC_KEY)
         )
     }


    private fun getTimestamp(): String {
        val tsLong = System.currentTimeMillis() / 1000
        return tsLong.toString()
    }

    fun hash(s: String): String {
        var m: MessageDigest? = null
        try {
            m = MessageDigest.getInstance("MD5")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        m!!.update(s.toByteArray(), 0, s.length)
        return BigInteger(1, m!!.digest()).toString(16)
    }
}