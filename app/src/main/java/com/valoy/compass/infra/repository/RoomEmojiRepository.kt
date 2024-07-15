package com.valoy.compass.infra.repository

import com.valoy.compass.domain.models.Emoji
import com.valoy.compass.domain.repository.EmojiRepository
import com.valoy.compass.domain.repository.LocalEmojiRepository
import com.valoy.compass.infra.EmojiHubAPI
import com.valoy.compass.infra.database.dao.EmojiDAO
import com.valoy.compass.infra.database.entity.EmojiEntity
import com.valoy.compass.infra.dto.toModel
import javax.inject.Inject

class RoomEmojiRepository @Inject constructor(private val emojiDao: EmojiDAO) :
    LocalEmojiRepository {

    override fun saveAll(emojis: List<Emoji>) {
        emojiDao.insertAll(emojis.map { EmojiEntity.fromModel(it) })
    }

    override suspend fun getAll(): List<Emoji> =
        emojiDao.getAll().map { emojiEntity -> emojiEntity.toModel() }

}