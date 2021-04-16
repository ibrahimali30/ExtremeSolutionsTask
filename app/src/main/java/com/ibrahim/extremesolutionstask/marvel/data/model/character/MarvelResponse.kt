package com.ibrahim.extremesolutionstask.marvel.data.model.character

import com.ibrahim.extremesolutionstask.marvel.data.model.character.Data


data class MarvelResponse(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val `data`: Data,
    val etag: String,
    val status: String
)