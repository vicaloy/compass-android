package com.valoy.compass.presentation.screens.search

import androidx.compose.runtime.Stable

@Stable
data class SearchUiState(
    val isLoading: Boolean = false,
    val shouldNav: Boolean? = null,
)