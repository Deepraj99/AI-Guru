package com.example.aiguru.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.aiguru.R
import com.example.aiguru.databinding.ActivityMainBinding
import com.example.aiguru.utils.Constant.Companion.authorization
import com.example.aiguru.utils.Constant.Companion.contentType
import com.example.aiguru.utils.Constant.Companion.requestBodyText
import com.example.aiguru.utils.NetworkResult
import com.example.aiguru.viewModel.ChatViewModel
import com.example.aiguru.viewModel.ChatViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var chatViewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val chatRepository = (application as ChatApplication).chatRepository
        chatViewModel = ViewModelProvider(this, ChatViewModelFactory(chatRepository, requestBodyText, contentType, authorization))[ChatViewModel::class.java]

        chatViewModel.getText.observe(this, Observer {

            when (it) {
                is NetworkResult.Success -> {
                    val res = it.data!!.choices.first().text
                    Log.d("DEEPAK", res)
                }
                is NetworkResult.Error -> {
                    val res = it.message!!
                    Log.d("DEEPAK1", res)
                }
                is NetworkResult.Loading -> {
                    Log.d("DEEPAK2", "Waiting...")
                }
            }
        })
    }
}