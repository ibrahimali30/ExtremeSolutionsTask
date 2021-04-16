package com.ibrahim.extremesolutionstask.marvel.data.model.character

data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)