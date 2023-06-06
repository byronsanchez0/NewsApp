package com.example.newsapp.network


interface GuardianApiService {
    suspend fun searchArticles(
        query: String,
        page: Int,
        pageSize: Int,
        filter: Filter
    ): GuardianApiResponse
}