package com.valoy.compass.domain.usecase

import com.valoy.compass.domain.repository.LocalEmojiRepository
import com.valoy.compass.domain.repository.RemoteEmojiRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val remoteEmojiRepository: RemoteEmojiRepository,
    private val localEmojiRepository: LocalEmojiRepository
) {
    suspend operator fun invoke() {
        val emojis = remoteEmojiRepository.getAll()
        localEmojiRepository.saveAll(emojis)
    }
}
