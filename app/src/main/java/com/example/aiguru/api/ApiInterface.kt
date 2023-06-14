package com.example.aiguru.api

import com.example.aiguru.model.response.image.ImageResponse
import com.example.aiguru.model.response.text.TextResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {

    @POST("text/generation")
    suspend fun getText(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: RequestBody
    ) : Response<TextResponse>

    @POST("image/generation")
    suspend fun getImage(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: RequestBody
    ) : Response<ImageResponse>
}