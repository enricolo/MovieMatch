package com.example.moviematch.api


import com.example.moviematch.model.MatchedMovie.MatchedMovieResponse
import com.example.moviematch.model.basicResponse.BasicResponse
import com.example.moviematch.model.createUser.CreateUser
import com.example.moviematch.model.findMatch.FindMatchResponse
import com.example.moviematch.model.getMovie.GetMovieResponse
import com.example.moviematch.model.setGenre.SetGenre
import com.example.moviematch.model.setNewRoom.SetNewRoom
import com.example.moviematch.model.setRoom.SetRoom
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @FormUrlEncoded
    @POST("/createUser/?")
    suspend fun createUser(
        @Field("username") username:String
    ): Response<CreateUser>

    @GET("getMovies/?")
    suspend fun getMovies(
        @Query("roomId") roomId: Int,
        @Query("requestNo") requestNo: Int

    ): Response<GetMovieResponse>

    @FormUrlEncoded
    @POST("/setNewRoom/?")
    suspend fun setNewRoom(
        @Field("userId") userId: Int
    ): Response<SetNewRoom>

    @FormUrlEncoded
    @POST("/setRoom/?")
    suspend fun setRoom(
        @Field("userId") userId:Int,
        @Field("roomId") roomId:Int
    ): Response<SetRoom>

    @FormUrlEncoded
    @POST("/setGenre/?")
    suspend fun setGenre(
        @Field("userId") userId:Int,
        @Field("genre") genre:String
    ): Response<SetGenre>

    @FormUrlEncoded
    @POST("/addApprovedMovie/?")
    suspend fun addApprovedMovie(
        @Field("userId") userId:Int,
        @Field("roomId") roomId:Int,
        @Field("movieId") movieId:Int
    ): Response<BasicResponse>


    @GET("/findMatch/?")
    suspend fun findMatch(
        @Query("roomId") roomId:Int
    ): Response<FindMatchResponse>


    @GET("/matchedMovie/?")
    suspend fun matchedMovie(
        @Query("roomId") roomId:Int
    ): Response<MatchedMovieResponse>
}
