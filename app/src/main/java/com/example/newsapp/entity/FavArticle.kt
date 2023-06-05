package com.example.newsapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavArticle (
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
    val sectionName: String?
)