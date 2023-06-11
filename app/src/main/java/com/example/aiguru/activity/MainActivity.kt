package com.example.aiguru.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aiguru.R
import com.example.aiguru.adapter.ChatAdapter
import com.example.aiguru.databinding.ActivityMainBinding
import com.example.aiguru.model.Message
import com.example.aiguru.utils.Constant.Companion.requestBodyText
import com.example.aiguru.utils.NetworkResult
import com.example.aiguru.viewModel.ChatViewModel
import com.example.aiguru.viewModel.ChatViewModelFactory
import com.razzaghimahdi78.dotsloading.core.DotSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var mLayoutManager: LinearLayoutManager
    private var list = ArrayList<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val apiInterface = (application as ChatApplication).apiInterface
        chatViewModel = ViewModelProvider(this, ChatViewModelFactory(apiInterface))[ChatViewModel::class.java]
        initialize()


        binding.ivSend.setOnClickListener {
            if (binding.editText.text.isEmpty()) {
                Toast.makeText(this, "Write your message!", Toast.LENGTH_SHORT).show()
            } else {
                binding.ivSend.visibility = View.GONE
                binding.viewLoadingWavy.visibility = View.VISIBLE
                list.add(Message(isText = true, isUser = true, message = binding.editText.text.toString()))
                requestBodyText(binding.editText.text.toString())
                binding.editText.text.clear()
                init()

                CoroutineScope(Dispatchers.IO).launch {
                    chatViewModel.getText()
                    withContext(Dispatchers.Main) {
                        when (val response = chatViewModel.textResponse) {
                            is NetworkResult.Success -> {
                                binding.viewLoadingWavy.visibility = View.GONE
                                binding.ivSend.visibility = View.VISIBLE
                                val result = response.data!!.choices.first().text.replace("\n","")
                                list.add(Message(isText = true, isUser = false, message = result))
                                init()
                            }
                            is NetworkResult.Error -> {
                                binding.viewLoadingWavy.visibility = View.GONE
                                binding.ivSend.visibility = View.VISIBLE
                                val result = response.message!!
                                list.add(Message(isText = true, isUser = false, message = result))
                                init()
                            }
                            is NetworkResult.Loading -> {
                                Log.d("DEEPAK Loading", "Loading called!")
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }

    private fun initialize() {
        mLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = mLayoutManager
        mLayoutManager.stackFromEnd = true

        chatAdapter = ChatAdapter(list)
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