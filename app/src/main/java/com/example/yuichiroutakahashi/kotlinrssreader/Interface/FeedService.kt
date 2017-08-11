package com.example.yuichiroutakahashi.kotlinrssreader.Interface

import com.example.yuichiroutakahashi.kotlinrssreader.Model.RSSObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FeedService {

    @GET("api.json")
    fun getFeed(@Query("rss_url") url: String): Call<RSSObject>
}