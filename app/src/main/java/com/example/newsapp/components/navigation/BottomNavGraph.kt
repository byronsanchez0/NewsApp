package com.example.newsapp.components.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsapp.components.search.SearchScreen
import com.example.newsapp.components.search.viewmodel.SearchViewModel
import com.example.newsapp.components.FavoritesScreen
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun BottomNavGraph(
    navHostController: NavHostController,
    contentPadding: PaddingValues
) {

    val viewModel: SearchViewModel = koinViewModel()
    NavHost(
        navController = navHostController,
        startDestination = "moviesearchscreen"
    ) {
        composable("moviesearchscreen") {
            SearchScreen(
                searchUiState = viewModel.searchUiState,
            )
        }
        composable("favorites") {
            FavoritesScreen()
        }
    }
}
