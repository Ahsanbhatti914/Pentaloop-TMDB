package com.example.tmdbexplorer.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdbexplorer.Models.AllMovies.PopularMovie
import com.example.tmdbexplorer.activities.MovieDetailsActivity
import com.example.tmdbexplorer.R
import java.time.Year


class MovieAdapter (private var moviesList: List<PopularMovie>, private val context: Context) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = moviesList[position]

        holder.movieTitle.text = movie.title
        val date = movie.release_date.split("-")
        holder.movieYear.text = date[0]
        holder.movieLanguage.text = " | "+movie.original_language

        val imageUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
        Glide.with(context).load(imageUrl).into(holder.posterImage)

        val year = Year.now().toString()
        if(year == date[0]){
            holder.movieYear.setTextColor(Color.RED)
            holder.movieYear.setTypeface(null, Typeface.BOLD)
        }else{
            holder.movieYear.setTextColor(Color.BLACK)
            holder.movieYear.setTypeface(null, Typeface.NORMAL)
        }

        holder.posterImage.setOnClickListener {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra("MOVIE_ID",moviesList[position].id.toString())
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMoviesList(moviesList: List<PopularMovie>) {
        this.moviesList = moviesList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterImage: ImageView = itemView.findViewById(R.id.moviePosterImageMI)
        val movieTitle: TextView = itemView.findViewById(R.id.movieTitleMI)
        val movieYear: TextView = itemView.findViewById(R.id.movieYearMI)
        val movieLanguage: TextView = itemView.findViewById(R.id.movieLanguageMI)
    }
}