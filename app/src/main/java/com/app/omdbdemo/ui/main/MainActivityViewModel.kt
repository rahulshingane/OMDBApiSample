package com.app.omdbdemo.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.app.omdbdemo.network.ApiCallBack
import com.app.omdbdemo.network.ApiClient.Companion.apiService
import kotlinx.coroutines.Dispatchers

/**
 * View model call for performing all business logic and api call for [MainActivity]
 */
class MainActivityViewModel : ViewModel() {

    var totalItems = 0
    private var currentPage = 1
    var searchKey = ""

    fun getMovies() = liveData(Dispatchers.IO) {
        emit(ApiCallBack.loading(data = null))
        try {
            emit(ApiCallBack.success(data = apiService.getMovieList(searchKey, currentPage)))
        } catch (exception: Exception) {
            emit(ApiCallBack.error(data = null, message = exception.message ?: "Something went wrong. Please try later..."))
        }
    }

    fun updatePageCount() = currentPage++

    fun resetPageCount() {
        currentPage = 1
    }

    fun isPageRemaining(listSize:Int) = listSize<totalItems

    fun isFirstPage() = currentPage==1

}