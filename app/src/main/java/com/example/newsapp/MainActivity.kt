package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.components.MainScreen
import com.example.newsapp.ui.theme.NewsAppTheme

class MainActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "mainscreen") {
                    composable("mainscreen") {
                        MainScreen()
                    }
                }
            }
        }
    }
}
