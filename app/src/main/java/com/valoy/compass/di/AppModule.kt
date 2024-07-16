package com.valoy.compass.di

import android.app.Application
import android.content.Context
import com.valoy.compass.domain.repository.RemoteEmojiRepository
import com.valoy.compass.domain.repository.LocalEmojiRepository
import com.valoy.compass.infra.service.EmojiHubApi
import com.valoy.compass.infra.database.dao.EmojiDAO
import com.valoy.compass.infra.repository.ApiRemoteEmojiRepository
import com.valoy.compass.infra.repository.RoomLocalEmojiRepository
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
    fun provideRemoteEmojiRepository(emojiHubAPI: EmojiHubApi): RemoteEmojiRepository {
        return ApiRemoteEmojiRepository(emojiHubAPI)
    }

    @Provides
    fun provideLocalEmojiRepository(emojiDAO: EmojiDAO): LocalEmojiRepository {
        return RoomLocalEmojiRepository(emojiDAO)
    }
}
