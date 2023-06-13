package com.example.aiguru.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.aiguru.R
import com.example.aiguru.utils.Constant.Companion.requestOption

class GridAdapter(private val context: Context, imageList: List<String?>) : ArrayAdapter<String?>(context, 0, imageList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.item_grid_view, parent, false)
        }

        val url: String? = getItem(position)
        val imageView = listItemView!!.findViewById<ImageView>(R.id.image_view)
        Glide.with(context).load(url).apply(requestOption()).into(imageView)
        return listItemView
    }
}