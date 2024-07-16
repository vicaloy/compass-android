package com.valoy.compass.presentation.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valoy.compass.di.IoDispatcher
import com.valoy.compass.domain.models.Emoji
import com.valoy.compass.domain.repository.LocalEmojiRepository
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

    private val categories = mutableListOf<String>()

    init {
        getEmojis()
    }

    fun onTabClick(index: Int) {
        viewModelScope.launch(dispatcher) {
            tryCatch(
                tryBlock = {
                    val category = categories[index]
                    val emojis =
                        localEmojiRepository.getAllByCategory(category)
                    showEmojis(emojis)
                },
                catchBlock = {
                    _uiState.update { it.copy(hasError = true) }
                }
            )
        }
    }

    private fun getEmojis() {
        viewModelScope.launch(dispatcher) {
            tryCatch(
                tryBlock = {
                    categories.addAll(localEmojiRepository.getAllCategories())
                    val emojis =
                        localEmojiRepository.getAllByCategory(categories.firstOrNull() ?: "")
                    showCategoriesEmojis(emojis)
                },
                catchBlock = {
                    _uiState.update { it.copy(hasError = true) }
                }
            )
        }
    }

    private fun showCategoriesEmojis(emojis: List<Emoji>) {
        _uiState.update {
            it.copy(
                items = mapUnicodes(emojis),
                categories = categories.toImmutableList()
            )
        }
    }

    private fun showEmojis(emojis: List<Emoji>) {
        _uiState.update {
            it.copy(items = mapUnicodes(emojis))
        }
    }

    private fun mapUnicodes(emojis: List<Emoji>) =
        emojis.map {
            String(Character.toChars(it.unicode.substring(2).toInt(16)))
        }.toImmutableList()
}
