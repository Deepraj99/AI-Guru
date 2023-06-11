package com.example.aiguru.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.aiguru.R
import com.example.aiguru.databinding.ActivityHomeBinding
import com.example.aiguru.utils.Constant.Companion.CHAT_TYPE

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        binding.goToTextChat.setOnClickListener {
            val intent  = Intent(this, ChatActivity::class.java)
            intent.putExtra(CHAT_TYPE, true)
            startActivity(intent)
        }
        binding.goToImageChat.setOnClickListener {
            val intent  = Intent(this, ChatActivity::class.java)
            intent.putExtra(CHAT_TYPE, false)
            startActivity(intent)
        }
    }
}