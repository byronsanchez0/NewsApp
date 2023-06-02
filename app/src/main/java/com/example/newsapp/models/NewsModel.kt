package com.example.newsapp.models

import kotlinx.serialization.Serializable

@Serializable
data class NewsModel(
    val webTitle: String,
    val sectionName: String,
    val webUrl: String
)
