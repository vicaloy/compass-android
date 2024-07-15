package com.valoy.compass.domain.repository

import com.valoy.compass.domain.models.Emoji

interface EmojiRepository {
    suspend fun getAll(): List<Emoji>
}