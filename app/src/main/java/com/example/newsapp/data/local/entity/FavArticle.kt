package com.example.newsapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity(tableName = "favorite")
data class FavArticle(
    @PrimaryKey
    val itemId: String,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "date")
    val date: String?,

    @ColumnInfo(name = "type")
    val type: String?,

    @ColumnInfo(name = "webUrl")
    val webUrl: String?,

    @ColumnInfo(name = "sectionName")
    val sectionName: String?,

    @ColumnInfo("thumbnail")
    val thumbnail: String?


)