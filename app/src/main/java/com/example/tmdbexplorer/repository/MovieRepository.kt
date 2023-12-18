package com.example.tmdbexplorer.repository

import com.example.tmdbexplorer.Models.AllMovies.PopularMovieResults
import com.example.tmdbexplorer.Models.MovieByID.MovieById
import com.example.tmdbexplorer.MovieApi
import com.example.tmdbexplorer.interfaces.IMovie


class MovieRepository() :IMovie {

    override suspend fun getAllPopularMovies(): PopularMovieResults  {
        return MovieApi.getAllMovies()
    }

    override suspend fun getMoviesByPage(pageNo:Int): PopularMovieResults {
        return MovieApi.getByMoviesPage(pageNo)
    }

    override suspend fun getAllSearchedMovies(movieName: String): PopularMovieResults {
        return MovieApi.getSearchedMovies(movieName)
    }

    override suspend fun getMovie(movieId: String): MovieById {
        return MovieApi.getMovie(movieId)
    }
}