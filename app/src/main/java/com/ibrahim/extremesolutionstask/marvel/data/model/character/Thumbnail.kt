package com.ibrahim.extremesolutionstask.marvel.data.model.character

import java.io.Serializable

data class Thumbnail(
    val extension: String,
    val path: String
): Serializable{
    fun getFullThumbnail() = "$path.$extension"
}