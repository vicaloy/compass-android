package com.valoy.compass.presentation.screens.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valoy.compass.di.IoDispatcher
import com.valoy.compass.domain.models.Emoji
import com.valoy.compass.domain.repository.LocalEmojiRepository
import com.valoy.compass.presentation.exceptions.CategoriesNotFoundException
import com.valoy.compass.presentation.exceptions.EmojisNotFoundException
import com.valoy.compass.util.tryCatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val localEmojiRepository: LocalEmojiRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(ResultUiState())
    val uiState = _uiState

    init {
        getEmojis()
    }

    fun onTabClick(index: Int) {
        viewModelScope.launch(dispatcher) {
            tryCatch(
                tryBlock = {
                    val categories = getCategories()
                    val category = categories[index]
                    val emojis = getEmojisByCategory(category)
                    showCategoriesEmojis(emojis, categories)
                },
                catchBlock = {
                    _uiState.update { it.copy(hasError = true) }
                }
            )
        }
    }

    private suspend fun getEmojisByCategory(category: String) =
        (localEmojiRepository.getAllByCategory(category).takeIf { it.isNotEmpty() }
            ?: throw EmojisNotFoundException())

    private suspend fun getCategories(): List<String> {
        val categories =
            localEmojiRepository.getAllCategories().takeIf { it.isNotEmpty() }
                ?: throw CategoriesNotFoundException()
        return categories
    }

    private fun getEmojis() {
        viewModelScope.launch(dispatcher) {
            tryCatch(
                tryBlock = {
                    val categories = getCategories()
                    val emojis = getEmojisByCategory(categories.firstOrNull() ?: "")
                    showCategoriesEmojis(emojis, categories)
                },
                catchBlock = {
                    _uiState.update { it.copy(hasError = true) }
                }
            )
        }
    }

    private fun showCategoriesEmojis(emojis: List<Emoji>, categories: List<String>) {
        _uiState.update {
            it.copy(
                items = mapUnicodes(emojis),
                categories = categories.toImmutableList()
            )
        }
    }

    private fun mapUnicodes(emojis: List<Emoji>) =
        emojis.map {
            String(Character.toChars(it.unicode.substring(2).toInt(16)))
        }.toImmutableList()
}
