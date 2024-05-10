package com.arvato.batuhansatilmis.thenewsapp.repository

import androidx.room.Query
import com.arvato.batuhansatilmis.thenewsapp.api.RetrofitInstance
import com.arvato.batuhansatilmis.thenewsapp.db.ArticleDatabase
import com.arvato.batuhansatilmis.thenewsapp.models.Article
import com.arvato.batuhansatilmis.thenewsapp.models.NewsResponse
import retrofit2.Response


class NewsRepository(private val db: ArticleDatabase) {


     suspend fun getHeadlines(countryCode: String, pageNumber: Int) =
         RetrofitInstance.api.getHeadlines(countryCode, pageNumber)


     suspend fun  searchNews(searchQuery: String, pageNumber: Int) =
         RetrofitInstance.api.searchForNews(searchQuery,pageNumber)


   suspend fun upsert(article: Article) = db.getArticleDatabase().upsert(article)

   fun getFavouriteNews() = db.getArticleDatabase().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDatabase().deleteArticle(article)

}