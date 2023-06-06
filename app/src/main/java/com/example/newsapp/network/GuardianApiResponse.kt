package com.example.newsapp.network


import kotlinx.serialization.Serializable

@Serializable
data class GuardianApiContent(
    val status:String,
    val total:String?,
    val results: List<Article>?
)

@Serializable
data class GuardianApiResponse(
    val response: GuardianApiContent?
)
