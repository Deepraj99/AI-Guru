package com.example.aiguru.model.response.image

data class Openai(
    val items: List<Item>,
    val status: String
)