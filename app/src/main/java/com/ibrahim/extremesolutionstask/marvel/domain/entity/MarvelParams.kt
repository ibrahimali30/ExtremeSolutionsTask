package com.ibrahim.extremesolutionstask.marvel.domain.entity

import com.ibrahim.extremesolutionstask.base.PUBLIC_KEY


data class MarvelParams(
         var apikey:String = PUBLIC_KEY,
         var nameStartsWith:String? = null,
         var id:String = "",
         var category:String = "",
         var offset:Int = 0,
         var limit:Int = 5
){

}