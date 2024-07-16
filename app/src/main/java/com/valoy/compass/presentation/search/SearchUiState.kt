package com.valoy.compass.presentation.search

import androidx.compose.runtime.Stable

@Stable
data class SearchUiState(
    val isLoading: Boolean = false,
    val shouldNavigate: Boolean? = null,
)