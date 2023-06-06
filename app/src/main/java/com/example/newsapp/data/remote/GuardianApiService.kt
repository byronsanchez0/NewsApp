package com.example.newsapp.data.remote


interface GuardianApiService {
    suspend fun searchArticles(
        query: String,
        page: Int,
        pageSize: Int,
        filter: Filter
    ): GuardianApiResponse
}
