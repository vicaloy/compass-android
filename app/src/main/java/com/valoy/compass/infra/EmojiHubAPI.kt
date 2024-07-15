package com.valoy.compass.infra

import com.valoy.compass.infra.dto.EmojiDTO
import retrofit2.http.GET

interface EmojiHubAPI {

    @GET("api/all")
    suspend fun getAll(): List<EmojiDTO>
}