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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SetRoomFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SetRoomFragment : Fragment() {

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
        val view =  inflater.inflate(R.layout.fragment_set_room, container, false)
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)



        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        clickNextButton(view)
        return view
    }

    private fun clickNextButton(view: View) {
        val nextButton = view.findViewById<View>(R.id.next)
        val textView: TextView = view.findViewById(R.id.roomId)



        nextButton.setOnClickListener {
            setRoom(textView)
//            if (!canGoToNext){
//                Toast.makeText(context, "some kind of error", Toast.LENGTH_SHORT).show()
//            }
        }
    }


    private fun setRoom(textView: TextView) {

        val roomId = textView.text.toString()
        if (roomId.isNullOrBlank()){
            Toast.makeText(context, "you have to write a room", Toast.LENGTH_SHORT).show()
            return
        }
        User.roomId = Integer.parseInt(textView.text.toString())

        viewModel.setRoom(User.userId, User.roomId)

        viewModel.setRoomResponse.observe(viewLifecycleOwner, Observer {

            if (it.code == 200){
                canGoToNext = true
                navigateNext()
            }
            else{
                Toast.makeText(context, "some kind of error", Toast.LENGTH_SHORT).show()
            }
            Log.d("getMoviesFR", "inside observer")
            Log.d("getMoviesFR", it.toString())


        })
    }

    private fun navigateNext() {

        if (canGoToNext){
            val action = SetRoomFragmentDirections.actionSetRoomFragmentToGenreFragment()
            findNavController().navigate(action)
        }else{
            Toast.makeText(context, "some kind of error", Toast.LENGTH_SHORT).show()
        }

    }
}