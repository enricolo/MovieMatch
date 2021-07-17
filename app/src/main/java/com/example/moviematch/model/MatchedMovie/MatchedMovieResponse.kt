package com.example.moviematch.model.MatchedMovie

data class MatchedMovieResponse(
    val code: Int,
    val found: Boolean,
    val response: List<Response>
)