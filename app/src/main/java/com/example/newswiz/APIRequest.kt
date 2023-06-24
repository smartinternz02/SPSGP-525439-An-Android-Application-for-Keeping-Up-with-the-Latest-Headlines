package com.example.newswiz

import com.example.newswiz.api.NewsApiJSON
import retrofit2.http.GET
import retrofit2.http.Query

interface APIRequest {
    @GET("/v2/top-headlines")
    suspend fun getNews(@Query("country")country:String,@Query("category")category:String,@Query("apiKey") apiKey:String): NewsApiJSON

}