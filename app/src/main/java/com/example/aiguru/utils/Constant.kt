package com.example.aiguru.utils

import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.aiguru.R
import com.example.aiguru.model.request.image.ImageRequest
import com.example.aiguru.model.request.text.TextRequest
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody

class Constant {
    companion object {
        const val BASE_URL = "https://api.openai.com/v1/"
        private const val API_KEY = "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"

        const val CHAT_TYPE = "CHAT_TYPE"
        const val SUGGESTION = "SUGGESTION"
        const val URL = "URL"

        const val contentType = "application/json"
        const val authorization = "Bearer $API_KEY"


        var requestBodyText: RequestBody? = null
        fun requestBodyText(message: String = "Who are you?") : RequestBody? {
            requestBodyText = RequestBody.create(
                MediaType.parse("application/json"),
                Gson().toJson(
                    TextRequest(
                        250,
                        "text-davinci-003",
                        message,
                        0.7
                    )
                ))
            return requestBodyText
        }

        var requestBodyImage: RequestBody? = null
        fun requestBodyImage(message: String = "A cute baby sea otter") : RequestBody? {
            requestBodyImage = RequestBody.create(
                MediaType.parse("application/json"),
                Gson().toJson(
                    ImageRequest(
                        4,
                        message,
                        "1024x1024"
                    )
                ))
            return requestBodyImage
        }

        fun requestOption() : RequestOptions {
            return RequestOptions()
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.no_image_found)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform()
        }
    }
}