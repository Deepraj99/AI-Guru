package com.example.aiguru.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aiguru.R
import com.example.aiguru.adapter.ChatAdapter
import com.example.aiguru.databinding.ActivityMainBinding
import com.example.aiguru.model.Message
import com.example.aiguru.model.request.text.TextRequest
import com.example.aiguru.utils.Constant.Companion.authorization
import com.example.aiguru.utils.Constant.Companion.contentType
import com.example.aiguru.utils.NetworkResult
import com.example.aiguru.viewModel.ChatViewModel
import com.example.aiguru.viewModel.ChatViewModelFactory
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var mLayoutManager: LinearLayoutManager
    private var list = ArrayList<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)



        val chatRepository = (application as ChatApplication).chatRepository


        mLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = mLayoutManager
        mLayoutManager.stackFromEnd = true

        chatAdapter = ChatAdapter(list)
        binding.recyclerView.adapter = chatAdapter

        binding.tvSend.setOnClickListener {
            if (binding.editText.text.isEmpty()) {
                Toast.makeText(this, "Enter text", Toast.LENGTH_SHORT).show()
            } else {
                list.add(Message(isText = true, isUser = true, message = binding.editText.text.toString()))
                init()
            }
        }

        val requestBodyText: RequestBody = RequestBody.create(
            MediaType.parse("application/json"),
            Gson().toJson(
                TextRequest(
                    250,
                    "text-davinci-003",
                    binding.editText.text.toString(),
                    0.7
                )
            ))

        chatViewModel = ViewModelProvider(this, ChatViewModelFactory(chatRepository, requestBodyText, contentType, authorization))[ChatViewModel::class.java]

        chatViewModel.getText.observe(this, Observer {

            when (it) {
                is NetworkResult.Success -> {
                    val res = it.data!!.choices.first().text
                    list.add(Message(isText = true, isUser = false, message = res))
                    init()
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

    private fun init() {
        chatAdapter.notifyItemInserted(list.size-1)
        binding.recyclerView.recycledViewPool.clear()
        binding.recyclerView.smoothScrollToPosition(list.size-1)
    }
}