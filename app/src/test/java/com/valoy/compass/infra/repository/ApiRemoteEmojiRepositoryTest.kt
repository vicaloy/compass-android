package com.valoy.compass.infra.repository

import com.valoy.compass.CoroutineMainDispatcherRule
import com.valoy.compass.domain.models.Emoji
import com.valoy.compass.infra.dto.EmojiDTO
import com.valoy.compass.infra.service.EmojiHubApi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ApiRemoteEmojiRepositoryTest {
    private lateinit var repository: ApiRemoteEmojiRepository
    private val emojiHubApi: EmojiHubApi = mockk(relaxed = true)

    @get:Rule
    val coroutineRule = CoroutineMainDispatcherRule(StandardTestDispatcher())
    private val testCoroutineScope = TestScope(coroutineRule.dispatcher)

    @Before
    fun setUp() {
        repository = ApiRemoteEmojiRepository(emojiHubApi)
    }

    @Test
    fun `getAll invoked`() = testCoroutineScope.runTest {
        coEvery { emojiHubApi.getAll() } returns emojisDto
        val result = repository.getAll()
        coVerify(exactly = 1) { emojiHubApi.getAll() }
        assert(result == emojis)
    }

    private companion object {
        val emojisDto = listOf(
            EmojiDTO(
                name = "grinning face",
                category = "Smileys & Emotion",
                group = "face-smiling",
                htmlCode = listOf("&#128512;"),
                unicode = listOf("U+1F600")
            )
        )

        val emojis = listOf(Emoji(category = "Smileys & Emotion", unicode = "U+1F600"))
    }
}