package com.example.moviematch.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviematch.model.MatchedMovie.MatchedMovieResponse
import com.example.moviematch.model.createUser.CreateUser
import com.example.moviematch.model.findMatch.FindMatchResponse
import com.example.moviematch.model.getMovie.GetMovieResponse
import com.example.moviematch.model.setGenre.SetGenre
import com.example.moviematch.model.setNewRoom.SetNewRoom
import com.example.moviematch.model.setRoom.SetRoom
import com.example.moviematch.repository.Repository
import kotlinx.coroutines.launch


class MainViewModel(private val repository: Repository): ViewModel() {


    val createUserResponse: MutableLiveData<CreateUser> = MutableLiveData()
    val setNewRoomResponse: MutableLiveData<SetNewRoom> = MutableLiveData()
    val setRoomResponse: MutableLiveData<SetRoom> = MutableLiveData()
    val setGenreResponse: MutableLiveData<SetGenre> = MutableLiveData()
    val getMoviesResponse: MutableLiveData<GetMovieResponse> = MutableLiveData()
    val findMatchResponse: MutableLiveData<FindMatchResponse> = MutableLiveData()
    val matchedMovieResponse: MutableLiveData<MatchedMovieResponse> = MutableLiveData()


    fun createUser(username: String) {
        viewModelScope.launch {
            try {
                val response = repository.createUser(username)
                if(response.isSuccessful) {
                    createUserResponse.value = response.body()
                    Log.d("createUser", response.body().toString())
                }
                else{
                    Log.d("createUser", "sent but failed")
                    Log.d("createUser", response.errorBody().toString())
                    Log.d("createUser", response.code().toString())
                }
                Log.d("createUser", response.toString())
            } catch (e: Exception) {
                Log.e("createUser", e.message, e)
            }
        }
    }
    fun setNewRoom(userId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.setNewRoom(userId)
                if(response.isSuccessful) {
                    setNewRoomResponse.value = response.body()
                    Log.d("setNewRoom", response.body().toString())
                }
                else{
                    Log.d("setNewRoom", "sent but failed")
                    Log.d("setNewRoom", response.errorBody().toString())
                    Log.d("setNewRoom", response.code().toString())
                }
                Log.d("setNewRoom", response.toString())
            } catch (e: Exception) {
                Log.e("setNewRoom", e.message, e)
            }
        }
    }

    fun setRoom(userId: Int, roomId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.setRoom(userId, roomId)
                if(response.isSuccessful) {
                    setRoomResponse.value = response.body()
                    Log.d("setRoom", response.body().toString())
                }
                else{
                    Log.d("setRoom", "sent but failed")
                    Log.d("setRoom", response.errorBody().toString())
                    Log.d("setRoom", response.code().toString())
                }
                Log.d("setRoom", response.toString())
            } catch (e: Exception) {
                Log.e("setRoom", e.message, e)
            }
        }
    }

    fun setGenre(userId: Int, genre: String) {
        viewModelScope.launch {
            try {
                val response = repository.setGenre(userId, genre)
                if(response.isSuccessful) {
                    setGenreResponse.value = response.body()
                    Log.d("setGenre", response.body().toString())
                }
                else{
                    Log.d("setGenre", "sent but failed")
                    Log.d("setGenre", response.errorBody().toString())
                    Log.d("setGenre", response.code().toString())
                }
                Log.d("setGenre", response.toString())
            } catch (e: Exception) {
                Log.e("setGenre", e.message, e)
            }
        }
    }

    fun getMovies(roomId: Int, requestNo: Int) {
        Log.d("testonegrosso1", "getMovies entrato")
        viewModelScope.launch {
            Log.d("testonegrosso1", "getMovies lanciato")
            try {
                Log.d("testonegrosso1", "siamo dentro")
                val response = repository.getMovies(roomId, requestNo)
                Log.d("testonegrosso1", response.toString())
                if(response.isSuccessful) {
                    getMoviesResponse.value = response.body()
                    Log.d("testonegrosso1", response.body().toString())
//                    moviesList.addAll(response.body()!!.response)
//                    Log.d("testonegrosso2", moviesList.toString())
                }
                else{
                    Log.d("getMoviesVM", "sent but failed")
                    Log.d("getMoviesVM", response.errorBody().toString())
                    Log.d("getMoviesVM", response.code().toString())
                }
            } catch (e: Exception) {
                Log.e("getMoviesVM", e.message, e)
            }
        }
    }

    fun addApprovedMovie(userId:Int,roomId:Int, movieId:Int) {
        viewModelScope.launch {
            try {
                val response = repository.addApprovedMovie(userId, roomId, movieId)
                if(response.isSuccessful) {

                    Log.d("addApprovedMovie", response.body().toString())
                }
                else{
                    Log.d("addApprovedMovie", "sent but failed")
                    Log.d("addApprovedMovie", response.errorBody().toString())
                    Log.d("addApprovedMovie", response.code().toString())
                }
            } catch (e: Exception) {
                Log.e("addApprovedMovie", e.message, e)
            }
        }
    }

    fun findMatch(roomId: Int) {
        Log.d("testonegrosso1", "getMovies entrato")
        viewModelScope.launch {


            Log.d("testonegrosso1", "getMovies lanciato")
            try {
                Log.d("testonegrosso1", "siamo dentro")
                val response = repository.findMatch(roomId)
                Log.d("testonegrosso1", response.toString())
                if(response.isSuccessful) {
                    findMatchResponse.value = response.body()
                    Log.d("testonegrosso1", response.body().toString())
//                    moviesList.addAll(response.body()!!.response)
//                    Log.d("testonegrosso2", moviesList.toString())
                }
                else{
                    Log.d("getMoviesVM", "sent but failed")
                    Log.d("getMoviesVM", response.errorBody().toString())
                    Log.d("getMoviesVM", response.code().toString())
                }
            } catch (e: Exception) {
                Log.e("getMoviesVM", e.message, e)
            }
        }
    }

    fun matchedMovie(roomId: Int) {
        Log.d("testonegrosso1", "getMovies entrato")
        viewModelScope.launch {

            Log.d("testonegrosso1", "getMovies lanciato")
            try {
                Log.d("testonegrosso1", "siamo dentro")
                val response = repository.matchedMovie(roomId)
                Log.d("testonegrosso1", response.toString())
                if(response.isSuccessful) {
                    matchedMovieResponse.value = response.body()
                    Log.d("testonegrosso1", response.body().toString())
                }
                else{
                    Log.d("getMoviesVM", "sent but failed")
                    Log.d("getMoviesVM", response.errorBody().toString())
                    Log.d("getMoviesVM", response.code().toString())
                }
            } catch (e: Exception) {
                Log.e("getMoviesVM", e.message, e)
            }
        }
    }


}
