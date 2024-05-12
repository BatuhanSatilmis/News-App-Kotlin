package com.arvato.batuhansatilmis.thenewsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.arvato.batuhansatilmis.thenewsapp.R
import com.arvato.batuhansatilmis.thenewsapp.adapters.NewsAdapter
import com.arvato.batuhansatilmis.thenewsapp.databinding.FragmentSearchBinding
import com.arvato.batuhansatilmis.thenewsapp.ui.NewsActivity
import com.arvato.batuhansatilmis.thenewsapp.ui.NewsViewModel


class SearchFragment : Fragment() {

      private lateinit var newsViewModel: NewsViewModel
      private lateinit var binding: FragmentSearchBinding
      lateinit var newsAdapter: NewsAdapter
      lateinit var retryButton: Button
      lateinit var errorText: TextView
      lateinit var itemSearchError: CardView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)



    }

    var isError = false
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private fun hideProgressBar(){
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }
    private fun showProgressBar(){
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }
    private fun hideErrorMessage(){
        binding.itemSearchError.errorText.visibility = View.INVISIBLE
        isError = false
    }

    private fun showErrorMessage(message: String){
        binding.itemSearchError.errorText.visibility = View.VISIBLE
        isError = true
    }





}