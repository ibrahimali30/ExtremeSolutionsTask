package com.ibrahim.extremesolutionstask

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ibrahim.extremesolutionstask.base.PRIVATE_KEY
import com.ibrahim.extremesolutionstask.base.PUBLIC_KEY
import com.ibrahim.extremesolutionstask.marvel.data.source.remote.MarvelApiService
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.inject.Inject

const val TAG = "loooog"
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var marvelApiService: MarvelApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



//        callApi()

    }

//    @SuppressLint("CheckResult")
//    private fun callApi() {
//        val ts = getTimestamp()
//
//        marvelApiService.getMarvel(
//            ts = ts,
//            hash = hash(ts+PRIVATE_KEY+PUBLIC_KEY)
//        )
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    Log.d(TAG, "onCreate: $it")
//                },
//                {
//                    Log.d(TAG, "callApi: $it")
//                }
//            )
//    }


}