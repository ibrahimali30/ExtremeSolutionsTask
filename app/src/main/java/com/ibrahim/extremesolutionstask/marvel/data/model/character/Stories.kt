package com.ibrahim.extremesolutionstask.marvel.data.model.character

import com.ibrahim.extremesolutionstask.marvel.data.model.character.ItemXX

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemXX>,
    val returned: Int
)