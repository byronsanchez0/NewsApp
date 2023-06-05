package com.example.newsapp.model.network

import com.example.newsapp.model.network.GuardianApiResponse

interface GuardianApiService {
    suspend fun searchArticles(query: String, page: Int): GuardianApiResponse
}