package com.example.moviematch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviematch.R
import com.example.moviematch.model.MatchedMovie.Response

class MovieMatchAdapter(private val dataSet: List<Response>, val context: Context): RecyclerView.Adapter<MovieMatchAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movieTitle: TextView = view.findViewById(R.id.movieTitle)
        val movieYear: TextView = view.findViewById(R.id.movieYear)
        val movieDescription: TextView = view.findViewById(R.id.movieDescription)
        val imageview: ImageView = view.findViewById(R.id.imageView3)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.movie_match_item, viewGroup, false)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        holder.movieTitle.text = dataSet[position].title
        holder.movieYear.text = dataSet[position].year.toString()
        holder.movieDescription.text = dataSet[position].plot

//        holder.movieTitle.text = "dataSet[position].title"
//        holder.movieYear.text =" dataSet[position].year.toString()"
//        holder.movieDescription.text = "dataSet[position].plot"


//        setImageView(holder, "")
        val noData = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fih1.redbubble.net%2Fimage.115705879.4421%2Fflat%2C800x800%2C075%2Cf.u4.jpg&f=1&nofb=1"

        setImageView(holder.imageview, dataSet[position].image)
    }


    override fun getItemCount(): Int = dataSet.size



    private fun setImageView(imageview: ImageView, media: String?) {

        if (media !== null) {
            Glide.with(context)
                .load(media)
                .into(imageview)
        } else {
            imageview.setImageResource(R.drawable.ic_launcher_background)
        }
    }
}