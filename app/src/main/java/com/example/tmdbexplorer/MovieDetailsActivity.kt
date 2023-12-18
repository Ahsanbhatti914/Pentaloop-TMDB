package com.example.tmdbexplorer

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.tmdbexplorer.Models.MovieByID.MovieById
import com.example.tmdbexplorer.databinding.ActivityMainBinding
import com.example.tmdbexplorer.databinding.ActivityMovieDetailsBinding
import com.example.tmdbexplorer.repository.MovieRepository
import com.example.tmdbexplorer.viewmodels.MovieDetailsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsActivity : AppCompatActivity() {
    private val viewModel:MovieDetailsViewModel by viewModels()
    private lateinit var binding: ActivityMovieDetailsBinding


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedIntent = intent
        val movieID = receivedIntent.getStringExtra("MOVIE_ID")
        if(movieID != null){
            getMovieById(movieID)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setViewsData(){
        viewModel.movieById.observe(this, Observer {
            val movie = it
            val res = movie.release_date.split("-")
            binding.movieYearMDA.text = res[0] + " | "

            binding.movieDurationMDA.text = changeTime(movie.runtime)
            binding.movieTitleMDA.text = movie.title
            binding.movieDateMDA.text = movie.release_date

            var genreType = " • "
            for (i in movie.genres.indices){
                genreType += "${movie.genres[i].name} "
            }
            binding.movieGenreMDA.text = genreType
            binding.movieTagMDA.text = movie.tagline
            binding.movieOverviewMDA.text = movie.overview
            binding.movieVoteAverageMDA.text = movie.vote_average.toString()
            binding.movieVoteCountMDA.text = movie.vote_count.toString()

            val imageUrlBackDrop = "https://image.tmdb.org/t/p/w500${movie.backdrop_path}"
            val imageUrlPoster = "https://image.tmdb.org/t/p/w500${movie.poster_path}"

            Glide.with(applicationContext).load(imageUrlBackDrop).into(binding.backDropImageMDA)
            Glide.with(applicationContext).load(imageUrlPoster).into(binding.posterImageMDA)
        })
    }

    private fun getMovieById(movieById:String){
        viewModel.movieById(movieById)
        setViewsData()
    }

    private fun changeTime(value:Int) : String{
        var duration = "" + value
        if(value > 60){
            val hour = value / 60
            val min = value % 60
            duration = "" + hour + "h " + min + "m"
        }
        return duration
    }
}