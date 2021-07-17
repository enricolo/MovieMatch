package com.example.moviematch.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviematch.adapter.MovieMatchAdapter
import com.example.moviematch.data.User
import com.example.moviematch.databinding.FragmentMatchedMovieBinding
import com.example.moviematch.repository.Repository
import com.example.moviematch.viewModel.MainViewModel
import com.example.moviematch.viewModel.MainViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


private var _binding: FragmentMatchedMovieBinding? = null
private val binding get() = _binding!!
private lateinit var recyclerView: RecyclerView

/**
 * A simple [Fragment] subclass.
 * Use the [MatchedMovieFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MatchedMovieFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_matched_movie, container, false)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMatchedMovieBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        matchedMovie()

//        val movieList = listOf<String>("", "", "")

    }


    private fun matchedMovie() {

        viewModel.matchedMovie(User.roomId)

        viewModel.matchedMovieResponse.observe(viewLifecycleOwner, Observer {
            Log.d("isMatchMovie", it.code.toString())
            Log.d("isMatchMovie", it.found.toString())
            Log.d("isMatchMovie", it.response.toString())

            val adapter = MovieMatchAdapter(
                it.response,
                requireContext()
            )
            setRecyclerView(adapter)


        })
    }

    private fun setRecyclerView(adapter: MovieMatchAdapter) {

        recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}