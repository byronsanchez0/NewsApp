package com.example.newsapp.model.repository


import androidx.paging.PagingData
import com.example.newsapp.model.network.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun searchArticles(query: String): Flow<PagingData<Article>>
}