package com.example.aiguru.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aiguru.R
import com.example.aiguru.adapter.ChatAdapter
import com.example.aiguru.databinding.ActivityChatBinding
import com.example.aiguru.model.Message
import com.example.aiguru.utils.Constant.Companion.CHAT_TYPE
import com.example.aiguru.utils.Constant.Companion.requestBodyImage
import com.example.aiguru.utils.Constant.Companion.requestBodyText
import com.example.aiguru.utils.NetworkResult
import com.example.aiguru.viewModel.ChatViewModel
import com.example.aiguru.viewModel.ChatViewModelFactory
import com.razzaghimahdi78.dotsloading.core.DotSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var mLayoutManager: LinearLayoutManager
    private var list = ArrayList<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        val chatType: Boolean = intent.extras!!.getBoolean(CHAT_TYPE)
        val apiInterface = (application as ChatApplication).apiInterface
        chatViewModel = ViewModelProvider(this, ChatViewModelFactory(apiInterface))[ChatViewModel::class.java]
        initialize(chatType)


        binding.ivSend.setOnClickListener {
            if (binding.editText.text.isEmpty()) {
                Toast.makeText(this, R.string.write_message, Toast.LENGTH_SHORT).show()
            } else {
                binding.ivSend.visibility = View.GONE
                binding.viewLoadingWavy.visibility = View.VISIBLE

                if (chatType) {
                    requestBodyText(binding.editText.text.toString())
                } else {
                    requestBodyImage(binding.editText.text.toString())
                }

                list.add(Message(isText = true, isUser = true, message = listOf(binding.editText.text.toString())))
                binding.editText.text.clear()
                init()

                CoroutineScope(Dispatchers.IO).launch {

                    if (chatType) {
                        chatViewModel.getText()
                    } else {
                        chatViewModel.getImage()
                    }

                    withContext(Dispatchers.Main) {
                        when (chatType) {
                            true -> {
                                when (val response = chatViewModel.textResponse) {
                                    is NetworkResult.Success -> {
                                        binding.viewLoadingWavy.visibility = View.GONE
                                        binding.ivSend.visibility = View.VISIBLE
                                        val result = listOf(response.data!!.choices.first().text.replace("\n",""))
                                        list.add(Message(isText = true, isUser = false, message = result))
                                        init()
                                    }
                                    is NetworkResult.Error -> {
                                        binding.viewLoadingWavy.visibility = View.GONE
                                        binding.ivSend.visibility = View.VISIBLE
                                        val result = response.message!!
                                        Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
                                    }
                                    is NetworkResult.Loading -> {
                                        Log.d("Loading", "Loading called!")
                                    }
                                    else -> {}
                                }
                            }
                            false -> {
                                when (val response = chatViewModel.imageResponse) {
                                    is NetworkResult.Success -> {
                                        binding.viewLoadingWavy.visibility = View.GONE
                                        binding.ivSend.visibility = View.VISIBLE
                                        val result = listOf(response.data!!.data[0].url, response.data.data[1].url, response.data.data[2].url, response.data.data[3].url)
                                        Log.d("RESPONSE", result.toString())
                                        list.add(Message(isText = true, isUser = false, message = result))
                                        init()
                                    }
                                    is NetworkResult.Error -> {
                                        binding.viewLoadingWavy.visibility = View.GONE
                                        binding.ivSend.visibility = View.VISIBLE
                                        val result = response.message!!
                                        Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
                                    }
                                    is NetworkResult.Loading -> {
                                        Log.d("Loading", "Loading called!")
                                    }
                                    else -> {}
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initialize(chatType: Boolean) {
        mLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = mLayoutManager
        mLayoutManager.stackFromEnd = true

        chatAdapter = ChatAdapter(list, chatType, this)
        binding.recyclerView.adapter = chatAdapter

        binding.loadingWavy.setSize(DotSize.TINY)
        binding.loadingWavy.setDotsCount(3)
        binding.loadingWavy.setDuration(400)
    }

    private fun init() {
        chatAdapter.notifyItemInserted(list.size-1)
        binding.recyclerView.recycledViewPool.clear()
        binding.recyclerView.smoothScrollToPosition(list.size-1)
    }
}