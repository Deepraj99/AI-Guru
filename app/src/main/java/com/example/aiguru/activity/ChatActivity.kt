package com.example.aiguru.activity

import android.annotation.SuppressLint
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
import com.example.aiguru.utils.Constant.Companion.SUGGESTION
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

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        val chatType: Boolean = intent.extras!!.getBoolean(CHAT_TYPE)
        val suggestion: String = intent.extras!!.getString(SUGGESTION)!!
        val apiInterface = (application as ChatApplication).apiInterface
        chatViewModel = ViewModelProvider(this, ChatViewModelFactory(apiInterface))[ChatViewModel::class.java]
        initialize(chatType)

        binding.editText.setText(suggestion)
        binding.ivBackArrow.setOnClickListener { finish() }

        if (chatType) {
            binding.suggestion1.setOnClickListener { binding.editText.setText(R.string.explain_quantum_computing_in_simple_terms) }
            binding.suggestion2.setOnClickListener { binding.editText.setText(R.string.got_any_creative_ideas_for_a_10_year_old_s_birthday) }
            binding.suggestion3.setOnClickListener { binding.editText.setText(R.string.how_do_i_make_an_http_request_in_javascript) }
        } else {
            binding.tvSuggestion1.setText(R.string.suggestion2)
            binding.tvSuggestion2.setText(R.string.suggestion3)
            binding.tvSuggestion3.setText(R.string.suggestion7)
            binding.suggestion1.setOnClickListener { binding.editText.setText(R.string.suggestion2) }
            binding.suggestion2.setOnClickListener { binding.editText.setText(R.string.suggestion3) }
            binding.suggestion3.setOnClickListener { binding.editText.setText(R.string.suggestion7) }
        }



        binding.ivSend.setOnClickListener {
            if (binding.editText.text.isEmpty()) {
                Toast.makeText(this, R.string.write_message, Toast.LENGTH_SHORT).show()
            } else {
                binding.ivSend.visibility = View.GONE
                binding.viewLoadingWavy.visibility = View.VISIBLE
                binding.suggestion1.visibility = View.GONE
                binding.suggestion2.visibility = View.GONE
                binding.suggestion3.visibility = View.GONE

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
                                        val result = listOf(response.data!!.openai.generated_text.substring(2))
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
                                        val result = listOf(response.data!!.openai.items[0].image_resource_url, response.data.openai.items[1].image_resource_url, response.data.openai.items[2].image_resource_url, response.data.openai.items[3].image_resource_url)
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