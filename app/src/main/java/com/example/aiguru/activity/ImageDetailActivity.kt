package com.example.aiguru.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.aiguru.R
import com.example.aiguru.databinding.ActivityImageDetailBinding
import com.example.aiguru.utils.Constant
import com.example.aiguru.utils.Constant.Companion.requestOption
import java.io.ByteArrayOutputStream
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

        binding.btnDownload.setOnClickListener { getImage(imageUrl, 256, 256) }

        binding.btnShare.setOnClickListener { showShareIntent() }
    }

    private fun showShareIntent() {
        val intent = Intent(Intent.ACTION_SEND).setType("image/*")
        val bitmap = binding.imageView.drawable.toBitmap()
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(this.contentResolver, bitmap, "image", null)
        val uri = Uri.parse(path)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(intent)
    }
    private fun getImage(imageUrl: String, desiredWidth: Int, desiredHeight: Int): Bitmap? {
        var image: Bitmap? = null
        var inSampleSize = 0
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        options.inSampleSize = inSampleSize
        try {
            Toast.makeText(this, "Image downloaded!", Toast.LENGTH_SHORT).show()
            val url = URL(imageUrl)
            var connection = url.openConnection() as HttpURLConnection
            var stream = connection.inputStream
            image = BitmapFactory.decodeStream(stream, null, options)
            val imageWidth = options.outWidth
            val imageHeight = options.outHeight
            if (imageWidth > desiredWidth || imageHeight > desiredHeight) {
                println("imageWidth:$imageWidth, imageHeight:$imageHeight")
                inSampleSize = inSampleSize + 2
                getImage(imageUrl, 256,256)
            } else {
                options.inJustDecodeBounds = false
                connection = url.openConnection() as HttpURLConnection
                stream = connection.inputStream
                image = BitmapFactory.decodeStream(stream, null, options)
                return image
            }
        } catch (e: Exception) {
            Log.e("getImage", e.toString())
        }
        return image
    }
}