package com.example.aiguru.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.aiguru.api.ApiInterface
import com.example.aiguru.model.response.image.ImageResponse
import com.example.aiguru.model.response.text.TextResponse
import okhttp3.RequestBody

class ChatRepository(private val apiInterface: ApiInterface) {

    private val textMutableLiveData = MutableLiveData<TextResponse>()
    val textLiveData: LiveData<TextResponse>
    get() = textMutableLiveData

    private val imageMutableLiveData = MutableLiveData<ImageResponse>()
    val imageLiveData: LiveData<ImageResponse>
    get() = imageMutableLiveData

    suspend fun getText(contentType: String, authorization: String, requestBody: RequestBody) {
        val data = apiInterface.getText(contentType, authorization, requestBody)

        if (data.body() != null) {
            textMutableLiveData.postValue(data.body())
        }
    }

    suspend fun getImage(contentType: String, authorization: String, requestBody: RequestBody) {
        val data = apiInterface.getImage(contentType, authorization, requestBody)

        if(data.body() != null) {
            imageMutableLiveData.postValue(data.body())
        }
    }
}