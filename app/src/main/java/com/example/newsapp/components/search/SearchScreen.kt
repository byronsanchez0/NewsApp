package com.example.newsapp.components.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchUiState: SearchUiState
) {
    val isLoading by searchUiState.isLoading.collectAsState()
    val searchValue by searchUiState.searchQuery.collectAsState()
    val articlesProvider by searchUiState.articlesProvider.collectAsState()
    val articles = articlesProvider?.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        TextField(
            value = searchValue,
            onValueChange = { newValue -> searchUiState.onQueryChange(newValue) },
            label = { Text("Search") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
            modifier = Modifier.fillMaxWidth()
        )
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
                            Text(text = article?.webTitle ?: "", modifier = Modifier.padding(8.dp))
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
