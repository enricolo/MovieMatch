package com.example.moviematch.model.findMatch

data class FindMatchResponse(
    val code: Int,
    val found: Boolean,
    val numberOfMatches: Int
)