package com.example.newsapp.components.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.rememberAsyncImagePainter
import com.example.newsapp.R
import com.example.newsapp.components.search.viewmodel.SearchViewModel
import com.example.newsapp.network.Filter
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchUiState: SearchUiState,
    searchViewModel: SearchViewModel
) {
    val isLoading = searchUiState.isLoading
    val searchValue = searchUiState.searchNews
    val articlesProvider = searchViewModel.articlesFlow
    val articles = articlesProvider?.collectAsLazyPagingItems()
    val query = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val markedFilter = remember { mutableStateOf(Filter("")) }

    val saveMarkedFilter = searchUiState.saveSelectedFilter
    val selectedFilter by searchUiState.selectedFilter.collectAsState(initial = Filter(""))

    val filters = remember { searchViewModel.filtersGenerator() }
    val filtersList = remember { mutableStateOf(false) }
    val favoritesIdsState by searchUiState.favoritesIds.collectAsState()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        TextField(
            value = query.value,
            onValueChange = { newValue ->
                query.value = newValue
                scope.launch {
                    searchValue(newValue, markedFilter.value)
                }
            },
            label = { Text("Search") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                scope.launch {
                    searchValue(query.value, markedFilter.value)
                }
            }),
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    modifier = Modifier
                        .clickable {
                            scope.launch {
                                searchValue(query.value, markedFilter.value)
                            }
                        }
                )
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Box {
                Button(onClick = { filtersList.value = !filtersList.value }) {
                    Text(selectedFilter.filterName.ifEmpty { stringResource(R.string.select_a_filter) })
                }
                DropdownMenu(
                    expanded = filtersList.value,
                    onDismissRequest = { filtersList.value = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    filters.forEach { filter ->
                        DropdownMenuItem(
                            onClick = {
                                saveMarkedFilter(filter)
                                filtersList.value = false
                                scope.launch { searchValue(query.value, filter) }

                            }, text = {
                                Text(text = filter.filterName)
                            })
                    }
                }
            }
        }


        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.wrapContentSize(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    articles?.let {
                        items(
                            count = articles.itemCount,
                            key = articles.itemKey(),
                            contentType = articles.itemContentType(),
                        ) { index ->
                            val article = articles[index]
                            val painter = rememberAsyncImagePainter(article?.fields?.thumbnail)
                            Image(
                                painter = painter,
                                contentDescription = stringResource(R.string.new_image),
                                modifier = Modifier
                                    .height(250.dp)
                                    .width(350.dp)
                                    .padding(horizontal = 30.dp),
                                contentScale = ContentScale.FillWidth
                            )
                            if (article != null)
                                Row {
                                    Text(
                                        text = article?.webTitle ?: "",
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .weight(1f),
                                        softWrap = true
                                    )
                                    Icon(
                                        modifier = Modifier
                                            .padding(top = 16.dp, end = 8.dp)
                                            .weight(0.5f)
                                            .clickable { searchUiState.onFavoriteClick(article) },
                                        painter = painterResource(
                                            if (favoritesIdsState.contains(article.id)) R.drawable.fillfav else R.drawable.emptyfav
                                        ),
                                        contentDescription = null
                                    )
                                }
                            Spacer(modifier = Modifier.height(2.dp))
                        }
                    } ?: item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "No Articles")
                        }
                    }
                }
            }
        }

    }
}
