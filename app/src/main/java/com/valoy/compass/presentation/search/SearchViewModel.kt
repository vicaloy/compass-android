package com.valoy.compass.presentation.search

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

    fun onSearchClick(isNetworkAvailable: Boolean) {
        viewModelScope.launch(dispatcher) {
            tryCatch(
                tryBlock = {
                    if(isNetworkAvailable){
                        _uiState.update { it.copy(isLoading = true) }
                        searchUseCase()
                    }

                    _uiState.update {
                        it.copy(isLoading = false, shouldNavigate = true)
                    }
                },
                catchBlock = {
                    _uiState.update { it.copy(shouldNavigate = false, isLoading = false) }
                }
            )
        }
    }

    fun consumeAction() {
        _uiState.update { it.copy(shouldNavigate = null) }
    }
}
