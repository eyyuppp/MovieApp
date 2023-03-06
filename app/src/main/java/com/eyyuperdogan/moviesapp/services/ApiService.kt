package com.eyyuperdogan.moviesapp.services

import com.eyyuperdogan.moviesapp.models.MovieListResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

  @GET("movie/popular?api_key=write api key")
    fun getMovieList():Call<MovieListResponse>

}