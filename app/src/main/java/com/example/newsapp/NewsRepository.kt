package com.example.newsapp


import com.example.newsapp.models.GuardianApiResponse

interface NewsRepository {
    suspend fun searchArticles(query: String): GuardianApiResponse
}