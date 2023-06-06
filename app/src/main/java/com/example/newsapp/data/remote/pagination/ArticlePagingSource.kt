package com.example.newsapp.data.remote.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.data.remote.Article
import com.example.newsapp.data.remote.Filter
import com.example.newsapp.data.remote.GuardianApiService

class ArticlePagingSource(
    private val apiService: GuardianApiService,
    private val query: String,
    private val filter: Filter
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
            val response = apiService.searchArticles(
                query,
                page = pagerNumber,
                pageSize = params.loadSize,
                filter = filter
            )
            val data = response.response?.results
            val list = response.response?.results ?: listOf()
            LoadResult.Page(
                data = data ?: listOf(),
                prevKey = if (pagerNumber == 1) null else pagerNumber.minus(1),
                nextKey = if (list.isEmpty()) null else pagerNumber.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
