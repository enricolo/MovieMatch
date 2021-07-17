package com.example.moviematch.model.MatchedMovie

data class Response(
    val image: String,
    val movieId: Int,
    val plot: String,
    val rating: Double,
    val studio: String,
    val title: String,
    val year: Int
)