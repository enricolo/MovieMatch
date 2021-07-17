package com.example.moviematch.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.moviematch.R
import com.example.moviematch.data.User
import com.example.moviematch.model.getMovie.Movie
import com.example.moviematch.repository.Repository
import com.example.moviematch.viewModel.MainViewModel
import com.example.moviematch.viewModel.MainViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [MovieFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieFragment : Fragment() {

    private var thisMoviesList: MutableList<Movie> = mutableListOf()
    private lateinit var viewModel: MainViewModel
    private var movieId = 0
    private var requestNo = 0
    private var found = false

        // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_movie, container, false)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        getMovies(view)




        return view
    }

    private fun getMovies(view: View) {

        viewModel.getMovies(User.roomId, requestNo)
        Log.d("getMoviesResponse", "getMovies FR")

        viewModel.getMoviesResponse.observe(viewLifecycleOwner, Observer {
            Log.d("getMoviesResponse", "inside observer")
            Log.d("getMoviesResponse", it.response.toString())

            thisMoviesList.addAll(it.response)
            setDetailsMovieView(view)

            likeButton(view)
            dislikeButton(view)

//            Log.d("moviesList.size", moviesList.size.toString())
//            for (movie in moviesList){
//                Log.d("moviesList", movie.title)
//            }

        })
    }


    private fun findMatch(view: View) {

        viewModel.findMatch(User.roomId)

        viewModel.findMatchResponse.observe(viewLifecycleOwner, Observer {
            Log.d("isMatch", it.code.toString())
            Log.d("isMatch", it.found.toString())
            Log.d("isMatch", it.numberOfMatches.toString())

            found = it.found
            view.findViewById<TextView>(R.id.noMovieMatched)
                .text = it.numberOfMatches.toString()


        })
    }


//    TODO change to real room
//    private fun addMovies() {
//    requestNo += 1
//        viewModel.getMoviesResponse.value = null
//        viewModel.getMovies(2, requestNo)
//        Log.d("getMoviesFR", "getMovies FR")
//        var test = 0
//        viewModel.getMoviesResponse.observe(viewLifecycleOwner, Observer {
//            Log.d("checazzosucceded", test.toString())
//            test += 1
//            Log.d("addMoviesResponse", "inside observer")
//            Log.d("addMoviesResponse", it.response.toString())
//            for(movie in it.response)
//                Log.d("addMoviesResponse","newResponse: " + movie.title)
//            for(movie in moviesList)
//                Log.d("movieResponse","old: " + movie.title)
//            Log.d("requestNo", requestNo.toString())
//
//            Log.d("requestNo", it.responseToRequestNo.toString())
//            if (requestNo == it.responseToRequestNo)
//                moviesList.addAll(it.response)
//
//                for(movie in moviesList)
//                    Log.d("movieResponse", "new: " +movie.title)
//        })
//    }

    private fun addApprovedMovie(view: View) {
        if(thisMoviesList.size != 0) {
            viewModel.addApprovedMovie(User.userId, User.roomId, movieId)
            findMatch(view)
            Log.d("getMoviesFR", "getMovies FR")
        }
    }

    private fun likeButton(view: View) {
        Log.d("MoviesFR", "like Button")
        val likeButton = view.findViewById<View>(R.id.likeButton)
        val noMovieMatched = view.findViewById<TextView>(R.id.noMovieMatched)

        movieMatched(noMovieMatched)
        likeButton.setOnClickListener{
            addApprovedMovie(view)
            changeMovie(view)

        }
    }



    private fun dislikeButton(view: View) {
        Log.d("MoviesFR", "dislike Button")
        val dislikeButton = view.findViewById<View>(R.id.dislikeButton)

        dislikeButton.setOnClickListener{
            changeMovie(view)
        }
    }

    private fun movieMatched(noMovieMatched: TextView) {

        noMovieMatched.setOnClickListener{
            if (found){
                val action = MovieFragmentDirections.actionMovieFragmentToMatchedMovieFragment2()
                findNavController().navigate(action)
            }

        }
    }

    private fun setImageView(view: View, media: String, ) {
        Log.d("MoviesFR", "setImageView")
        val imageview = view.findViewById<ImageView>(R.id.imageView)

        if (media !== null) {
            Glide.with(this)
                .load(media)
                .into(imageview)
        } else {
            imageview.setImageResource(R.drawable.ic_launcher_background)
        }
    }

    private fun setDetailsMovieView(view: View) {

        val textTitle = view.findViewById<TextView>(R.id.textTitle)
        val textYear = view.findViewById<TextView>(R.id.textYear)
        val media = thisMoviesList[0].image
        movieId = thisMoviesList[0].movieId
        textTitle.text = thisMoviesList[0].title
        textYear.text = thisMoviesList[0].year.toString()
        setImageView(view, media)
    }

        private fun changeMovie(view: View) {
        Log.d("moviesList_size", thisMoviesList.size.toString())
        if(thisMoviesList.size > 0) {
            thisMoviesList.removeAt(0)
            if(thisMoviesList.size > 0) {
                setDetailsMovieView(view)
            }

        }
        else{
            Log.d("moviesList", "0 elements")
            val noData = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fih1.redbubble.net%2Fimage.115705879.4421%2Fflat%2C800x800%2C075%2Cf.u4.jpg&f=1&nofb=1"
            setImageView(view, noData)
        }
    }


}

