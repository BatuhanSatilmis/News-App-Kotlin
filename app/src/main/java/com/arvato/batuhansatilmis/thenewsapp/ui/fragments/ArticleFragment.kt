package com.arvato.batuhansatilmis.thenewsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.arvato.batuhansatilmis.thenewsapp.R
import com.arvato.batuhansatilmis.thenewsapp.databinding.FragmentArticleBinding
import com.arvato.batuhansatilmis.thenewsapp.databinding.FragmentSearchBinding
import com.arvato.batuhansatilmis.thenewsapp.ui.NewsActivity
import com.arvato.batuhansatilmis.thenewsapp.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class ArticleFragment : Fragment(R.layout.fragment_article) {

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var binding: FragmentArticleBinding
    private val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleBinding.bind(view)

        newsViewModel = (activity as NewsActivity).newsViewModel
        val article = args.article

        binding.webView.apply{
              webViewClient = WebViewClient()
              article.url?.let {
                  loadUrl(it)
              }
        }
        binding.fab.setOnClickListener{
            newsViewModel.addToFavourites(article)
            Snackbar.make(view,"added to fav",Snackbar.LENGTH_SHORT).show()
        }



    }


}