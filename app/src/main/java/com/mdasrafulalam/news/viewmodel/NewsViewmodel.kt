package com.mdasrafulal

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.mdasrafulalam.news.db.NewsDB
import com.mdasrafulalam.news.model.Article
import com.mdasrafulalam.news.model.News
import com.mdasrafulalam.news.network.NewsApi
import com.mdasrafulalam.news.repository.NewsRepository
import com.mdasrafulalam.news.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class NewsApiStatus { LOADING, ERROR, DONE }
class NewsViewmodel(application: Application) : AndroidViewModel(application){
    private var repository: NewsRepository
    private val _status = MutableLiveData<NewsApiStatus>()
    private val _topNewsList = MutableLiveData<List<Article>>()
    val topNewsList: LiveData<List<Article>> = _topNewsList
    private var _businessNewsList = MutableLiveData<List<Article>>()
    val businessNewsList: LiveData<List<Article>> = _businessNewsList
    private val _entertainNewsList = MutableLiveData<List<Article>>()
    val entertainNewsList: LiveData<List<Article>> = _entertainNewsList
    private val _healthNewsList = MutableLiveData<List<Article>>()
    val healthNewsList: LiveData<List<Article>> = _healthNewsList
    private val _scienceNewsList = MutableLiveData<List<Article>>()
    val scienceNewsList: LiveData<List<Article>> = _scienceNewsList
    private val _sportsNewsList = MutableLiveData<List<Article>>()
    val sportsNewsList: LiveData<List<Article>> = _sportsNewsList
    private val _technologyNewsList = MutableLiveData<List<Article>>()
    val technologyNewsList: LiveData<List<Article>> = _technologyNewsList



    init {
        val dao = NewsDB.getDB(application).getNewsDao()
        repository = NewsRepository(dao)
        refreshRV()
    }
    fun refreshRV(){
        getTopNews()
        getBusinessNews()
        getEntertainmentNews()
        getHealthNews()
        getScienceNews()
        getSportsNews()
        getTechnologyNews()
        getBookMaredNews()
    }
    fun addNews(news: News) {
        viewModelScope.launch {
            repository.addNews(news)
        }
    }
    fun updateBookMark(news: News){
        viewModelScope.launch {
            repository.updateNews(news)
        }
    }

    fun loadDataIntoDB(category: String, newsList: MutableLiveData<List<Article>>){
        for (i in newsList.value!!){
            var author = i.author
            val content = i.content
            val description = i.description
            val publishedAt = i.publishedAt
            val title  = i.title
            val url = i.url
            var urlToImage = i.urlToImage
            if (author==null){
                author = "Not Found"
            }
            if (urlToImage==null){
                urlToImage = "https://storage.googleapis.com/afs-prod/media/4acdae97a52b43e4a9aaffb4f78f6eae/3000.webp"
            }

            val news = News(
                author = author,
                content = content,
                description = description,
                publishedAt = publishedAt,
                title = title,
                url = url,
                urlToImage = urlToImage,
                category = category)
            addNews(news)

        }
    }
    private fun getTopNews() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                _topNewsList.value = NewsApi.retrofitService.getTopNews(Constants.COUNTRY, Constants.API_KEY).articles
                _status.value = NewsApiStatus.DONE
                Log.d("list", "list: ${_topNewsList.value}")
                if (topNewsList.value!!.size > 0){
                    viewModelScope.launch(Dispatchers.IO) {
                        loadDataIntoDB(Constants.CATEGORY_TOP_NEWS,_topNewsList)
                    }
                }
            } catch (e: Exception) {
                _status.value = NewsApiStatus.ERROR
                _topNewsList.value = listOf()
            }
        }
    }
    private fun getBusinessNews() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                _businessNewsList.value = NewsApi.retrofitService.getCategoryNews(Constants.COUNTRY, Constants.CATEGORY_BUSINESS, Constants.API_KEY).articles
                _status.value = NewsApiStatus.DONE
                if (businessNewsList.value!!.size > 0){
                    viewModelScope.launch(Dispatchers.IO) {
                        loadDataIntoDB(Constants.CATEGORY_BUSINESS,_businessNewsList)
                    }
                }
            } catch (e: Exception) {
                _status.value = NewsApiStatus.ERROR
                _businessNewsList.value = listOf()
            }
        }
    }
    private fun getEntertainmentNews() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                _entertainNewsList.value = NewsApi.retrofitService.getCategoryNews(Constants.COUNTRY, Constants.CATEGORY_ENTERTAINMENT, Constants.API_KEY).articles
                _status.value = NewsApiStatus.DONE
                if (entertainNewsList.value!!.size > 0){
                    viewModelScope.launch(Dispatchers.IO) {
                        loadDataIntoDB(Constants.CATEGORY_ENTERTAINMENT,_entertainNewsList)
                    }
                }
            } catch (e: Exception) {
                _status.value = NewsApiStatus.ERROR
                _entertainNewsList.value = listOf()
            }
        }
    }
    private fun getHealthNews() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                _healthNewsList.value = NewsApi.retrofitService.getCategoryNews(Constants.COUNTRY, Constants.CATEGORY_HEALTH, Constants.API_KEY).articles
                _status.value = NewsApiStatus.DONE
                if (healthNewsList.value!!.size > 0){
                    viewModelScope.launch(Dispatchers.IO) {
                        loadDataIntoDB(Constants.CATEGORY_HEALTH,_healthNewsList)
                    }
                }
            } catch (e: Exception) {
                _status.value = NewsApiStatus.ERROR
                _healthNewsList.value = listOf()
            }
        }
    }
    private fun getScienceNews() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                _scienceNewsList.value = NewsApi.retrofitService.getCategoryNews(Constants.COUNTRY, Constants.CATEGORY_SCIENCE, Constants.API_KEY).articles
                _status.value = NewsApiStatus.DONE
                if (scienceNewsList.value!!.size > 0){
                    viewModelScope.launch(Dispatchers.IO) {
                        loadDataIntoDB(Constants.CATEGORY_SCIENCE,_scienceNewsList)
                    }
                }
            } catch (e: Exception) {
                _status.value = NewsApiStatus.ERROR
                _scienceNewsList.value = listOf()
            }
        }
    }
    private fun getSportsNews() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                _sportsNewsList.value = NewsApi.retrofitService.getCategoryNews(Constants.COUNTRY, Constants.CATEGORY_SPORTS, Constants.API_KEY).articles
                _status.value = NewsApiStatus.DONE
                if (sportsNewsList.value!!.size > 0){
                    viewModelScope.launch(Dispatchers.IO) {
                        loadDataIntoDB(Constants.CATEGORY_SPORTS,_sportsNewsList)
                    }
                }
            } catch (e: Exception) {
                _status.value = NewsApiStatus.ERROR
                _sportsNewsList.value = listOf()
            }
        }
    }
    private fun getTechnologyNews() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                _technologyNewsList.value = NewsApi.retrofitService.getCategoryNews(Constants.COUNTRY, Constants.CATEGORY_TECHNOLOGY, Constants.API_KEY).articles
                _status.value = NewsApiStatus.DONE
                if (technologyNewsList.value!!.size > 0){
                    viewModelScope.launch(Dispatchers.IO) {
                        loadDataIntoDB(Constants.CATEGORY_TECHNOLOGY,_technologyNewsList)
                    }
                }
            } catch (e: Exception) {
                _status.value = NewsApiStatus.ERROR
                _technologyNewsList.value = listOf()
            }
        }
    }

    fun getBookMaredNews() : LiveData<List<News>> = repository.getBookMaredNews()
    fun getAllNews() : LiveData<List<News>> = repository.getAllNews()
    fun getBusinessNews(category: String) : LiveData<List<News>> = repository.getNewsByCategory(category)
    fun getEntertainmentNews(category: String) : LiveData<List<News>> = repository.getNewsByCategory(category)
    fun getHealthNews(category: String) : LiveData<List<News>> = repository.getNewsByCategory(category)
    fun getScienceNews(category: String) : LiveData<List<News>> = repository.getNewsByCategory(category)
    fun getSportsNews(category: String) : LiveData<List<News>> = repository.getNewsByCategory(category)
    fun getTechnologyNews(category: String) : LiveData<List<News>> = repository.getNewsByCategory(category)
}