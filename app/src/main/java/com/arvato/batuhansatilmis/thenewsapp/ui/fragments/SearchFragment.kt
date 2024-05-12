package com.arvato.batuhansatilmis.thenewsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.AbsListView
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arvato.batuhansatilmis.thenewsapp.R
import com.arvato.batuhansatilmis.thenewsapp.adapters.NewsAdapter
import com.arvato.batuhansatilmis.thenewsapp.databinding.FragmentSearchBinding
import com.arvato.batuhansatilmis.thenewsapp.ui.NewsActivity
import com.arvato.batuhansatilmis.thenewsapp.ui.NewsViewModel
import com.arvato.batuhansatilmis.thenewsapp.util.Constants


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

    val scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val isNoErrors = !isError
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= 0
            val isNotAtBeggining = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNoErrors &&
                    isNotLoadingAndNotLastPage &&
                    isNotAtBeggining &&
                    isAtLastItem &&
                    isTotalMoreThanVisible &&
                    isScrolling
            if(shouldPaginate) {
                newsViewModel.getHeadlines("tr")
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }

        }
    }



}