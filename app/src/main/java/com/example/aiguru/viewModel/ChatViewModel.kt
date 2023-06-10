package com.example.aiguru.viewModel

import androidx.lifecycle.ViewModel
import com.example.aiguru.api.ApiInterface
import com.example.aiguru.model.response.text.TextResponse
import com.example.aiguru.utils.Constant
import com.example.aiguru.utils.NetworkResult
import org.json.JSONObject

class ChatViewModel(private val apiInterface: ApiInterface) : ViewModel() {

    var textResponse : NetworkResult<TextResponse>? = null
    suspend fun  getText() {
        val response = apiInterface.getText(
            Constant.contentType,
            Constant.authorization, Constant.requestBodyText!!)

        textResponse = if (response.isSuccessful  &&  response.body() != null) {
            NetworkResult.Success(response.body()!!)
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            NetworkResult.Error(errorObj.getString("message: "))
        } else {
            NetworkResult.Error("Something went wrong!")
        }
    }
}