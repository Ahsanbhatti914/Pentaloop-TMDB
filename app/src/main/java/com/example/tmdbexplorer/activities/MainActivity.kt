package com.example.tmdbexplorer.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tmdbexplorer.Adapters.MovieAdapter
import com.example.tmdbexplorer.databinding.ActivityMainBinding
import com.example.tmdbexplorer.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var movieAdapter: MovieAdapter
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding



    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerViewMA.layoutManager = layoutManager

        binding.searchViewMA.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText.isNullOrEmpty()){
                    getPopularMovies()
                }else{
                    getSearchedMovies(newText)
                }
                return true
            }
        })

        binding.textViewNextMA.setOnClickListener { viewModel.getMoviesOfNextPage() }

        binding.textViewPreviousMA.setOnClickListener { viewModel.getMoviesOfPreviousPage() }
        getPopularMovies()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setMovies(){
        viewModel.movies.observe(this, Observer {
            if (!::movieAdapter.isInitialized){
                movieAdapter = MovieAdapter(it.results,this@MainActivity)
                binding.recyclerViewMA.adapter = movieAdapter
            }else{
                movieAdapter.setMoviesList(it.results)
            }
            movieAdapter.notifyDataSetChanged()
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getPopularMovies(){
        viewModel.getPopularMovies()
        setMovies()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getSearchedMovies(movieName: String) {
        viewModel.getSearchedMovies(movieName)
    }
}