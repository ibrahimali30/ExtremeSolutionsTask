package com.ibrahim.extremesolutionstask.marvel.data.model.character

import java.io.Serializable

data class Character(
    val id: Int,
    val modified: String,
    val name: String,
    val description: String,
    val resourceURI: String,
    val comics: SubCategory,
    val events: SubCategory,
    val series: SubCategory,
    val stories: SubCategory,
    val thumbnail: Thumbnail?,
    val urls: List<Url>
): Serializable