package com.example.moviematch.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.moviematch.R

class RoomChoiceFragment : Fragment() {
    private lateinit var userId: String
    private var username: String? = null

    companion object {
        const val USERID = "userId"
        const val USERNAME = "username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("onCreate", arguments.toString())
        arguments?.let {
            userId = it.getString(USERID).toString()
            username = it.getString(USERNAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_room_choice, container, false)

        navigateSetRoomButton(view)
        navigateNewRoomButton(view)

        return view
    }

    private fun navigateNewRoomButton(view: View) {
        val nextButton = view.findViewById<View>(R.id.newRoom)

        nextButton.setOnClickListener{
            Log.d("onCreate", userId)
            val action = RoomChoiceFragmentDirections.actionRoomChoiceFragmentToNewRoomFragment(userId)
            findNavController().navigate(action)
        }
    }
    private fun navigateSetRoomButton(view: View) {
        val nextButton = view.findViewById<View>(R.id.setRoom)

        nextButton.setOnClickListener{
            val action = RoomChoiceFragmentDirections.actionRoomChoiceFragmentToSetRoomFragment()
            findNavController().navigate(action)
        }
    }
}