package com.ibrahim.extremesolutionstask.marvel.data.model.character

import java.io.Serializable

data class Character(
    val id: Int,
    val modified: String,
    val name: String,
    val description: String,
    val resourceURI: String,
    val comics: Series,
    val events: Series,
    val series: Series,
    val stories: Series,
    val thumbnail: Thumbnail,
    val urls: List<Url>
): Serializable