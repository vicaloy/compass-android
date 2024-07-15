package com.valoy.compass.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.valoy.compass.presentation.emoji.EmojiViewModel
import com.valoy.compass.presentation.emoji.ResultScreen
import com.valoy.compass.presentation.emoji.SearchScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavSections.EMOJI_SEARCH.name) {
        composable(NavSections.EMOJI_SEARCH.name) {
            SearchScreen(
                onSearch = { navController.navigate(NavSections.EMOJI_RESULT.name) },
            )
        }

        composable(
            route = NavSections.EMOJI_RESULT.name,
        ) { entry ->

            val parentEntry = remember(entry) {
                navController.getBackStackEntry(NavSections.EMOJI_SEARCH.name)
            }
            val parentViewModel = hiltViewModel<EmojiViewModel>(parentEntry)
            ResultScreen(parentViewModel)
        }
    }
}
