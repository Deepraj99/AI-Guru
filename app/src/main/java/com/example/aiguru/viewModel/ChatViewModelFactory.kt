package com.example.aiguru.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aiguru.api.ApiInterface

class ChatViewModelFactory(private val apiInterface: ApiInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChatViewModel(apiInterface) as T
    }
}