package com.example.newsapp.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DrawerValue
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.components.navigation.BottomBar
import com.example.newsapp.components.navigation.BottomNavGraph
import com.example.newsapp.components.search.SearchUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current



    Scaffold(
        bottomBar = { BottomBar(navHostController = navController) },
    ) {
        it
        Box(modifier = Modifier.padding(it)) {
            BottomNavGraph(
                navController,
                contentPadding = it
            )
        }

    }


}
