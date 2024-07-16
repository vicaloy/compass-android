package com.valoy.compass.domain.repository

import com.valoy.compass.domain.models.Emoji

interface LocalEmojiRepository {
    suspend fun saveAll(emojis: List<Emoji>)
    suspend fun getAllByCategory(category: String): List<Emoji>
    suspend fun getAllCategories(): List<String>
}
