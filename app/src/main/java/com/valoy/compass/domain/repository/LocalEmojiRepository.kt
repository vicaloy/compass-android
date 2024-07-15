package com.valoy.compass.domain.repository

import com.valoy.compass.domain.models.Emoji

interface LocalEmojiRepository : EmojiRepository {
    fun saveAll(emojis: List<Emoji>)
    override suspend fun getAll(): List<Emoji>
}