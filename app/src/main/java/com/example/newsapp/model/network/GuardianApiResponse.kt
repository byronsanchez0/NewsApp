package com.example.newsapp.model.network


import kotlinx.serialization.Serializable

@Serializable
data class GuardianApiContent(
    val status:String,
    val total:String,
    val startIndex: Int?,
    val pageSize: Int?,
    val currentPage: Int?,
    val pages: Int?,
    val results: List<Article>
)

@Serializable
data class GuardianApiResponse(
    val response: GuardianApiContent
)
