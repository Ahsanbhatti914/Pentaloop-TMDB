package com.example.tmdbexplorer.Models.AllMovies

data class PopularMovieResults(
    val page: Int,
    val results: List<PopularMovie>,
    val total_pages: Int,
    val total_results: Int
)
