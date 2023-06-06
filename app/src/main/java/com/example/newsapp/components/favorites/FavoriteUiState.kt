package com.example.newsapp.components.favorites

import kotlin.reflect.KFunction0

data class FavoriteUiState(
    val deleteFavArticle: (String) -> Unit
)