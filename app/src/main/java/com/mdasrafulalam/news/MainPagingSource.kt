package com.mdasrafulalam.news

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mdasrafulalam.news.dao.NewsDao
import com.mdasrafulalam.news.model.News
import com.mdasrafulalam.news.utils.Constants
import kotlinx.coroutines.delay

//class MainPagingSource(
//    private val dao: NewsDao
//) : PagingSource<Int, News>() {
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
//        val page = params.key ?: 0
//
////        return try {
////////            val entities = dao.getTopNews(params.loadSize, page * params.loadSize)
//////            Log.d("MainPagingSource", "load: ${entities.size}")
//////            if (page != 0) delay(1000)
//////            LoadResult.Page(
//////                data = entities,
//////                prevKey = if (page == 0) null else page - 1,
//////                nextKey = if (entities.isEmpty()) null else page + 1
//////            )
////        } catch (e: Exception) {
////            LoadResult.Error(e)
////        }
////    }
//
//        override fun getRefreshKey(state: PagingState<Int, News>): Int? {
//            return state.anchorPosition?.let { anchorPosition ->
//                val anchorPage = state.closestPageToPosition(anchorPosition)
//                anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//            }
//        }
//    }
//}