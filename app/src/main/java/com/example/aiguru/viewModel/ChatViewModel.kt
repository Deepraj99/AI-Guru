package com.example.aiguru.viewModel

import androidx.lifecycle.ViewModel
import com.example.aiguru.api.ApiInterface
import com.example.aiguru.model.response.image.ImageResponse
import com.example.aiguru.model.response.text.TextResponse
import com.example.aiguru.utils.Constant
import com.example.aiguru.utils.NetworkResult

class ChatViewModel(private val apiInterface: ApiInterface) : ViewModel() {

    var textResponse : NetworkResult<TextResponse>? = null
    var imageResponse: NetworkResult<ImageResponse>? = null
    suspend fun  getText() {
        textResponse = NetworkResult.Loading()
        val response = apiInterface.getText(
            Constant.contentType,
            Constant.authorization, Constant.requestBodyText!!)

        textResponse = if (response.isSuccessful  &&  response.body() != null) {
            NetworkResult.Success(response.body()!!)
        } else if (response.errorBody() != null) {
            NetworkResult.Error("Enter a valid input!")
        } else {
            NetworkResult.Error("No internet connection!")
        }
    }

    suspend fun getImage() {
        imageResponse = NetworkResult.Loading()
        val response = apiInterface.getImage(
            Constant.contentType,
            Constant.authorization,
            Constant.requestBodyImage!!)

        imageResponse = if (response.isSuccessful  &&  response.body() != null) {
            NetworkResult.Success(response.body()!!)
        } else if (response.errorBody() != null) {
            NetworkResult.Error("Enter a valid input!")
        } else {
            NetworkResult.Error("No internet connection!")
        }
    }
}