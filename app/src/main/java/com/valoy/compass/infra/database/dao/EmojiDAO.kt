package com.valoy.compass.infra.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.valoy.compass.infra.database.entity.EmojiEntity

@Dao
interface EmojiDAO {
    @Query("SELECT DISTINCT category FROM emojis")
    suspend fun getAllCategories(): List<String>

    @Query("SELECT * FROM emojis WHERE category = :category")
    suspend fun getEmojisByCategory(category: String): List<EmojiEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(emoji: List<EmojiEntity>)
}