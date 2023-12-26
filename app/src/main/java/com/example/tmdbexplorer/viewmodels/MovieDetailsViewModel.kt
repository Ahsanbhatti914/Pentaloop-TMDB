package com.example.tmdbexplorer.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tmdbexplorer.Models.MovieByID.MovieById
import com.example.tmdbexplorer.api.MovieApi
import com.example.tmdbexplorer.interfaces.IMovie
import com.example.tmdbexplorer.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieDetailsViewModel(application: Application) : AndroidViewModel(application) {
    val movieById = MutableLiveData<MovieById>()
    private var movieRepository: IMovie

    init {
        MovieApi.init(application)
        movieRepository = MovieRepository()
    }

    fun movieById(movieId : String){
        viewModelScope.launch {
            val movie = MovieRepository().getMovie(movieId)
            movieById.postValue(movie)
        }
    }
}