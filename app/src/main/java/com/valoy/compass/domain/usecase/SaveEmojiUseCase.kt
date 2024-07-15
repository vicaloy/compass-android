package com.valoy.compass.domain.usecase

import com.valoy.compass.domain.models.Emoji
import com.valoy.compass.domain.repository.LocalEmojiRepository
import javax.inject.Inject

class SaveEmojiUseCase @Inject constructor(private val localEmojiRepository: LocalEmojiRepository) {
    operator fun invoke(emojis: List<Emoji>) {
        localEmojiRepository.saveAll(emojis)
    }
}
