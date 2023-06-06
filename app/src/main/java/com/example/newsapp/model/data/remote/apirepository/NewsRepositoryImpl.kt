package com.example.newsapp.model.data.remote.apirepository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.network.Article
import com.example.newsapp.network.GuardianApiService
import com.example.newsapp.model.data.remote.paging.ArticlePagingSource
import com.example.newsapp.network.Filter
import kotlinx.coroutines.flow.Flow

class NewsRepositoryImpl(private val apiService: GuardianApiService) : NewsRepository {
    override  fun searchArticles(query: String, filter: Filter): Flow<PagingData<Article>> = Pager(
        initialKey = null,
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            prefetchDistance = 1
        ),
        pagingSourceFactory = {
            ArticlePagingSource(
                apiService,
                query,
                filter
            )
        }
    ).flow


}