package com.arvato.batuhansatilmis.thenewsapp.ui
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import com.arvato.batuhansatilmis.thenewsapp.ui.Resource
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arvato.batuhansatilmis.thenewsapp.models.Article
import com.arvato.batuhansatilmis.thenewsapp.models.NewsResponse
import com.arvato.batuhansatilmis.thenewsapp.repository.NewsRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOError
import java.io.IOException


class NewsViewModel(app: Application, private val newsRepository: NewsRepository) : AndroidViewModel(app) {

    val headlines: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private var headlinesPage = 1
    private var headlinesResponse : NewsResponse? = null
    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    val searchNewsResponse: NewsResponse? = null
    val newSearchQuery: String? = null
    val oldSearchQuery: String? = null


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
                is IOException -> headlines.postValue(Resource.Error("unable to connect my friend.."))
                else -> headlines.postValue(Resource.Error("No signal my friend.."))
            }
        }
    }


}
