package com.example.newswiz

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.newswiz.api.Article
import com.example.newswiz.api.NewsApiJSON
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.lang.Exception

const val BASE_URL="https://newsapi.org"
class MainViewModel : ViewModel() {

    suspend  fun makeAPIRequest(category:String): Deferred<MutableList<Article>> = coroutineScope {
        val api=
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(APIRequest::class.java)
        val deferredArticles =  async(Dispatchers.IO) {
            var Articles = mutableListOf<Article>()
            try{
                val response=api.getNews("in",category,"c703b19f3ea04a3bae85dfe8c411164b")
                for (article in response.articles) {
                    if (article.title!=null && article.description!=null && article.urlToImage!=null) {
                        Log.d("MainActivity", "Result + $article")
                        Articles.add(
                            Article(
                                article.title,
                                article.description,
                                article.urlToImage
                            )
                        )
                    }
                }
                Log.d("MainActivity","$Articles.size")
            }
            catch (e: Exception){
                Log.e("MainActivity", e.toString())
            }
            Articles
        }
        return@coroutineScope deferredArticles
    }

}