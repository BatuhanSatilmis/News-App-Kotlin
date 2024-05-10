package com.arvato.batuhansatilmis.thenewsapp.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arvato.batuhansatilmis.thenewsapp.repository.NewsRepository

class NewsViewModelProviderFactory(val app: Application, val newsRepository: Unit) : ViewModelProvider.Factory{

    @Override
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {
         return NewsViewModel(app, newsRepository) as T
  }



}