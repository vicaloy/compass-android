package com.valoy.compass.infra.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.valoy.compass.domain.models.Emoji

@Entity(tableName = "emojis")
data class EmojiEntity(
    @ColumnInfo(name = "category") val category: String,
    @PrimaryKey @ColumnInfo(name = "unicode") val unicode: String,
) {
    companion object {
        fun fromModel(emoji: Emoji) = EmojiEntity(
            category = emoji.category,
            unicode = emoji.unicode
        )
    }

    fun toModel() = Emoji(
        category = category,
        unicode = unicode
    )
}
