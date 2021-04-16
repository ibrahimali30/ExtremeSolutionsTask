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
                 apikey = params.apikey,
                 ts = ts,
                 hash = getMD5hash(ts + PRIVATE_KEY + PUBLIC_KEY),
                 offset = params.offset,
                 nameStartsWith = params.nameStartsWith
         )
     }


    private fun getTimestamp(): String {
        return (System.currentTimeMillis() / 1000).toString()
    }

    fun getMD5hash(s: String): String {
        var messageDigest: MessageDigest? = null
        try {
            messageDigest = MessageDigest.getInstance("MD5")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        messageDigest!!.update(s.toByteArray(), 0, s.length)
        return BigInteger(1, messageDigest!!.digest()).toString(16)
    }
}