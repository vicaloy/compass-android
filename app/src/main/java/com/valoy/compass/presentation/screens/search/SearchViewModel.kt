package com.valoy.compass.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valoy.compass.di.IoDispatcher
import com.valoy.compass.domain.usecase.SearchUseCase
import com.valoy.compass.util.tryCatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState

    fun onSearchClick() {
        viewModelScope.launch(dispatcher) {
            tryCatch(
                tryBlock = {
                    _uiState.update { it.copy(isLoading = true) }
                    searchUseCase()

                    updateNavigate()
                },
                catchBlock = {
                    updateNavigate()
                }
            )
        }
    }

    private fun updateNavigate() {
        _uiState.update {
            it.copy(isLoading = false, shouldNav = true)
        }
    }

    fun consumeAction() {
        _uiState.update { it.copy(shouldNav = null, isLoading = false) }
    }
}
