package com.example.aiguru.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.aiguru.R
import com.example.aiguru.model.Message

class ChatAdapter(private val list: List<Message>) : Adapter<ChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(view: View) : ViewHolder(view) {
        val tvUser = view.findViewById<TextView>(R.id.tv_user_message)!!
//        val tvAi = view.findViewById<TextView>(R.id.tv_message_ai)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_user, parent, false)
//        val view = if (viewType == 0) {
//            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_user, parent, false)
//        } else {
//            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_ai, parent, false)
//        }
        return ChatViewHolder(view)
    }

//    override fun getItemViewType(position: Int): Int {
//        val message = list[position]
//        return if(message.isUser) 0 else 1
//    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = list[position]
        holder.tvUser.text = message.message
//        if (message.isUser) {
//            holder.tvUser.text = message.message
//        } else {
//            holder.tvAi.text = message.message
//        }
    }
}