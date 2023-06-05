package com.example.newsapp.components.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.FavRepo
import com.example.newsapp.components.search.SearchUiState
import com.example.newsapp.model.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class SearchViewModel(
    private val localRepository: FavRepo,
    private val guardianRepository: NewsRepository
) : ViewModel() {

    private val isLoading = MutableStateFlow(false)
    private val searchQuery = MutableStateFlow("")
    private val favoritesIds = localRepository.getAllIds()

    private val paginatedArticlesProvider = combine(
        searchQuery,
        favoritesIds,
    ) { searchQuery, ids, ->
        if(searchQuery.isNotEmpty()){
            isLoading.value = false
            guardianRepository.searchArticles(
                query = searchQuery
            )
        }else {
            null
        }

    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    val searchUiState = SearchUiState(
        searchQuery = searchQuery,
        isLoading = isLoading,
        articlesProvider = paginatedArticlesProvider,
        favoritesIds = favoritesIds.stateIn(
            viewModelScope, SharingStarted.Lazily,
            emptyList()
        ),
        onQueryChange = { query ->
            isLoading.value = true
            searchQuery.value = if (query.length == 1) query.trim() else query
            isLoading.value = false
        },
        onFavoriteClick = { article ->
            println("Le diste favorito")
        }
    )


//    combine(
//        searchQuery,
//    ) { searchQuery ->
//        if (searchQuery.isNotEmpty()) {
//            isLoading.value = false
//            guardianRepository.searchArticles(
//                query = searchQuery
//            )
//        }
//    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

//    private val favoritesIds = localRepository.getAllIds()
//
//    private val _uiState = MutableStateFlow(
//        SearchUiState(
//            articles = emptyList(),
//            isLoading = false,
//        )
//    )
//    val uiState = _uiState.asStateFlow()
//
//    fun searchArticles(query: String) {
//        viewModelScope.launch {
//            _uiState.value = _uiState.value.copy(isLoading = true)
//            val response = guardianRepository.searchArticles(query)
//            _uiState.value = SearchUiState(articles = response.response.results, isLoading = false)
//        }
//    }
}