package com.example.newsapp.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id: String,

    val type: String,

    @SerialName("sectionId")
    val sectionId: String,

    @SerialName("sectionName")
    val sectionName: String,

    @SerialName("webPublicationDate")
    val webPublicationDate: String,

    @SerialName("webTitle")
    val webTitle: String,

    @SerialName("webUrl")
    val webUrl: String,

    @SerialName("apiUrl")
    val apiUrl: String
)

