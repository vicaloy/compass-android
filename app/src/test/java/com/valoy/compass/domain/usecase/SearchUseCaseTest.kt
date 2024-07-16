package com.valoy.compass.domain.usecase

import com.valoy.compass.CoroutineMainDispatcherRule
import com.valoy.compass.domain.repository.LocalEmojiRepository
import com.valoy.compass.domain.repository.RemoteEmojiRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class SearchUseCaseTest {

    private lateinit var searchUseCase: SearchUseCase
    private val remoteEmojiRepository: RemoteEmojiRepository = mockk(relaxed = true)
    private val localEmojiRepository: LocalEmojiRepository = mockk(relaxed = true)

    @get:Rule
    val coroutineRule = CoroutineMainDispatcherRule(StandardTestDispatcher())
    private val testCoroutineScope = TestScope(coroutineRule.dispatcher)

    @Test
    fun `onSearchUseCase invoked`() = testCoroutineScope.runTest {
        searchUseCase = SearchUseCase(remoteEmojiRepository, localEmojiRepository)
        searchUseCase()
        coVerify(exactly = 1) { remoteEmojiRepository.getAll() }
        coVerify(exactly = 1) { localEmojiRepository.saveAll(any()) }
    }
}