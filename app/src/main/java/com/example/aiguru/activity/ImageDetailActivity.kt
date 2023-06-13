package com.example.aiguru.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.aiguru.R
import com.example.aiguru.databinding.ActivityImageDetailBinding
import com.example.aiguru.utils.Constant
import com.example.aiguru.utils.Constant.Companion.requestOption
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class ImageDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_detail)

        val imageUrl: String = intent.extras!!.getString(Constant.URL)!!
        Glide.with(this).load(imageUrl).apply(requestOption()).into(binding.imageView)

        binding.ivBackArrow.setOnClickListener { finish() }

        binding.btnDownload.setOnClickListener { downloadImage(imageUrl) }
    }
    private fun downloadImage(imageUrl: String) {
        var `in`: InputStream? = null
        var bmp: Bitmap? = null
        val iv = binding.imageView
        var responseCode = -1
        try {
            val url = URL(imageUrl)
            val con: HttpURLConnection = url.openConnection() as HttpURLConnection
            con.doInput = true
            con.connect()
            responseCode = con.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                //download
                `in` = con.inputStream
                bmp = BitmapFactory.decodeStream(`in`)
                `in`.close()
                iv.setImageBitmap(bmp)
            }
        } catch (ex: Exception) {
            Log.e("Exception", ex.toString())
        }
    }
}