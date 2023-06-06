package com.example.newsapp.data.remote.apirepository


import androidx.paging.PagingData
import com.example.newsapp.data.remote.Article
import com.example.newsapp.data.remote.Filter
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
     fun searchArticles(query: String, filter: Filter): Flow<PagingData<Article>>
}