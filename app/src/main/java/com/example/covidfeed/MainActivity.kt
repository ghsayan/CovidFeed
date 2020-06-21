package com.example.covidfeed

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray


class MainActivity : AppCompatActivity() {
//    "https://newsapi.org/v2/top-headlines?pageSize=100&category=health&apiKey=45b0ddd7db0d4c1390cca3705196e7fd&country=in"
    private var newsAPI: String ="&apiKey=45b0ddd7db0d4c1390cca3705196e7fd"
    private val baseUrl: String ="https://newsapi.org/v2/top-headlines?pageSize=100&category=health"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        val code = listOf("ae", "ar", "at" ,"au","be","bg","br", "ca","ch", "cn","co","cu", "cz","de","eg","fr","gb", "gr","hk","hu", "id","ie","il", "in","it", "jp","kr","lt","lv","ma", "mx","my", "ng","nl ","no","nz","ph","pl","pt", "ro", "rs", "ru", "sa", "se","sg","si", "sk", "th","tr","tw","ua","us","ve","za");
        val country = listOf("United Arab Emirates","Argentina","Austria","Australia","Belgium","Bulgaria","Brazil","Canada","Switzerland","China","Colombia","Cuba","Czechia","Germany","Egypt","France", "United Kingdom of Great Britain and Northern Ireland","Greece","Hong Kong","Hungary","Indonesia","Ireland","Israel","India","Italy","Japan","Korea","Lithuania","Latvia","	Morocco","Mexico","Malaysia","Nigeria","Netherlands","Norway","New Zealand","Philippines","Poland","Portugal","Romania","Serbia","Russian Federation","Saudi Arabia","Sweden","Singapore","Slovenia","Slovakia","	Thailand","Turkey","Taiwan, Province of China","Ukraine","United States of America","Venezuela","South Africa")
        val countryAdapter = ArrayAdapter(this, R.layout.list_item, country)
        (etCountry.editText as? AutoCompleteTextView)?.setAdapter(countryAdapter)

        btnGo.setOnClickListener{
            if(etCountryText?.text.toString()=="") {
                etCountryText?.error = "Enter country name"
                return@setOnClickListener
            }

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)

            val list=ArrayList<News>()
            val queue: RequestQueue = Volley.newRequestQueue(this)
            val pin: String = "&country="+code[country.indexOf(etCountryText.text.toString())]
            Log.i("Test",pin)
            val myURL: String = baseUrl+newsAPI+pin
            Log.i("Test",myURL)
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, myURL, null,
                Response.Listener { response ->
                    var items: JSONArray =response.getJSONArray("articles")
                    for (i in 0 until items.length()) {
                        var author: String ="No Data"
                        var desc: String ="No Data"
                        var image: String=""
                        var title:String="Title"
                        title = items.getJSONObject(i).getString("title")
                        desc = items.getJSONObject(i).getString("description")
                        author = items.getJSONObject(i).getJSONObject("source").getString("name")
                        image = items.getJSONObject(i).getString("urlToImage")
                        list.add(News(image,title,author,desc))
                    }
                    rvList.apply {
                        layoutManager= LinearLayoutManager(this@MainActivity)
                        adapter=NewsAdapter(list)
                    }
                },
                Response.ErrorListener { error ->
                    Log.i("trial",error.message.toString()+" "+error.cause.toString())
                }
            )
            queue.add(jsonObjectRequest)

        }
    }
}
