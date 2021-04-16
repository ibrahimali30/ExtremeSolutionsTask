package com.ibrahim.extremesolutionstask.marvel.data.model.details

data class Characters(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)