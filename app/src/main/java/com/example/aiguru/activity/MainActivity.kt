package com.example.aiguru.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aiguru.R
import com.example.aiguru.api.ApiInterface
import com.example.aiguru.api.RetrofitHelper
import com.example.aiguru.databinding.ActivityMainBinding
import com.example.aiguru.model.request.image.ImageRequest
import com.example.aiguru.model.request.text.TextRequest
import com.example.aiguru.repository.ChatRepository
import com.example.aiguru.utils.Constant.Companion.API_KEY
import com.example.aiguru.viewModel.ChatViewModel
import com.example.aiguru.viewModel.ChatViewModelFactory
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var chatViewModel: ChatViewModel

    lateinit var apiInterface: ApiInterface
    lateinit var chatRepository: ChatRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initialize()

        val requestBody = RequestBody.create(MediaType.parse("application/json"),
            Gson().toJson(
                TextRequest(
                    250,
                    "text-davinci-003",
                    "Hi, how are you?",
                    0.7
                )
            ))

//        val requestBody1 = RequestBody.create(MediaType.parse("application/json"),
//            Gson().toJson(
//                ImageRequest(
//                    1,
//                    "A cute baby sea otter",
//                "1024x1024"
//                )
//            ))

        val contentType = "application/json"
        val authorization = "Bearer $API_KEY"

        chatViewModel = ViewModelProvider(this, ChatViewModelFactory(chatRepository, requestBody, contentType, authorization))[ChatViewModel::class.java]

        chatViewModel.getText.observe(this, Observer {

            val res = it.choices.first().text
            Log.d("DEEPAK", res)
        })

//        chatViewModel = ViewModelProvider(this, ChatViewModelFactory(chatRepository, requestBody1, contentType, authorization))[ChatViewModel::class.java]
//
//        chatViewModel.getImage.observe(this, Observer {
//
//            val res = it.data.first().url
//            Log.d("DEEPAK1", res)
//        })
    }
    private fun initialize() {
        apiInterface = RetrofitHelper.getInstance().create(ApiInterface::class.java)
        chatRepository = ChatRepository(apiInterface)
    }
}