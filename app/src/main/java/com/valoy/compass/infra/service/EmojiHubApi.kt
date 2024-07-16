package com.valoy.compass.infra.service

import com.valoy.compass.infra.dto.EmojiDTO
import retrofit2.http.GET

interface EmojiHubApi {

    @GET("api/all")
    suspend fun getAll(): List<EmojiDTO>
}