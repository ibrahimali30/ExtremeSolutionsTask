package com.ibrahim.extremesolutionstask.marvel.data.model.details

data class Data(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<Result>,
    val total: Int
)