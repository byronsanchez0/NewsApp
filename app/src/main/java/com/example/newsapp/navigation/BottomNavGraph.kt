package com.example.movieappcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsapp.FavoritesScreen
import com.example.newsapp.NewsViewModel
import com.example.newsapp.SearchNewsScreen


@Composable
fun BottomNavGraph(navHostController: NavHostController, newsViewModel: NewsViewModel) {

    NavHost(
        navController = navHostController,
        startDestination = "moviesearchscreen"
    ) {
        composable("moviesearchscreen") {
            SearchNewsScreen()
        }
        composable("favorites") {
            FavoritesScreen()
        }
    }
}
