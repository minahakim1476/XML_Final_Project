package com.example.xmlfinalproject

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


class News{
    val articles : List<Article> = listOf()
   // val favouriteArticles: List<FavouriteArticle> = listOf()
}

data class Article(
    val author : String,
    val title : String,
    val description : String?,
    val url : String,
    val urlToImage : String?
)

data class FavouriteArticle(
    var id: String = "",
    val title: String = "",
    val url: String = ""
)

interface ArticleCallable{
    @GET("/v2/top-headlines")
    fun getNews(
        @Query("category") category: String,
        @Query("country") country: String,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ) : Call<News>
}