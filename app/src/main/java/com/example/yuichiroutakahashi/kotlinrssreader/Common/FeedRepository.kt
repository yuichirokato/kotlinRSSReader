package com.example.yuichiroutakahashi.kotlinrssreader.Common

import com.example.yuichiroutakahashi.kotlinrssreader.Interface.FeedService
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import ru.gildor.coroutines.retrofit.awaitResponse


class FeedRepository(val service: FeedService) {

    fun feeds(rssUrl: String) = async(CommonPool) {
        service.getFeed(rssUrl).awaitResponse()
    }
}
