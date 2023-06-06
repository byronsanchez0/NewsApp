package com.example.newsapp.components.search

import androidx.paging.PagingData
import com.example.newsapp.network.Article
import com.example.newsapp.network.Filter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class SearchUiState(
//    val articlesProvider: StateFlow<Flow<PagingData<Article>>?>,
//    val isLoading: StateFlow<Boolean>,
//    val searchQuery: (String, Filter) -> Unit,
    val favoritesIds: StateFlow<List<String>>,
//    val onQueryChange: (String) -> Unit,
    val onFavoriteClick: (Article) -> Unit,
    val news: List<Article> = emptyList(),
    var isLoading: Boolean = false,
    val searchNews: (String,Filter) -> Unit
)
