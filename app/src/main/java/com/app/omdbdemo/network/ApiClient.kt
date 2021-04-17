package com.app.omdbdemo.network

import com.app.omdbdemo.BuildConfig
import com.app.omdbdemo.utils.Constants
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import kotlin.math.log

class ApiClient {
    //please use your own url

    companion object {
        val apiService: ApiService by lazy {
            return@lazy ApiClient().service
        }
    }

    private val service: ApiService
        get() {

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val loggerInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }


        // Create a REST adapter which points the omdb API.

        // Create a REST adapter which points the omdb API.
        val httpClient = OkHttpClient.Builder().apply {
            addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original: Request = chain.request()
                    val originalHttpUrl = original.url
                    val url = originalHttpUrl.newBuilder()
                        .addQueryParameter("apikey", BuildConfig.OMDBKey)
                        .build()

                    // Request customization: add request headers
                    val requestBuilder = original.newBuilder()
                        .url(url)
                    val request: Request = requestBuilder.build()
                    return chain.proceed(request)
                }
            })

            addInterceptor(loggerInterceptor)
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(ApiService::class.java)

    }
}