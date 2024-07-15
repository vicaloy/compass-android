package com.valoy.compass.infra.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.valoy.compass.infra.database.entity.EmojiEntity

@Dao
interface EmojiDAO {
    @Query("SELECT * FROM emojis")
    fun getAll(): List<EmojiEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(emoji: List<EmojiEntity>)
}