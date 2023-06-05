package com.example.newsapp.model.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import com.example.newsapp.model.network.Article
import com.example.newsapp.model.network.GuardianApiService
import java.io.IOException

class ArticlePagingSource (
    private val apiService: GuardianApiService,
    private val query: String,
) : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val pagerNumber = params.key ?: 1
            val response = apiService.searchArticles(query, pagerNumber)
            val data = response.response.results
            LoadResult.Page(
                data = data,
                null,
                nextKey = if (data.isNotEmpty()) pagerNumber + 1 else null
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

}