package com.valoy.compass.presentation.result

import app.cash.turbine.turbineScope
import com.valoy.compass.CoroutineMainDispatcherRule
import com.valoy.compass.domain.models.Emoji
import com.valoy.compass.domain.repository.LocalEmojiRepository
import com.valoy.compass.presentation.screens.result.ResultUiState
import com.valoy.compass.presentation.screens.result.ResultViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ResultViewModelTest {

    private lateinit var viewModel: ResultViewModel
    private val localEmojiRepository: LocalEmojiRepository = mockk(relaxed = true)

    @get:Rule
    val coroutineRule = CoroutineMainDispatcherRule(StandardTestDispatcher())
    private val testCoroutineScope = TestScope(coroutineRule.dispatcher)

    @Test
    fun `on init invoked`() = testCoroutineScope.runTest {
        coEvery { localEmojiRepository.getAllCategories() } returns CATEGORIES
        coEvery { localEmojiRepository.getAllByCategory(CATEGORY) } returns EMOJIS

        setUp()

        turbineScope {
            val state = viewModel.uiState.testIn(backgroundScope)
            val initial = state.awaitItem()
            val categories = state.awaitItem()

            assertEquals(INITIAL_STATE, initial)
            assertEquals(CATEGORIES_STATE, categories)
        }
    }

    @Test
    fun `on init invoked with error`() = testCoroutineScope.runTest {
        coEvery { localEmojiRepository.getAllCategories() } throws Exception()
        coEvery { localEmojiRepository.getAllByCategory(CATEGORY) } throws Exception()

        setUp()

        turbineScope {
            val state = viewModel.uiState.testIn(backgroundScope)
            val initial = state.awaitItem()
            val error = state.awaitItem()

            assertEquals(INITIAL_STATE, initial)
            assertEquals(ERROR_STATE, error)
        }
    }

    @Test
    fun `onTabClick invoked`() = testCoroutineScope.runTest {
        setUp()

        coEvery { localEmojiRepository.getAllCategories() } returns CATEGORIES
        coEvery { localEmojiRepository.getAllByCategory(CATEGORY) } returns EMOJIS

        viewModel.onTabClick(0)

        turbineScope {
            val state = viewModel.uiState.testIn(backgroundScope)
            val initial = state.awaitItem()
            val categories = state.awaitItem()
            assertEquals(INITIAL_STATE, initial)
            assertEquals(CATEGORIES_STATE, categories)
        }
    }

    @Test
    fun `onTabClick invoked with error`() = testCoroutineScope.runTest {
        setUp()

        coEvery { localEmojiRepository.getAllCategories() } throws Exception()
        coEvery { localEmojiRepository.getAllByCategory(CATEGORY) } throws Exception()

        viewModel.onTabClick(0)

        turbineScope {
            val state = viewModel.uiState.testIn(backgroundScope)
            val initial = state.awaitItem()
            val error = state.awaitItem()
            assertEquals(INITIAL_STATE, initial)
            assertEquals(ERROR_STATE, error)
        }
    }

    private fun setUp() {
        viewModel = ResultViewModel(
            localEmojiRepository,
            coroutineRule.dispatcher
        )
    }

    private companion object {
        const val CATEGORY = "Smileys & Emotion"
        const val OTHER_CATEGORY = "Animals & Nature"
        const val UNICODE = "U+1F600"
        val CATEGORIES = listOf(CATEGORY, OTHER_CATEGORY)
        val EMOJIS = listOf(
            Emoji(category = CATEGORY, unicode = UNICODE)
        )
        val EMOJIS_STR = listOf(
            String(Character.toChars(UNICODE.substring(2).toInt(16)))
        )
        val INITIAL_STATE = ResultUiState(
            items = null,
            categories = null,
        )
        val CATEGORIES_STATE = ResultUiState(
            items = EMOJIS_STR.toImmutableList(),
            categories = CATEGORIES.toImmutableList(),
        )
        val ERROR_STATE = ResultUiState(
            hasError = true,
        )
    }
}