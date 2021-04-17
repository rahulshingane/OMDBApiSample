package com.app.omdbdemo.network

import com.app.omdbdemo.utils.Constants

data class ApiCallBack<out T>(val status: Constants.Status, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T): ApiCallBack<T> =
            ApiCallBack(status = Constants.Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String?): ApiCallBack<T> =
            ApiCallBack(status = Constants.Status.ERROR, data = data, message = message)

        fun <T> loading(data: T?): ApiCallBack<T> =
            ApiCallBack(status = Constants.Status.LOADING, data = data, message = null)
    }
}

