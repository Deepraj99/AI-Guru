package com.example.aiguru.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.aiguru.R
import com.example.aiguru.model.Message

class ChatAdapter(private val list: List<Message>, private val chatType: Boolean, private val context: Context) : Adapter<ViewHolder>() {

    inner class UserViewHolder(view: View) : ViewHolder(view) {
        val tvUser = view.findViewById<TextView>(R.id.tv_user_message)!!
    }
    inner class AiViewHolder(view: View) : ViewHolder(view) {
        val tvAi = view.findViewById<TextView>(R.id.tv_message_ai)!!
        val ivAi = view.findViewById<ImageView>(R.id.iv_message_ai)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            0 -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_user, parent, false)
                UserViewHolder(view)
                }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_ai, parent, false)
                AiViewHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position].isUser) {
            true -> 0
            false -> 1
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = list[position]
        when (message.isUser) {
            true -> {
                (holder as UserViewHolder).tvUser.text = message.message
            }
            false -> {
                if (chatType) {
                    (holder as AiViewHolder).apply {
                        ivAi.visibility = View.GONE
                        tvAi.visibility = View.VISIBLE
                        tvAi.text = message.message
                    }
                } else {
                    (holder as AiViewHolder).apply {
                        tvAi.visibility = View.GONE
                        ivAi.visibility = View.VISIBLE
                    }
                    Glide.with(context).load(message.message).into(holder.ivAi);
                }
            }
        }
    }
}