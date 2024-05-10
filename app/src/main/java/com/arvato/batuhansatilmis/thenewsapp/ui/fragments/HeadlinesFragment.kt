package com.arvato.batuhansatilmis.thenewsapp.ui.fragments

import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.arvato.batuhansatilmis.thenewsapp.R
import com.arvato.batuhansatilmis.thenewsapp.adapters.NewsAdapter
import com.arvato.batuhansatilmis.thenewsapp.databinding.ActivityNewsBinding
import com.arvato.batuhansatilmis.thenewsapp.databinding.FragmentHeadlinesBinding
import com.arvato.batuhansatilmis.thenewsapp.repository.NewsRepository
import com.arvato.batuhansatilmis.thenewsapp.ui.NewsViewModel


class HeadlinesFragment : Fragment() {
  //pagination progressbar aswell as error message

  lateinit var newsViewModel: NewsViewModel
  lateinit var newsViewAdapter: NewsAdapter
  lateinit var newsRepository: NewsRepository
  private lateinit var newsBinding: FragmentHeadlinesBinding
  lateinit var errorText: TextView
  lateinit var itemHeadlinesError: CardView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsBinding = FragmentHeadlinesBinding.bind(view)

    }

    var isError = false
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private fun hideProgressBar(){
        newsBinding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }
    private fun showProgressBar(){
        newsBinding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }
    private fun hideErrorMessage(){
        newsBinding.itemHeadlinesError.errorText.visibility = View.INVISIBLE
        isError = false
    }

    private fun showErrorMessage(message: String){
        newsBinding.itemHeadlinesError.errorText.visibility = View.VISIBLE
        isError = true
    }



}