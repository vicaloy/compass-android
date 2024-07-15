package com.valoy.compass.di

import android.content.Context
import androidx.room.Room
import com.valoy.compass.infra.database.EmojiDatabase
import com.valoy.compass.infra.database.dao.EmojiDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): EmojiDatabase {
        return Room.databaseBuilder(
            context,
            EmojiDatabase::class.java, "emoji-database"
        ).build()
    }

    @Provides
    fun provideDao(database: EmojiDatabase): EmojiDAO {
        return database.getDAO()
    }
}