package com.example.moviematch.model.getMovie

data class GetMovieResponse(
    val code: Int,
    val response: List<Movie>,
    val responseToRequestNo: Int
)