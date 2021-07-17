package com.example.moviematch.data

import com.example.moviematch.model.User
import kotlin.properties.Delegates

class User{
    companion object{

        var userId by Delegates.notNull<Int>()
        var roomId by Delegates.notNull<Int>()
        lateinit var genre: List<String>
        lateinit var platform: List<String>
        lateinit var approvedMovies: List<Int>
    }

}
