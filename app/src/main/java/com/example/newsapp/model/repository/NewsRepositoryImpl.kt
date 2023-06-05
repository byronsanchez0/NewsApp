package com.example.newsapp.model.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.model.network.Article
import com.example.newsapp.model.network.GuardianApiService
import com.example.newsapp.model.paging.ArticlePagingSource
import kotlinx.coroutines.flow.Flow

class NewsRepositoryImpl(private val apiService: GuardianApiService) : NewsRepository {
    override suspend fun searchArticles(query: String): Flow<PagingData<Article>> = Pager(
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
            )
        }
    ).flow
}