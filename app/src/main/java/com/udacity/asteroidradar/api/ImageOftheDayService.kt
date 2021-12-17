package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private val moshi = Moshi.Builder()
    .build()

private val imageRetrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

interface ImageApiService{
    @GET("planetary/apod")
    suspend fun getImageofTheDay(@Query("api_key")apiKey: String):PictureOfDay
}

object ImageOftheDayApi{
    val retrofitService : ImageApiService by lazy{
        imageRetrofit.create(ImageApiService::class.java)
    }
}