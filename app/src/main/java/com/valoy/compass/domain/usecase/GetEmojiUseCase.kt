package com.valoy.compass.domain.usecase

import com.valoy.compass.domain.repository.EmojiRepository
import com.valoy.compass.domain.repository.LocalEmojiRepository
import com.valoy.compass.domain.repository.SessionRepository
import javax.inject.Inject

class GetEmojiUseCase @Inject constructor(
    private val remoteEmojiRepository: EmojiRepository,
    private val localEmojiRepository: LocalEmojiRepository
) {
    suspend operator fun invoke(useRemote: Boolean) =
        if (useRemote) remoteEmojiRepository.getAll() else localEmojiRepository.getAll()
}
