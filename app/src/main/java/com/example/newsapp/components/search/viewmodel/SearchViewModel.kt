package com.example.newsapp.components.search.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.data.local.repository.FavRepo
import com.example.newsapp.components.search.SearchUiState
import com.example.newsapp.components.search.viewmodel.SearchViewModel.FiltersNames.SECTION_CULTURE
import com.example.newsapp.components.search.viewmodel.SearchViewModel.FiltersNames.SECTION_POLITICS
import com.example.newsapp.components.search.viewmodel.SearchViewModel.FiltersNames.SECTION_TECHNOLOGY
import com.example.newsapp.components.search.viewmodel.SearchViewModel.FiltersNames.TAG_ENVIRONMENT_RECYCLING
import com.example.newsapp.components.search.viewmodel.SearchViewModel.FiltersNames.TAG_POLITICS_BLOG
import com.example.newsapp.components.search.viewmodel.SearchViewModel.FiltersNames.TYPE_INTERACTIVE
import com.example.newsapp.components.search.viewmodel.SearchViewModel.FiltersNames.TYPE_LIVE_BLOG
import com.example.newsapp.data.local.entity.FavArticle
import com.example.newsapp.model.data.remote.apirepository.NewsRepository
import com.example.newsapp.network.Article
import com.example.newsapp.network.Filter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(
    private val localRepository: FavRepo,
    private val guardianRepository: NewsRepository
) : ViewModel() {

    private val isLoading = MutableStateFlow(false)
    private val searchQuery = MutableStateFlow("")
    private val favoritesIds = localRepository.getAllIds()
    private val favoritesIdsState = favoritesIds.stateIn(
        viewModelScope, SharingStarted.Lazily,
        emptyList()
    )
    private var filters = MutableStateFlow(Filter(""))

    @OptIn(ExperimentalCoroutinesApi::class)
    val articlesFlow: Flow<PagingData<Article>> = combine(searchQuery, filters) { query, filter ->
        Pair(query, filter)
    }.flatMapLatest { (query, filter) ->
        guardianRepository.searchArticles(query, filter)
    }.cachedIn(viewModelScope)

    fun searchNews(query: String, filter: Filter) {
        searchQuery.value = query
        filters.value = filter
    }

    val uiState = MutableStateFlow(
        SearchUiState(
            news = emptyList(),
            isLoading = false,
            searchNews = ::searchNews,
            onFavoriteClick = { article ->
                viewModelScope.launch() {
                    withContext(Dispatchers.IO){
                        if (favoritesIdsState.value.contains(article.id)) {
                            val favArticle = localRepository.getFavArticleById(article.id)
                            localRepository.deleteFavArticle(favArticle)

                        } else {
                            val newFavArticle = FavArticle(
                                article.id,
                                article.webTitle,
                                article.webPublicationDate,
                                article.type,
                                article.webUrl,
                                article.sectionName,
                                ""
//                                article.fields.thumbnail
                            )
                            localRepository.addFavArticle(newFavArticle)
                        }
                    }

                }
            },
            favoritesIds = favoritesIdsState
        )
    )


    fun filtersGenerator(): List<Filter> {
        val sections = listOf(SECTION_POLITICS, SECTION_TECHNOLOGY, SECTION_CULTURE)
        val types = listOf(TYPE_LIVE_BLOG, TYPE_INTERACTIVE)
        val tags = listOf(TAG_ENVIRONMENT_RECYCLING, TAG_POLITICS_BLOG)

        val sectionFilters =
            sections.map { section -> Filter("Section: $section", section = section) }
        val typeFilters = types.map { type -> Filter("Type: $type", type = type) }
        val tagFilters = tags.map { tag -> Filter("Tag: $tag", tag = tag) }

        return sectionFilters + typeFilters + tagFilters
    }

    object FiltersNames {
        const val SECTION_POLITICS = "politics"
        const val SECTION_TECHNOLOGY = "technology"
        const val SECTION_CULTURE = "culture"
        const val TYPE_LIVE_BLOG = "liveblog"
        const val TYPE_INTERACTIVE = "interactive"
        const val TAG_ENVIRONMENT_RECYCLING = "environment/recycling"
        const val TAG_POLITICS_BLOG = "politics/blog"
    }
}