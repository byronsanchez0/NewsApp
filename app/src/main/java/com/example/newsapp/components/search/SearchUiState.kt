package com.example.newsapp.components.search

import com.example.newsapp.data.remote.Article
import com.example.newsapp.data.remote.Filter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

data class SearchUiState(
    val favoritesIds: StateFlow<List<String>>,
    val onFavoriteClick: (Article) -> Unit,
    val news: List<Article> = emptyList(),
    var isLoading: Boolean = false,
    val searchNews: (String, Filter) -> Unit,
    val saveSelectedFilter: (Filter) -> Unit,
    val selectedFilter: Flow<Filter>
)
