package com.example.yuichiroutakahashi.kotlinrssreader

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.yuichiroutakahashi.kotlinrssreader.Adapter.FeedAdapter
import com.example.yuichiroutakahashi.kotlinrssreader.Common.RetrofitServiceGenerator
import com.example.yuichiroutakahashi.kotlinrssreader.Model.RSSObject
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    private fun loadRSS() {

        var dialog = ProgressDialog(this@MainActivity)
        val call = RetrofitServiceGenerator.createService().getFeed(RSS_link)

        dialog.setMessage("Please wait...")
        dialog.show()

        call.enqueue(object : Callback<RSSObject> {

            override fun onFailure(call: Call<RSSObject>?, t: Throwable?) {

                dialog.dismiss()
                Log.d("ResponseError", "failed")
            }

            override fun onResponse(call: Call<RSSObject>?, response: Response<RSSObject>?) {

                dialog.dismiss()

                if (response?.isSuccessful == true) {
                    response?.body()?.let { rssObject ->
                        Log.d("Response", "${rssObject.toString()}")
                        val adapter = FeedAdapter(rssObject, baseContext)
                        recyclerView.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
//
//        val loadRSSAsync = object: AsyncTask<String, String, String>() {
//
//            var mDialog = ProgressDialog(this@MainActivity)
//
//            override fun onPostExecute(result: String?) {
//                mDialog.dismiss()
//
//                var rssObject: RSSObject
//
//                rssObject = Gson().fromJson<RSSObject>(result, RSSObject::class.java!!)
//                val adapter = FeedAdapter(rssObject, baseContext)
//
//                recyclerView.adapter = adapter
//
//                adapter.notifyDataSetChanged()
//            }
//
//            override fun onPreExecute() {
//                mDialog.setMessage("Please wait...")
//                mDialog.show()
//            }
//
//            override fun doInBackground(vararg params: String): String {
//
//                val result: String
//                val http = HttpDataHandler()
//
//                result = http.getHTTPDataHandler(params[0])
//
//                return result
//            }
//        }
//
//        val urlGetData = StringBuilder(RSS_to_JSON_API)
//        urlGetData.append(RSS_link)
//
//        loadRSSAsync.execute(urlGetData.toString())
    }
}
