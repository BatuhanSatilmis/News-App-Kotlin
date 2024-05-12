package com.arvato.batuhansatilmis.thenewsapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arvato.batuhansatilmis.thenewsapp.R
import com.arvato.batuhansatilmis.thenewsapp.adapters.NewsAdapter
import com.arvato.batuhansatilmis.thenewsapp.databinding.ActivityNewsBinding
import com.arvato.batuhansatilmis.thenewsapp.databinding.FragmentHeadlinesBinding
import com.arvato.batuhansatilmis.thenewsapp.repository.NewsRepository
import com.arvato.batuhansatilmis.thenewsapp.ui.NewsViewModel
import com.arvato.batuhansatilmis.thenewsapp.util.Constants
import com.arvato.batuhansatilmis.thenewsapp.util.Resource


class HeadlinesFragment : Fragment() {
  //pagination progressbar aswell as error message

  lateinit var newsViewModel: NewsViewModel
  private lateinit var newsViewAdapter: NewsAdapter
  lateinit var newsRepository: NewsRepository
  private lateinit var newsBinding: FragmentHeadlinesBinding
  private lateinit var errorText: TextView
  private lateinit var retryButton : Button
  private lateinit var itemHeadlinesError: CardView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsBinding = FragmentHeadlinesBinding.bind(view)

        itemHeadlinesError = view.findViewById(R.id.itemHeadlinesError)
        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.item_error, null)

        retryButton = view.findViewById(R.id.retryButton)
        errorText = view.findViewById(R.id.errorText)
        setupHeadLinesRecycler() // ->

        newsViewAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_headlinesFragment_to_articleFragment, bundle)
        }

        newsViewModel.headlines.observe(viewLifecycleOwner, Observer{response ->
         when(response) {
             is Resource.Success<*> -> {
                 hideProgressBar()
                 hideErrorMessage()
                 response.data?.let { newsResponse ->
                     newsViewAdapter.differ.submitList(newsResponse.articles.toList())
                     val totalPages = newsResponse.totalResults / Constants.QUERY_PAGE_SIZE +2
                     isLastPage = newsViewModel.headlinesPage == totalPages
                     if(isLastPage){
                         newsBinding.recyclerHeadlines.setPadding(0,0,0,0)
                     }

                 }
             }
             is Resource.Error<*> -> {
           hideProgressBar()
                 response.message?.let {
                     message -> Toast.makeText(activity,"sorry error $message",Toast.LENGTH_SHORT).show()
                     showErrorMessage(message)
                 }
             }
             is Resource.Loading<*> -> {
                showProgressBar()
             }

         }
        })

        retryButton.setOnClickListener {
            newsViewModel.getHeadlines("tr")
        }

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
    private fun setupHeadLinesRecycler(){
        newsViewAdapter = NewsAdapter()
        newsBinding.recyclerHeadlines.apply {
            adapter = this@HeadlinesFragment.newsViewAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@HeadlinesFragment.scrollListener)
        }

    }


}


