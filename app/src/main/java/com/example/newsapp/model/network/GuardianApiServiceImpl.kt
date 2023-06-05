package com.example.newsapp.model.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class GuardianApiServiceImpl(private val client: HttpClient): GuardianApiService {
    override suspend fun searchArticles(query: String, page: Int): GuardianApiResponse {
        val url = "https://content.guardianapis.com/search?q=$query&api-key=7de6160d-4534-4d15-9db6-dc0ea468d6e3&page=${page}&page-size=20"
        return client.get(url).body()
    }

}