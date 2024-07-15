package com.valoy.compass.infra.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.valoy.compass.domain.models.Emoji

@Entity(tableName = "emojis")
data class EmojiEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "unicode") val unicode: String,
) {
    companion object {
        fun fromModel(emoji: Emoji) = EmojiEntity(
            id = 0,
            category = emoji.category,
            unicode = emoji.unicode
        )
    }

    fun toModel() = Emoji(
        category = category,
        unicode = unicode
    )
}
