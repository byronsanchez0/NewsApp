package com.example.newsapp

import android.content.Context
import com.example.newsapp.dao.FavArticleDao
import com.example.newsapp.database.FavDataBase
import com.example.newsapp.entity.FavArticle
import kotlinx.coroutines.flow.Flow

class FavRepo (context: Context) {
    private val favArticleDao: FavArticleDao = FavDataBase.getDatabase(context).favArticleDao()
    suspend fun addFavArticle(favMovie: FavArticle) {
        favArticleDao.insertItem(favMovie)
    }
    fun getFavArticle(userId: String): Flow<FavArticle> {
        return favArticleDao.getItemById(userId)
    }
    suspend fun deleteFavArticle(favArticle: FavArticle) {
        favArticleDao.deleteItem(favArticle)
    }

    fun getAllIds(): Flow<List<String>>{
        return favArticleDao.getAllIds()
    }
}