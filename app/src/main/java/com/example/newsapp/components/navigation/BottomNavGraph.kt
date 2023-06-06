package com.example.newsapp.components.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.newsapp.components.details.DetailsScreen
import com.example.newsapp.components.search.SearchScreen
import com.example.newsapp.components.search.viewmodel.SearchViewModel
import com.example.newsapp.components.favorites.FavoritesScreen
import com.example.newsapp.components.favorites.FavoritesViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun BottomNavGraph(
    navHostController: NavHostController,
    contentPadding: PaddingValues
) {

    val searchViewModel: SearchViewModel = koinViewModel()
    val favoritesViewModel: FavoritesViewModel = koinViewModel()
    val uiState by searchViewModel.uiState.collectAsState()
    NavHost(
        navController = navHostController,
        startDestination = "moviesearchscreen"
    ) {
        composable("moviesearchscreen") {
            SearchScreen(
                searchUiState = uiState,
                searchViewModel
            )
        }
        composable("favorites") {
            FavoritesScreen(favoritesViewModel, navHostController)
        }
        composable("details/{url}", arguments = listOf(navArgument("url"){type = NavType.StringType}))
        { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url")?:""
            DetailsScreen(webUrl = url)
        }
    }
}
