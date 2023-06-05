package com.example.newsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapp.dao.FavArticleDao
import com.example.newsapp.entity.FavArticle

@Database(entities = [FavArticle::class], version = 1)
abstract class FavDataBase : RoomDatabase() {

    companion object {
        fun getDatabase(context: Context): FavDataBase {
            return Room.databaseBuilder(context, FavDataBase::class.java, "fav_database")
                .build()
        }
    }
    abstract fun favArticleDao(): FavArticleDao
}