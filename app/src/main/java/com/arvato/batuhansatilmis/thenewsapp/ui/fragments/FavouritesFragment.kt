package com.arvato.batuhansatilmis.thenewsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arvato.batuhansatilmis.thenewsapp.R
import com.arvato.batuhansatilmis.thenewsapp.adapters.NewsAdapter
import com.arvato.batuhansatilmis.thenewsapp.databinding.FragmentFavouritesBinding
import com.arvato.batuhansatilmis.thenewsapp.databinding.FragmentHeadlinesBinding
import com.arvato.batuhansatilmis.thenewsapp.ui.NewsViewModel


class FavouritesFragment : Fragment() {

    lateinit var newsViewModel : NewsViewModel
    lateinit var newsAdapter : NewsAdapter
    lateinit var binding : FragmentFavouritesBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavouritesBinding.bind(view)

    }
    private fun setupFavouritesRecycler(){
        newsAdapter = NewsAdapter()
        binding.recyclerFavourites.apply {
            adapter = this@FavouritesFragment.newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}