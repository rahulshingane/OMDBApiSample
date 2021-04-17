package com.app.omdbdemo.utils

class Constants {

    companion object{
        const val SNAKBAR_TYPE_ERROR = 1
        const val SNAKBAR_TYPE_SUCCESS = 2
        const val SNAKBAR_TYPE_MESSAGE = 3

        const val BASE_URL = "http://www.omdbapi.com"
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}