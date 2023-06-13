package com.example.aiguru.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.aiguru.R
import com.example.aiguru.activity.ChatActivity
import com.example.aiguru.activity.ImageDetailActivity
import com.example.aiguru.model.Message
import com.example.aiguru.utils.Constant


class ChatAdapter(private val list: List<Message>, private val chatType: Boolean, private val context: Context) : Adapter<ViewHolder>() {

    inner class UserViewHolder(view: View) : ViewHolder(view) {
        val tvUser = view.findViewById<TextView>(R.id.tv_user_message)!!
    }
    inner class AiViewHolder(view: View) : ViewHolder(view) {
        val tvAi = view.findViewById<TextView>(R.id.tv_message_ai)!!
        val gridView = view.findViewById<GridView>(R.id.grid_layout)!!
        val contentCopy = view.findViewById<ImageView>(R.id.tv_content_copy)!!
        val shareText = view.findViewById<ImageView>(R.id.tv_share)!!
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
                (holder as UserViewHolder).tvUser.text = message.message[0]
            }
            false -> {
                if (chatType) {
                    (holder as AiViewHolder).apply {
                        gridView.visibility = View.GONE
                        tvAi.visibility = View.VISIBLE
                        tvAi.text = message.message[0]
                        contentCopy.setOnClickListener { (context).copyToClipboard(message.message[0]) }
                        shareText.setOnClickListener { shareText(message.message[0]) }
                    }
                } else {
                    (holder as AiViewHolder).apply {
                        contentCopy.visibility = View.GONE
                        shareText.visibility = View.GONE
                        tvAi.visibility = View.GONE
                        gridView.visibility = View.VISIBLE

                        val adapter = GridAdapter(context, message.message)
                        gridView.adapter = adapter
                        itemClick(gridView, message.message)
                    }
                }
            }
        }
    }

    private fun itemClick(gridView: GridView, urlList: List<String>) {
        gridView.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                when (position) {
                    0 -> {
                        view.setOnClickListener {navigateToImageDetailActivity(urlList[0]) }
                    }
                    1 -> {
                        view.setOnClickListener {navigateToImageDetailActivity(urlList[1]) }
                    }
                    2 -> {
                        view.setOnClickListener {navigateToImageDetailActivity(urlList[2]) }
                    }
                    3 -> {
                        view.setOnClickListener {navigateToImageDetailActivity(urlList[3]) }
                    }
                }
            }
    }
    private fun navigateToImageDetailActivity(url: String) {
        val intent  = Intent(context, ImageDetailActivity::class.java)
        intent.putExtra(Constant.URL, url)
        context.startActivity(intent)
    }
    private fun shareText(text: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, text)
        context.startActivity(Intent.createChooser(intent, "Share with:"))
    }
    private fun Context.copyToClipboard(text: CharSequence){
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label",text)
        clipboard.setPrimaryClip(clip)
    }
}