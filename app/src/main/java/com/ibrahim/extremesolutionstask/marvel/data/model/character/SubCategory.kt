package com.ibrahim.extremesolutionstask.marvel.data.model.character

import java.io.Serializable

data class SubCategory(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
): Serializable