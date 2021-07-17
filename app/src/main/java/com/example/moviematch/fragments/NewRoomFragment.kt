package com.example.moviematch.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.moviematch.R
import com.example.moviematch.data.User
import com.example.moviematch.repository.Repository
import com.example.moviematch.viewModel.MainViewModel
import com.example.moviematch.viewModel.MainViewModelFactory

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


class NewRoomFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var userId: String
    private var canGoToNext: Boolean = false

    companion object {
        const val USERID = "userId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("onCreate", arguments.toString())
        arguments?.let {
            userId = it.getString(USERID).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_new_room, container, false)
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        setNewRoom(view)
        navigateNextButton(view)

        return view
    }


    private fun navigateNextButton(view: View) {
        val nextButton = view.findViewById<View>(R.id.next)

        nextButton.setOnClickListener{
            if (canGoToNext){
            val action = NewRoomFragmentDirections.actionNewRoomFragmentToGenreFragment()
            findNavController().navigate(action)}
            else{
                Toast.makeText(context, "wait for response", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setNewRoom(view: View) {

        viewModel.setNewRoom(User.userId)
        Log.d("getMoviesFR", "getMovies FR")

        viewModel.setNewRoomResponse.observe(viewLifecycleOwner, Observer {
            Log.d("getMoviesFR", "inside observer")
            Log.d("getMoviesFR", it.toString())
            if (it.code == 200){
                view.findViewById<TextView>(R.id.newRoomId).text = it.response.roomId.toString()
                User.roomId = it.response.roomId
                canGoToNext = true
            }


        })
    }
}