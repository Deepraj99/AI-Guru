package com.example.aiguru.model.request.image

data class ImageRequest(
    val n: Int,
    val prompt: String,
    val size: String
)