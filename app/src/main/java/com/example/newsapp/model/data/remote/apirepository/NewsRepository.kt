package com.example.newsapp.model.data.remote.apirepository


import androidx.paging.PagingData
import com.example.newsapp.network.Article
import com.example.newsapp.network.Filter
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
     fun searchArticles(query: String, filter: Filter): Flow<PagingData<Article>>
}