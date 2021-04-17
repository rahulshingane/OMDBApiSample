package com.app.omdbdemo.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.app.omdbdemo.network.ApiCallBack
import com.app.omdbdemo.network.ApiClient
import com.app.omdbdemo.ui.main.MainActivity
import kotlinx.coroutines.Dispatchers

/**
 * View model call for performing all business logic and api call for [MovieDetailsActivity]
 */
class MovieDetailViewModel: ViewModel() {

    fun getMovieDetail(movieId:String) = liveData(Dispatchers.IO) {
        emit(ApiCallBack.loading(data = null))
        try {
            emit(
                ApiCallBack.success(
                    data = ApiClient.apiService.getMovieDetail(
                        movieId = movieId
                    )
                )
            )
        } catch (exception: Exception) {
            emit(ApiCallBack.error(data = null, message = exception.message ?: "Something went wrong. Please try later..."))
        }
    }

}