package com.valoy.compass.infra.repository

import com.valoy.compass.CoroutineMainDispatcherRule
import com.valoy.compass.domain.models.Emoji
import com.valoy.compass.infra.database.dao.EmojiDAO
import com.valoy.compass.infra.database.entity.EmojiEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RoomLocalEmojiRepositoryTest {
    private lateinit var repository: RoomLocalEmojiRepository
    private val emojiDao: EmojiDAO = mockk(relaxed = true)

    @get:Rule
    val coroutineRule = CoroutineMainDispatcherRule(StandardTestDispatcher())
    private val testCoroutineScope = TestScope(coroutineRule.dispatcher)

    @Before
    fun setUp() {
        repository = RoomLocalEmojiRepository(emojiDao)
    }

    @Test
    fun `getAllCategories invoked`() = testCoroutineScope.runTest {
        coEvery { emojiDao.getAllCategories() } returns CATEGORIES
        val result = repository.getAllCategories()
        coVerify(exactly = 1) { emojiDao.getAllCategories() }
        assert(result == CATEGORIES)
    }

    @Test
    fun `getAllByCategory invoked`() = testCoroutineScope.runTest {
        coEvery { emojiDao.getEmojisByCategory(CATEGORY) } returns EMOJIS_ENTITY
        val result = repository.getAllByCategory(CATEGORY)
        coVerify(exactly = 1) { emojiDao.getEmojisByCategory(CATEGORY) }
        assert(result == EMOJIS)
    }

    @Test
    fun `saveAll invoked`() = testCoroutineScope.runTest {
        repository.saveAll(EMOJIS)
        coVerify(exactly = 1) { emojiDao.insertAll(EMOJIS_ENTITY) }
    }

    private companion object {
        const val CATEGORY = "Smileys & Emotion"
        const val UNICODE = "U+1F600"
        val CATEGORIES = listOf(CATEGORY)
        val EMOJIS = listOf(
            Emoji(category = CATEGORY, unicode = UNICODE)
        )
        val EMOJIS_ENTITY = listOf(
            EmojiEntity(category = CATEGORY, unicode = UNICODE)
        )
    }
}