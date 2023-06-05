package com.example.aiguru.model.request.text

data class TextRequest(
    val max_tokens: Int,
    val model: String,
    val prompt: String,
    val temperature: Double
)