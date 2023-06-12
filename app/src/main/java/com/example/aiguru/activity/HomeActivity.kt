package com.example.aiguru.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.aiguru.R
import com.example.aiguru.databinding.ActivityHomeBinding
import com.example.aiguru.utils.Constant.Companion.CHAT_TYPE
import com.example.aiguru.utils.Constant.Companion.SUGGESTION

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        init()
    }
    private fun init() {
        binding.goToTextChat.setOnClickListener {
            val intent  = Intent(this, ChatActivity::class.java)
            intent.putExtra(CHAT_TYPE, true)
            intent.putExtra(SUGGESTION, "")
            startActivity(intent)
        }
        binding.goToImageChat.setOnClickListener {
            val intent  = Intent(this, ChatActivity::class.java)
            intent.putExtra(CHAT_TYPE, false)
            intent.putExtra(SUGGESTION, "")
            startActivity(intent)
        }



        binding.suggestion1.setOnClickListener {
            val intent  = Intent(this, ChatActivity::class.java)
            intent.putExtra(CHAT_TYPE, true)
            intent.putExtra(SUGGESTION, "Write an application for 1 day leave.")
            startActivity(intent)
        }
        binding.suggestion4.setOnClickListener {
            val intent  = Intent(this, ChatActivity::class.java)
            intent.putExtra(CHAT_TYPE, true)
            intent.putExtra(SUGGESTION, "Write an essay on Artificial Intelligence.")
            startActivity(intent)
        }
        binding.suggestion5.setOnClickListener {
            val intent  = Intent(this, ChatActivity::class.java)
            intent.putExtra(CHAT_TYPE, true)
            intent.putExtra(SUGGESTION, "Write few cool instagram captions.")
            startActivity(intent)
        }
        binding.suggestion8.setOnClickListener {
            val intent  = Intent(this, ChatActivity::class.java)
            intent.putExtra(CHAT_TYPE, true)
            intent.putExtra(SUGGESTION, "Explain me Gravity like I'm 5.")
            startActivity(intent)
        }


        binding.suggestion2.setOnClickListener {
            val intent  = Intent(this, ChatActivity::class.java)
            intent.putExtra(CHAT_TYPE, false)
            intent.putExtra(SUGGESTION, "Dog walking in the street.")
            startActivity(intent)
        }
        binding.suggestion3.setOnClickListener {
            val intent  = Intent(this, ChatActivity::class.java)
            intent.putExtra(CHAT_TYPE, false)
            intent.putExtra(SUGGESTION, "A cute smiling newborn baby.")
            startActivity(intent)
        }
        binding.suggestion6.setOnClickListener {
            val intent  = Intent(this, ChatActivity::class.java)
            intent.putExtra(CHAT_TYPE, false)
            intent.putExtra(SUGGESTION, "Few samples of writing letters.")
            startActivity(intent)
        }
        binding.suggestion7.setOnClickListener {
            val intent  = Intent(this, ChatActivity::class.java)
            intent.putExtra(CHAT_TYPE, false)
            intent.putExtra(SUGGESTION, "A bird sitting on a girls hand.")
            startActivity(intent)
        }
    }
}