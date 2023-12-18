package com.example.tmdbexplorer.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tmdbexplorer.Models.AllMovies.PopularMovieResults
import com.example.tmdbexplorer.MovieApi
import com.example.tmdbexplorer.interfaces.IMovie
import com.example.tmdbexplorer.repository.MovieRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(
    application
) {

    val movies = MutableLiveData<PopularMovieResults>()
    private var pageNo:Int = 1

    private var movieRepository: IMovie

    init {
        MovieApi.init(application)
        movieRepository = MovieRepository()
    }

    fun getPopularMovies(){
        viewModelScope.launch {
            val response = movieRepository.getAllPopularMovies()
            movies.postValue(response)
        }
    }

    fun getMoviesOfNextPage(){
        pageNo++
        viewModelScope.launch {
            val response = movieRepository.getMoviesByPage(pageNo)
            movies.postValue(response)
        }
    }

    fun getMoviesOfPreviousPage(){
        if(pageNo > 1){
            pageNo--
            viewModelScope.launch {
                val response = movieRepository.getMoviesByPage(pageNo)
                movies.postValue(response)
            }
        }
    }

    fun getSearchedMovies(movieName : String){
        viewModelScope.launch {
            val response = movieRepository.getAllSearchedMovies(movieName)
            movies.postValue(response)
        }
    }
}