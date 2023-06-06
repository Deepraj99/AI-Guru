package com.example.aiguru.model

data class Message(
    val isText: Boolean,
    val isUser: Boolean,
    val message: String
)
