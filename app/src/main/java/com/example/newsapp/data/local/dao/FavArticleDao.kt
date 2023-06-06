package com.example.newsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.data.local.entity.FavArticle
import kotlinx.coroutines.flow.Flow

@Dao
interface FavArticleDao {
    @Query("SELECT * FROM favorite")
    fun getAll(): Flow<List<FavArticle>>

    @Query("SELECT itemId FROM favorite")
    fun getAllIds(): Flow<List<String>>

    @Query("SELECT * FROM favorite WHERE itemId = :id")
    fun getItemById(id: String): FavArticle

    @Query("DELETE FROM favorite")
    suspend fun deleteAllItems()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: FavArticle)

    @Query("DELETE FROM favorite WHERE itemId = :id")
    suspend fun deleteItem(id: String)
}