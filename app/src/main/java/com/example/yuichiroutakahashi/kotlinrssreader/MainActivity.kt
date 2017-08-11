package com.example.yuichiroutakahashi.kotlinrssreader

import android.app.ProgressDialog
import android.os.Bundle
import android.provider.Contacts
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.yuichiroutakahashi.kotlinrssreader.Adapter.FeedAdapter
import com.example.yuichiroutakahashi.kotlinrssreader.Common.FeedRepository
import com.example.yuichiroutakahashi.kotlinrssreader.Common.RetrofitServiceGenerator
import com.example.yuichiroutakahashi.kotlinrssreader.Model.RSSObject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.experimental.CoroutineContext
import kotlinx.coroutines.experimental.android.UI



class MainActivity : AppCompatActivity() {

    private val RSS_link = "http://www.nytimes.com/services/xml/rss/nyt/Science.xml"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "NEWS"

        setSupportActionBar(toolbar)

        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager

        loadRSS()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.menu_refresh) {
            loadRSS()
        }

        return true
    }

    private fun loadRSS() = launch(UI) {

        val dialog = ProgressDialog(this@MainActivity)

        dialog.setMessage("Please wait...")
        dialog.show()

        val repository = FeedRepository(RetrofitServiceGenerator.createService())
        val response = repository.feeds(RSS_link).await()

        dialog.dismiss()

        response?.body()?.let { rssObject ->
            Log.d("Response", "${rssObject.toString()}")
            val adapter = FeedAdapter(rssObject, baseContext)
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }
}
