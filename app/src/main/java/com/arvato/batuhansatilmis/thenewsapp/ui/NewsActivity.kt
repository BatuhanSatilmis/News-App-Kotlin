package com.arvato.batuhansatilmis.thenewsapp.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.arvato.batuhansatilmis.thenewsapp.databinding.ActivityNewsBinding
import com.arvato.batuhansatilmis.thenewsapp.db.ArticleDatabase

class NewsActivity : AppCompatActivity() {


  private lateinit var newsViewModel: NewsViewModel
  private lateinit var binding : ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)


        fun newsRepository(articleDatabase: ArticleDatabase) {
             newsRepository(ArticleDatabase(this))
        }

        val viewModelProviderFactory = NewsViewModelProviderFactory(application , newsRepository(ArticleDatabase(this)) )
        newsViewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]

    }


}