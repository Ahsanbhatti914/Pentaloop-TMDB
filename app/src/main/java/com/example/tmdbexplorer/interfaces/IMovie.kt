package com.example.tmdbexplorer.interfaces

import com.example.tmdbexplorer.Models.AllMovies.PopularMovieResults
import com.example.tmdbexplorer.Models.MovieByID.MovieById

interface IMovie {
    suspend fun getAllPopularMovies():PopularMovieResults
    suspend fun getMoviesByPage(pageNo:Int):PopularMovieResults
    suspend fun getAllSearchedMovies(movieName:String):PopularMovieResults
    suspend fun getMovie(movieId:String): MovieById
}