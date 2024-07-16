package com.valoy.compass.presentation.search

import app.cash.turbine.turbineScope
import com.valoy.compass.CoroutineMainDispatcherRule
import com.valoy.compass.domain.usecase.SearchUseCase
import com.valoy.compass.presentation.screens.search.SearchUiState
import com.valoy.compass.presentation.screens.search.SearchViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel
    private val searchUseCase: SearchUseCase = mockk(relaxed = true)

    @get:Rule
    val coroutineRule = CoroutineMainDispatcherRule(StandardTestDispatcher())
    private val testCoroutineScope = TestScope(coroutineRule.dispatcher)

    @Before
    fun setUp() {
        viewModel = SearchViewModel(searchUseCase, coroutineRule.dispatcher)
    }

    @Test
    fun `onSearchClick invoked`() = testCoroutineScope.runTest {
        viewModel.onSearchClick()

        turbineScope {
            val state = viewModel.uiState.testIn(backgroundScope)
            val initial = state.awaitItem()
            val loading = state.awaitItem()
            val navigate = state.awaitItem()

            assertEquals(INITIAL_STATE, initial)
            assertEquals(LOADING_STATE, loading)
            assertEquals(NAVIGATE_STATE, navigate)
        }

        coVerify(exactly = 1) { searchUseCase() }
    }

    @Test
    fun `onSearchClick invoked with error`() = testCoroutineScope.runTest {
        coEvery{ searchUseCase() } throws Exception()

        viewModel.onSearchClick()

        turbineScope {
            val state = viewModel.uiState.testIn(backgroundScope)
            val initial = state.awaitItem()
            val loading = state.awaitItem()
            val navigate = state.awaitItem()

            assertEquals(INITIAL_STATE, initial)
            assertEquals(LOADING_STATE, loading)
            assertEquals(NAVIGATE_STATE, navigate)
        }

        coVerify(exactly = 1) { searchUseCase() }
    }

    @Test
    fun `consumeAction invoked`() = testCoroutineScope.runTest {
        viewModel.consumeAction()

        turbineScope {
            val state = viewModel.uiState.testIn(backgroundScope)
            val clear = state.awaitItem()

            assertEquals(INITIAL_STATE, clear)
        }
    }

    private companion object {
        val INITIAL_STATE = SearchUiState(
            isLoading = false,
            shouldNav = null,
        )
        val LOADING_STATE = SearchUiState(
            isLoading = true,
            shouldNav = null,
        )
        val NAVIGATE_STATE = SearchUiState(
            isLoading = false,
            shouldNav = true,
        )
    }
}