package com.valoy.compass.presentation.emoji

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valoy.compass.di.IoDispatcher
import com.valoy.compass.domain.models.Emoji
import com.valoy.compass.domain.usecase.GetEmojiUseCase
import com.valoy.compass.domain.usecase.SaveEmojiUseCase

import com.valoy.compass.util.tryCatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmojiViewModel @Inject constructor(
    private val getEmojiUseCase: GetEmojiUseCase,
    private val saveEmojiUseCase: SaveEmojiUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(EmojiUiState())
    val uiState = _uiState

    fun onSearchClick(isNetworkAvailable: Boolean) {
        viewModelScope.launch(dispatcher) {
            tryCatch(
                tryBlock = {
                    _uiState.update { it.copy(isLoading = true) }
                    val emojis = getEmojiUseCase(isNetworkAvailable)
                    saveEmojis(isNetworkAvailable, emojis)
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isSuccessful = true,
                            result = ResultList(items = mapToResultList(emojis))
                        )
                    }
                },
                catchBlock = {
                    _uiState.update { it.copy(isSuccessful = false, isLoading = false) }
                }
            )
        }
    }

    fun onHeaderClick(item: IResultList) {
        val items = _uiState.value.result.items
        val results = items.map {
            var result = it
            if (it.headerName == item.headerName) {
                result = when (it) {
                    is HeaderItem ->
                        it.copy(isExpanded = !item.isExpanded)

                    is ContentItem -> it.copy(isExpanded = !item.isExpanded)
                    else -> it
                }
            }
            result
        }
        _uiState.update {
            it.copy(result = ResultList(results.toList()))
        }
    }

    fun consumeAction() {
        _uiState.update {
            it.copy(isSuccessful = null)
        }
    }

    private fun saveEmojis(
        isNetworkAvailable: Boolean,
        emojis: List<Emoji>
    ) {
        if (isNetworkAvailable)
            saveEmojiUseCase(emojis)
    }

    private fun mapToResultList(emojis: List<Emoji>) = emojis.groupBy { it.category }
        .map { (category, emojis) ->
            val header = HeaderItem(category, false)
            val content = emojis.map { ContentItem(category, false, unicodeToEmoji(it.unicode)) }
            listOf(header) + content
        }.flatten()

    private fun unicodeToEmoji(unicode: String) =
        String(Character.toChars(unicode.substring(2).toInt(16)))
}
