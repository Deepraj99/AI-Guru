package com.example.aiguru.activity

import android.app.Application
import com.example.aiguru.api.ApiInterface
import com.example.aiguru.api.RetrofitHelper

class ChatApplication : Application() {

    lateinit var apiInterface: ApiInterface

    override fun onCreate() {
        super.onCreate()
        initialize()
    }
    private fun initialize() {
        apiInterface = RetrofitHelper.getInstance().create(ApiInterface::class.java)
    }
}