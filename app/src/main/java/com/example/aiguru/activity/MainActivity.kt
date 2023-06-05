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
import com.example.aiguru.model.request.text.TextRequest
import com.example.aiguru.repository.ChatRepository
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

        val contentType = "application/json"
        val authorization = "Bearer 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"

        chatViewModel = ViewModelProvider(this, ChatViewModelFactory(chatRepository, requestBody, contentType, authorization))[ChatViewModel::class.java]

        chatViewModel.getText.observe(this, Observer {

            val res = it.choices.first().text
            Log.d("DEEPAK", res)
        })
    }
    private fun initialize() {
        apiInterface = RetrofitHelper.getInstance().create(ApiInterface::class.java)
        chatRepository = ChatRepository(apiInterface)
    }
}