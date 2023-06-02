package com.example.newsapp


import com.example.newsapp.models.GuardianApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType

import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class GuardianApiServiceImpl(): NewsRepository {
    val client: HttpClient by lazy { KtorClient()}
    private fun  KtorClient(): HttpClient{

        return HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
                )
            }
            install(HttpTimeout) {
                requestTimeoutMillis = TIMEOUT
                connectTimeoutMillis = TIMEOUT
                socketTimeoutMillis = TIMEOUT
            }
            install(DefaultRequest) {
//                host = BuildConfig.GUARDIAN_API_BASE_URL
                url {
                    protocol = URLProtocol.HTTPS
//                    parameters.append("key", BuildConfig.GUARDIAN_API_KEY)
                }
                contentType(ContentType.Application.Json)
            }
        }
    }
//    suspend fun searchNews(query: String): List<GuardianResult> {
//        val response = KtorClient().use { client ->
//
//        }
////        val apiKey = "34a8034"
////        val response = RetrofitClient.omdbApiService.searchMovies(apiKey, query)
////        if (response.isSuccessful){
////            val searchResponse = response.body()
////            return searchResponse?.search ?: emptyList()
////        } else {
////            throw Exception("Failed to search movies: ${response.errorBody()}")
////        }
//
//    }
    companion object{
        private const val TIMEOUT = 60_000L
    }

    override suspend fun searchArticles(query: String): GuardianApiResponse {
        val url = "https://content.guardianapis.com/search?q=$query&api-key=32c3c682-48a4-4333-af67-3fa3eaccda37"
        return client.get(url).body()
    }

    suspend fun testSearchArticles() {
        val guardianApiService = GuardianApiServiceImpl()
        val query = "technology"
        val response = guardianApiService.searchArticles(query)

        println("Status: ${response.response.status}")
        println("Total Results: ${response.response.results}")
        println("Results:")
        response.response.results.forEach { article ->
            println("Title: ${article.webTitle}")
            println("URL: ${article.webUrl}")
            println()
        }
    }
}
