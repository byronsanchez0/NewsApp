package com.example.newsapp.network

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
    val apiUrl: String,

//    @SerialName("fields")
//    val fields:Fields
)
@Serializable
data class Fields(
    @SerialName("thumbnail")
    val thumbnail: String
)

@Serializable
data class Filter(
    val filterName: String,
    val section: String? = null,
    val tag: String? = null,
    val type: String? = null
)




