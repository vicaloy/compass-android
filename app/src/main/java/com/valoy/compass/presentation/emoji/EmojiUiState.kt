package com.valoy.compass.presentation.emoji

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

data class EmojiUiState(
    val isLoading: Boolean = false,
    val isSuccessful: Boolean? = null,
    val result: ResultList = ResultList()
)

@Immutable
data class ResultList(
    val items: List<IResultList> = listOf()
)

@Stable
data class HeaderItem(
    override val headerName: String,
    override val isExpanded: Boolean = false,
) : IResultList

@Stable
data class ContentItem(
    override val headerName: String,
    override val isExpanded: Boolean = false,
    val content: String = "") : IResultList

interface IResultList {
    val isExpanded: Boolean
    val headerName: String
}
