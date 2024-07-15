package com.valoy.compass.di

import android.app.Application
import android.content.Context
import com.valoy.compass.domain.repository.EmojiRepository
import com.valoy.compass.domain.repository.LocalEmojiRepository
import com.valoy.compass.domain.repository.SessionRepository
import com.valoy.compass.infra.EmojiHubAPI
import com.valoy.compass.infra.database.dao.EmojiDAO
import com.valoy.compass.infra.repository.RemoteEmojiRepository
import com.valoy.compass.infra.repository.RoomEmojiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun provideRemoteEmojiRepository(emojiHubAPI: EmojiHubAPI): EmojiRepository {
        return RemoteEmojiRepository(emojiHubAPI)
    }

    @Provides
    fun provideLocalEmojiRepository(emojiDAO: EmojiDAO): LocalEmojiRepository {
        return RoomEmojiRepository(emojiDAO)
    }
}
