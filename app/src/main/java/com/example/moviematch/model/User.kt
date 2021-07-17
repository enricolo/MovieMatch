package com.example.moviematch.model

data class User(
    var userId: Int?,
    var roomId: Int?,
    var genre: List<String>?,
    var platform: List<String>?,
    var approvedMovies: List<Int>?,
)
