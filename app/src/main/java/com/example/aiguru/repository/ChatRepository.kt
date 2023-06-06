package com.example.aiguru.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.aiguru.api.ApiInterface
import com.example.aiguru.model.response.image.ImageResponse
import com.example.aiguru.model.response.text.TextResponse
import com.example.aiguru.utils.NetworkResult
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject

class ChatRepository(private val apiInterface: ApiInterface) {

    private val textMutableLiveData = MutableLiveData<NetworkResult<TextResponse>>()
    val textLiveData: LiveData<NetworkResult<TextResponse>>
    get() = textMutableLiveData

//    private val imageMutableLiveData = MutableLiveData<ImageResponse>()
//    val imageLiveData: LiveData<ImageResponse>
//    get() = imageMutableLiveData

    suspend fun getText(contentType: String, authorization: String, requestBody: RequestBody) {
        val response = apiInterface.getText(contentType, authorization, requestBody)

        if (response.isSuccessful  &&  response.body() != null) {
            textMutableLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            textMutableLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            textMutableLiveData.postValue(NetworkResult.Error("Something went wrong!"))
        }
    }

//    suspend fun getImage(contentType: String, authorization: String, requestBody: RequestBody) {
//        val data = apiInterface.getImage(contentType, authorization, requestBody)
//
//        if(data.body() != null) {
//            imageMutableLiveData.postValue(data.body())
//        }
//    }
}