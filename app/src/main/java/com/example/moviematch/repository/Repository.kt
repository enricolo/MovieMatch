package com.example.moviematch.repository

import com.example.moviematch.api.RetrofitInstance
import com.example.moviematch.model.MatchedMovie.MatchedMovieResponse
import com.example.moviematch.model.basicResponse.BasicResponse
import com.example.moviematch.model.createUser.CreateUser
import com.example.moviematch.model.findMatch.FindMatchResponse
import com.example.moviematch.model.getMovie.GetMovieResponse
import com.example.moviematch.model.setGenre.SetGenre
import com.example.moviematch.model.setNewRoom.SetNewRoom
import com.example.moviematch.model.setRoom.SetRoom
import retrofit2.Response

class Repository {



    suspend fun createUser(username:String): Response<CreateUser> {
        return RetrofitInstance.api.createUser(username)
    }

    suspend fun setNewRoom(userId:Int): Response<SetNewRoom> {
        return RetrofitInstance.api.setNewRoom(userId)
    }

    suspend fun setRoom(userId:Int, roomId:Int): Response<SetRoom> {
        return RetrofitInstance.api.setRoom(userId, roomId)
    }

    suspend fun getMovies(roomId: Int, requestNo: Int): Response<GetMovieResponse> {
        return RetrofitInstance.api.getMovies(roomId, requestNo)
    }

    suspend fun setGenre(userId:Int,genre:String): Response<SetGenre> {
        return RetrofitInstance.api.setGenre(userId,genre)
    }

    suspend fun addApprovedMovie(userId:Int,roomId:Int, movieId:Int): Response<BasicResponse> {
        return RetrofitInstance.api.addApprovedMovie(userId, roomId, movieId)
    }

    suspend fun findMatch(roomId:Int): Response<FindMatchResponse> {
        return RetrofitInstance.api.findMatch(roomId)
    }

    suspend fun matchedMovie(roomId:Int): Response<MatchedMovieResponse> {
        return RetrofitInstance.api.matchedMovie(roomId)
    }


}
