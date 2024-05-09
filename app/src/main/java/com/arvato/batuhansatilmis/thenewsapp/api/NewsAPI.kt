package com.arvato.batuhansatilmis.thenewsapp.api

import com.arvato.batuhansatilmis.thenewsapp.models.NewsResponse
import com.arvato.batuhansatilmis.thenewsapp.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getHeadlines(
       @Query("country")
       countryCode: String = "us",
       @Query("page")
       pageNumber: Int = 1,
       @Query("apiKey")
       apiKey: String = API_KEY
    ): Response<NewsResponse>


    @GET("v2/top-headlines")
    suspend fun getHeadlinesv2(
        @Query("country")
        countryCode: String = "tr",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun getTechCrunch(
        @Query("sources")
        countryCode: String = "techcrunch",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>


    @GET("v2/everything")
     suspend fun searchForNews(
         @Query("q")
         searchQuery: String,
         @Query("page")
         pageNumber: Int = 1,
         apiKey: String = API_KEY
     ): Response<NewsResponse>



}