package com.valoy.compass.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.valoy.compass.presentation.result.ResultScreen
import com.valoy.compass.presentation.search.SearchScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavSections.EMOJI_SEARCH.name) {
        composable(NavSections.EMOJI_SEARCH.name) {
            SearchScreen(
                onNavigate = { navController.navigate(NavSections.EMOJI_RESULT.name) },
            )
        }

        composable(
            route = NavSections.EMOJI_RESULT.name,
        ) {
            ResultScreen()
        }
    }
}
