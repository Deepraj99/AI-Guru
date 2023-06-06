package com.example.aiguru.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aiguru.model.response.image.ImageResponse
import com.example.aiguru.model.response.text.TextResponse
import com.example.aiguru.repository.ChatRepository
import com.example.aiguru.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class ChatViewModel(private val chatRepository: ChatRepository,
                    private val requestBody: RequestBody,
                    private val contentType: String,
                    private val authorization: String) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            chatRepository.getText(contentType, authorization, requestBody)
//            chatRepository.getImage(contentType, authorization, requestBody)
        }
    }

    val getText: LiveData<NetworkResult<TextResponse>>
    get() = chatRepository.textLiveData


//    val getImage: LiveData<ImageResponse>
//    get() = chatRepository.imageLiveData
}