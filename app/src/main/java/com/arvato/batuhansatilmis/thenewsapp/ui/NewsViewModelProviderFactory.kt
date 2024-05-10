package com.arvato.batuhansatilmis.thenewsapp.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arvato.batuhansatilmis.thenewsapp.repository.NewsRepository

@Suppress("UNCHECKED_CAST")
class NewsViewModelProviderFactory(val app: Application, private val newsRepository: NewsRepository) : ViewModelProvider.Factory{

    @Override
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {
         return NewsViewModel(app, newsRepository) as T
  }

}