package com.example.newsapp.data.local.repository

import android.content.Context
import com.example.newsapp.data.local.dao.FavArticleDao
import com.example.newsapp.data.local.database.FavDataBase
import com.example.newsapp.data.local.entity.FavArticle
import kotlinx.coroutines.flow.Flow

class FavRepo (context: Context) {
    private val favArticleDao: FavArticleDao = FavDataBase.getDatabase(context).favArticleDao()
    suspend fun addFavArticle(favMovie: FavArticle) {
        favArticleDao.insertItem(favMovie)
    }
//    fun getFavArticle(userId: String): <FavArticle> {
//        return favArticleDao.getItemById(userId)
//    }

    fun getAllFav():Flow<List<FavArticle>>{
        return favArticleDao.getAll()
    }
    fun getFavArticleById(userId: String): FavArticle {
        return favArticleDao.getItemById(userId)
    }
    suspend fun deleteFavArticle(favArticle: FavArticle) {
        favArticleDao.deleteItem(favArticle)
    }

    fun getAllIds(): Flow<List<String>>{
        return favArticleDao.getAllIds()
    }
}