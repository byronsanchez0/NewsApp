package com.example.newsapp.models

import kotlinx.serialization.Serializable

@Serializable
data class GuardianApiContent(
    val status:String,
    val total:String,
    val results: List<NewsModel>
)

@Serializable
data class GuardianApiResponse(
    val response: GuardianApiContent
)