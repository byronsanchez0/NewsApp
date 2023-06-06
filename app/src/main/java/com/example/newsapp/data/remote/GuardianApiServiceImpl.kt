package com.example.newsapp.data.remote

import com.example.newsapp.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class GuardianApiServiceImpl(private val client: HttpClient) :
    GuardianApiService {
    override suspend fun searchArticles(
        query: String,
        page: Int,
        pageSize: Int,
        filter: Filter
    ): GuardianApiResponse {
        val url =
            "https://content.guardianapis.com/search"

        return client.get(url) {
            parameter(QUERY, query)
            parameter(KEY, BuildConfig.GUARDIAN_API_KEY)
            parameter(SHOW_FIELDS, THUMBNAIL)
            parameter(PAGE_SIZE, pageSize)
            parameter(PAGE, page)
            filter.section?.let { parameter(SECTION, it) }
            filter.tag?.let { parameter(TAG, it) }
            filter.type?.let { parameter(TYPE, it) }
        }.body()
    }

    companion object {
        const val KEY = "api-key"
        const val BASE_URL = "search"
        const val QUERY = "q"
        const val PAGE = "page"
        const val PAGE_SIZE = "page-size"
        const val SHOW_FIELDS = "show-fields"
        const val THUMBNAIL = "thumbnail"
        const val SECTION = "section"
        const val TAG = "tag"
        const val TYPE = "type"
    }
}
