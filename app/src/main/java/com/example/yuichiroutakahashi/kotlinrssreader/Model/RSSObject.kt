package com.example.yuichiroutakahashi.kotlinrssreader.Model

/**
 * Created by yuichiroutakahashi on 2017/08/09.
 */

data class RSSObject(val status: String, val feed: Feed, val items: List<Item>)