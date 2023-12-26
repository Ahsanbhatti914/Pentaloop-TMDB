package com.example.tmdbexplorer.api

import android.annotation.SuppressLint
import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.tmdbexplorer.Models.AllMovies.PopularMovieResults
import com.example.tmdbexplorer.Models.MovieByID.MovieById
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.net.URLEncoder
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


@SuppressLint("StaticFieldLeak")
object MovieApi {
    private lateinit var requestQueue: RequestQueue
    private lateinit var builder: GsonBuilder
    private lateinit var gson: Gson
    private lateinit var ctx: Context
    private const val apiKey = "83d01f18538cb7a275147492f84c3698"
    private const val baseUrl = "https://api.themoviedb.org/3/"

    fun init(context: Context) {
        ctx = context
        builder = GsonBuilder()
        gson = builder.create()
        requestQueue = Volley.newRequestQueue(ctx)
    }

    suspend fun getAllMovies(): PopularMovieResults = suspendCoroutine { continuation ->
        val url = "${baseUrl}movie/popular?api_key=$apiKey"
        val request = StringRequest(Request.Method.GET, url, { response ->
            val results = gson.fromJson(response, PopularMovieResults::class.java)
            continuation.resume(results)
        }, { error ->
            continuation.resumeWithException(error)
        })
        requestQueue.add(request)
    }

    suspend fun getByMoviesPage(pageNo:Int): PopularMovieResults = suspendCoroutine { continuation ->
        val url = "${baseUrl}movie/popular?api_key=$apiKey&page=$pageNo"
        val request = StringRequest(Request.Method.GET, url, { response ->
            val results = gson.fromJson(response, PopularMovieResults::class.java)
            continuation.resume(results)
        }, { error ->
            continuation.resumeWithException(error)
        })
        requestQueue.add(request)
    }

    suspend fun getSearchedMovies(movieName:String): PopularMovieResults = suspendCoroutine { continuation ->
        val url = "${baseUrl}search/movie?api_key=$apiKey&query=${URLEncoder.encode(movieName, "UTF-8")}"
        val request = StringRequest(Request.Method.GET, url, { response ->
            val results = gson.fromJson(response, PopularMovieResults::class.java)
            continuation.resume(results)
        }, { error ->
            continuation.resumeWithException(error)
        })
        requestQueue.add(request)
    }

    suspend fun getMovie(movieID:String): MovieById = suspendCoroutine { continuation ->
        val url = "${baseUrl}movie/${movieID}?api_key=$apiKey"
        val request = StringRequest(Request.Method.GET, url, { response ->
            val result = gson.fromJson(response, MovieById::class.java)
            continuation.resume(result)
        }, { error ->
            continuation.resumeWithException(error)
        })
        requestQueue.add(request)
    }
}