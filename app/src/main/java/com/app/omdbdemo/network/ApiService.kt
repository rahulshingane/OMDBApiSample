package com.app.omdbdemo.network

import com.app.omdbdemo.model.MovieDetailResponse
import com.app.omdbdemo.model.MovieResponse
import retrofit2.http.*

interface ApiService {

    @GET("?type=movie")
    suspend fun getMovieList(@Query("s") searchKey:String, @Query("page") page:Int): MovieResponse

    @GET(".")
    suspend fun getMovieDetail(@Query("i") movieId:String): MovieDetailResponse
}