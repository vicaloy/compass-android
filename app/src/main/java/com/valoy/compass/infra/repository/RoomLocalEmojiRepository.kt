package com.valoy.compass.infra.repository

import com.valoy.compass.domain.models.Emoji
import com.valoy.compass.domain.repository.LocalEmojiRepository
import com.valoy.compass.infra.database.dao.EmojiDAO
import com.valoy.compass.infra.database.entity.EmojiEntity
import javax.inject.Inject

class RoomLocalEmojiRepository @Inject constructor(private val emojiDao: EmojiDAO) :
    LocalEmojiRepository {

    override suspend fun saveAll(emojis: List<Emoji>) {
        emojiDao.insertAll(emojis.map { EmojiEntity.fromModel(it) })
    }

    override suspend fun getAllByCategory(category: String): List<Emoji> =
        emojiDao.getEmojisByCategory(category).map { emojiEntity -> emojiEntity.toModel() }

    override suspend fun getAllCategories(): List<String> = emojiDao.getAllCategories()
}