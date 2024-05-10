package com.arvato.batuhansatilmis.thenewsapp.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.arvato.batuhansatilmis.thenewsapp.databinding.ActivityNewsBinding
import com.arvato.batuhansatilmis.thenewsapp.db.ArticleDatabase
import com.arvato.batuhansatilmis.thenewsapp.repository.NewsRepository

class NewsActivity : AppCompatActivity() {


  private lateinit var newsViewModel: NewsViewModel
  private lateinit var binding : ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)


       /* fun newsRepository(articleDatabase: ArticleDatabase) {
             newsRepository(ArticleDatabase(this))
        }

        */

        val newsRepository = NewsRepository(ArticleDatabase(this))

        val viewModelProviderFactory = NewsViewModelProviderFactory(application , newsRepository )
        newsViewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]



    }


}