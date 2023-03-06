package com.aegis.myapplication.dto

import kotlinx.serialization.Serializable

@Serializable
data class Results(
    var results: List<Movie>
)

@Serializable
data class Movie(
    var adult: Boolean,
    var backdrop_path: String,
    var genre_ids: List<Int>,
    var id: Int,
    var original_language: String,
    var original_title: String,
    var overview: String,
    var popularity: Double,
    var release_date: String,
    var title: String,
    var video: Boolean,
    var vote_average: Double,
    var vote_count: Int,
)
