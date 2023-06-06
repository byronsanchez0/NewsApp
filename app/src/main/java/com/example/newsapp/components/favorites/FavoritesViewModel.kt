package com.example.newsapp.components.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.local.entity.FavArticle
import com.example.newsapp.data.local.repository.FavRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(private val localRepository: FavRepo) : ViewModel() {

    val favArticlesList = localRepository.getAllFav()
    val favorites = favArticlesList.stateIn(
        viewModelScope, SharingStarted.Lazily,
        emptyList()
    )

    val uiStateFav = MutableStateFlow(
        FavoriteUiState(
            deleteFavArticle = ::deleteFavArticle
        )
    )

    val uiState = uiStateFav.asStateFlow()

    private fun deleteFavArticle(id: String){

        viewModelScope.launch(Dispatchers.IO ) {
            localRepository.deleteFavArticle(id)
        }
    }




}