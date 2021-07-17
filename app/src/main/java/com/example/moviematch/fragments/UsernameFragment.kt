package com.example.moviematch.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.moviematch.R
import com.example.moviematch.data.User
import com.example.moviematch.repository.Repository
import com.example.moviematch.viewModel.MainViewModel
import com.example.moviematch.viewModel.MainViewModelFactory


class UsernameFragment : Fragment() {

    private lateinit var viewModel: MainViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        User.userId = 0
        User.roomId = 0
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_username, container, false)
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        navigateNextButton(view)
        return view
    }

    private fun navigateNextButton(view: View) {
        val nextButton = view.findViewById<View>(R.id.next)

        nextButton.setOnClickListener{
            val username = view.findViewById<TextView>(R.id.roomId).text.toString()
            viewModel.createUser(username)
            viewModel.createUserResponse.observe(viewLifecycleOwner, Observer {
                val userId = it.response.userId

                User.userId = userId
                val action = UsernameFragmentDirections.actionUsernameFragmentToRoomChoiceFragment(
                    username,
                    userId.toString()
                )
                findNavController().navigate(action)

            })

        }
    }

}