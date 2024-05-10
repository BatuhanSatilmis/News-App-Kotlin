package com.arvato.batuhansatilmis.thenewsapp.ui
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arvato.batuhansatilmis.thenewsapp.models.Article
import com.arvato.batuhansatilmis.thenewsapp.models.NewsResponse
import com.arvato.batuhansatilmis.thenewsapp.repository.NewsRepository
import com.arvato.batuhansatilmis.thenewsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException


class NewsViewModel(app: Application, private val newsRepository: NewsRepository) : AndroidViewModel(app) {

    private val headlines: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private var headlinesPage = 1
    private var headlinesResponse : NewsResponse? = null
    private var searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private var searchNewsPage = 1
    private var searchNewsResponse: NewsResponse? = null
    private var newSearchQuery: String? = null
    private var oldSearchQuery: String? = null


    init {
        getHeadlines("tr")
     //   searchNews("/v2/")
    }

  fun getHeadlines(countryCode: String) = viewModelScope.launch {
        headLinesInternet(countryCode) //parsing arguments countrycode
    }
    private fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNewsInternet(searchQuery)
    }
    private fun handleHeadlinesResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        return if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                headlinesPage += 1
                if (headlinesResponse == null) {
                    headlinesResponse = resultResponse
                } else {
                    val oldArticles = headlinesResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                Resource.Success(headlinesResponse ?: resultResponse)
            } ?: Resource.Error("Empty response body", null)
        } else {
            Resource.Error(response.message())
        }
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        return if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if(searchNewsResponse == null || newSearchQuery != oldSearchQuery){
                    searchNewsPage = 1
                    oldSearchQuery = newSearchQuery
                    searchNewsResponse = resultResponse
                } else {
                    searchNewsPage++  // Assuming you meant to increment the page number here
                    val oldSearchArticles = searchNewsResponse?.articles
                    val newSearchArticles = resultResponse.articles
                    oldSearchArticles?.addAll(newSearchArticles)
                }
                Resource.Success(searchNewsResponse ?: resultResponse)
            } ?: Resource.Error("Empty response body", null)  // Return error if response body is null
        } else {
            Resource.Error(response.message() ?: "Unknown error")  // Handle case where response message might be null
        }
    }


    fun addToFavourites(article: Article) = viewModelScope.launch{
        newsRepository.upsert(article)
    }
    fun getFavouriteNews() = newsRepository.getFavouriteNews()
    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    private fun internetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities?.let {
            it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false
    }

    private suspend fun headLinesInternet(countryCode: String){
        headlines.postValue(Resource.Loading())

        try {
            if(internetConnection(this.getApplication())){
                val response = newsRepository.getHeadlines(countryCode, headlinesPage)
                headlines.postValue(handleHeadlinesResponse(response))
            }
            else{
                headlines.postValue(Resource.Error("no internet connection.."))
            }
        }
        catch (t: Throwable){
            when(t){
                is IOException -> headlines.postValue(Resource.Error("unable to connect.."))
                else -> headlines.postValue(Resource.Error("No signal my friend.."))
            }
        }
    }


    private suspend fun searchNewsInternet(searchQuery: String) {
        newSearchQuery = searchQuery
        searchNews.postValue(Resource.Loading())

        try {
            if (internetConnection(getApplication())) {
                val response = newsRepository.searchNews(searchQuery, searchNewsPage)
                searchNews.postValue(handleSearchNewsResponse(response))
            } else {
                searchNews.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> searchNews.postValue(Resource.Error("Unable to connect"))
                else -> searchNews.postValue(Resource.Error("No signal, my friend."))
            }
        }
    }

}


