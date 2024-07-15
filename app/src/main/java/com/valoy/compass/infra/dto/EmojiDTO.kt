package com.valoy.compass.infra.dto

import com.valoy.compass.domain.models.Emoji

data class EmojiDTO(
    val name: String,
    val category: String,
    val group: String,
    val htmlCode: List<String>,
    val unicode: List<String>,
)

fun EmojiDTO.toModel() = Emoji(
    unicode = unicode.first(),
    category = category
)