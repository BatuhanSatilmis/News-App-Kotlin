package com.arvato.batuhansatilmis.thenewsapp.ui
import com.arvato.batuhansatilmis.thenewsapp.ui.Resource
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arvato.batuhansatilmis.thenewsapp.models.Article
import com.arvato.batuhansatilmis.thenewsapp.models.NewsResponse
import com.arvato.batuhansatilmis.thenewsapp.repository.NewsRepository
import com.bumptech.glide.load.engine.Resource
import kotlinx.coroutines.launch
import retrofit2.Response


class NewsViewModel(app: Application, val newsRepository: NewsRepository) : AndroidViewModel(app) {

    val headlines: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var headlinesPage = 1
    var headlinesResponse : NewsResponse? = null
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


}
