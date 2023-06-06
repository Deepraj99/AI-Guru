package com.example.aiguru.utils

import com.example.aiguru.model.request.image.ImageRequest
import com.example.aiguru.model.request.text.TextRequest
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody

class Constant {
    companion object {
        const val BASE_URL = "https://api.openai.com/v1/"
        const val API_KEY = "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"

        const val contentType = "application/json"
        const val authorization = "Bearer $API_KEY"



        val requestBodyImage: RequestBody = RequestBody.create(MediaType.parse("application/json"),
            Gson().toJson(
                ImageRequest(
                    1,
                    "A cute baby sea otter",
                "1024x1024"
                )
            ))
    }
}