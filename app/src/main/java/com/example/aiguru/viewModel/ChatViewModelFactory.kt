package com.example.aiguru.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aiguru.repository.ChatRepository
import okhttp3.RequestBody

class ChatViewModelFactory(private val chatRepository: ChatRepository,
                           private val requestBody: RequestBody,
                           private val contentType: String,
                           private val authorization: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChatViewModel(chatRepository, requestBody, contentType, authorization) as T
    }
}