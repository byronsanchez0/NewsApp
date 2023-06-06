package com.example.newsapp.components.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.newsapp.components.search.SearchUiState
import com.example.newsapp.data.local.entity.FavArticle
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import java.net.URLEncoder

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FavoritesScreen(favoritesViewModel: FavoritesViewModel, navHostController: NavHostController) {

    val favoritesFromViewModel by favoritesViewModel.favorites.collectAsState()
    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            count = favoritesFromViewModel.size,
            state = pagerState,
            itemSpacing = 5.dp
        ) { page ->
            Card(
                modifier = Modifier
                    .size(400.dp)
                    .padding(16.dp)
                    .graphicsLayer {
                        val pageOffset =
                            (pagerState.currentPage - page) + pagerState.currentPageOffset
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                            .also { scale ->
                                scaleX = scale
                                scaleY = scale
                            }
                    }
            ) {
                val favArticle = favoritesFromViewModel[page]
                Item(favArticle = favArticle, favoritesViewModel, navHostController )
            }
        }
    }
}

@Composable
fun Item(favArticle: FavArticle, favoritesViewModel: FavoritesViewModel, navHostController: NavHostController) {
    val url = URLEncoder.encode(favArticle.webUrl, "UTF-8")
    val favUiState by favoritesViewModel.uiState.collectAsState()
    Box {
        Image(
            painter = rememberAsyncImagePainter(favArticle.fields),
            contentDescription = "Movie Poster",
            modifier = Modifier
                .fillMaxSize()
                .clickable { navHostController.navigate("details/${url}") }
        )
        FloatingActionButton(onClick = { favUiState.deleteFavArticle(favArticle.itemId) },
            content = {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Delete")
            }
        )
    }

}

