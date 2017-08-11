package com.example.yuichiroutakahashi.kotlinrssreader.Model

/**
 * Created by yuichiroutakahashi on 2017/08/09.
 */

data class Item(val title: String, val pubDate: String, val link: String, val guid: String, val author: String, val thumbnail: String, val description: String, val content: String, val enclosure: Object, val categories: List<String>)