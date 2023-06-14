package com.example.aiguru.model.response.text

data class Openai(
    val generated_text: String,
    val status: String
)