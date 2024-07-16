package com.valoy.compass.infra.repository

import com.valoy.compass.domain.models.Emoji
import com.valoy.compass.domain.repository.RemoteEmojiRepository
import com.valoy.compass.infra.service.EmojiHubApi
import com.valoy.compass.infra.dto.toModel
import javax.inject.Inject

class ApiRemoteEmojiRepository @Inject constructor(private val emojiHubApi: EmojiHubApi) :
    RemoteEmojiRepository {
    override suspend fun getAll(): List<Emoji> =
        emojiHubApi.getAll().map { emojiDTO -> emojiDTO.toModel() }
}