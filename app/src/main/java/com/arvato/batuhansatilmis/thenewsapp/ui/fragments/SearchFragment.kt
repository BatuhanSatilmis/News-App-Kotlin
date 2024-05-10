package com.arvato.batuhansatilmis.thenewsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.arvato.batuhansatilmis.thenewsapp.R
import com.arvato.batuhansatilmis.thenewsapp.databinding.FragmentSearchBinding
import com.arvato.batuhansatilmis.thenewsapp.ui.NewsActivity
import com.arvato.batuhansatilmis.thenewsapp.ui.NewsViewModel


class SearchFragment : Fragment(R.layout.fragment_search) {

      private lateinit var newsViewModel: NewsViewModel
      private lateinit var binding: FragmentSearchBinding
      val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        newsViewModel = (activity as NewsActivity).newsViewModel
        val article = args.article

        val  webViewClient = WebViewClient()


    }


}