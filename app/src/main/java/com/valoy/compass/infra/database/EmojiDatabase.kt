package com.valoy.compass.infra.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.valoy.compass.infra.database.dao.EmojiDAO
import com.valoy.compass.infra.database.entity.EmojiEntity

@Database(entities = [EmojiEntity::class], version = 1)
abstract class EmojiDatabase : RoomDatabase() {
    abstract fun getDAO(): EmojiDAO
}