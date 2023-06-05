package com.example.newsapp.components.search

import androidx.paging.PagingData
import com.example.newsapp.model.network.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

data class SearchUiState(
    val articlesProvider: StateFlow<Flow<PagingData<Article>>?>,
    val isLoading: StateFlow<Boolean>,
    val searchQuery: StateFlow<String>,
    val favoritesIds: StateFlow<List<String>>,
    val onQueryChange: (String) -> Unit,
    val onFavoriteClick: (Article) -> Unit,
)
