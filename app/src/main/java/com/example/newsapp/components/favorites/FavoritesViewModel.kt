package com.example.newsapp.components.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.local.repository.FavRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class FavoritesViewModel(private val localRepository: FavRepo) : ViewModel() {

    val favArticlesList = localRepository.getAllFav()
    val favorites = favArticlesList.stateIn(
        viewModelScope, SharingStarted.Lazily,
        emptyList()
    )



}