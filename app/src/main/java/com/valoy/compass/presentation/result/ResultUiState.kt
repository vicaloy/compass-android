package com.valoy.compass.presentation.result

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList

@Stable
data class ResultUiState(
    val hasError: Boolean = false,
    val items: ImmutableList<String>? = null,
    val categories: ImmutableList<String>? = null,
)