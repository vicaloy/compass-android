package com.valoy.compass.infra.repository

import com.valoy.compass.domain.models.Emoji
import com.valoy.compass.domain.repository.EmojiRepository
import com.valoy.compass.infra.EmojiHubAPI
import com.valoy.compass.infra.dto.toModel
import javax.inject.Inject

class RemoteEmojiRepository @Inject constructor(private val emojiHubAPI: EmojiHubAPI) :
    EmojiRepository {
    override suspend fun getAll(): List<Emoji> =
        emojiHubAPI.getAll().map { emojiDTO -> emojiDTO.toModel() }

}