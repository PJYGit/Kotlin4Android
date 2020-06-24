package com.example.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ui.R
import com.example.ui.entity.Msg
import kotlinx.android.synthetic.main.msg_left_item.view.*
import java.lang.IllegalArgumentException

/**
 * Adapter of the recyclerview
 *
 * @param msgList message list
 */
class MsgAdapter(val msgList: List<Msg>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    /**
     * received message view holder
     */
    inner class LeftViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val leftMsg: TextView = view.findViewById(R.id.leftMsg)
    }

    /**
     * sent message view holder
     */
    inner class RightViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rightMsg: TextView = view.findViewById(R.id.rightMsg)
    }

    /**
     * Get the message type
     *
     * @return msg.type 0 -> received, 1 -> sent
     */
    override fun getItemViewType(position: Int): Int {
        val msg = msgList[position]
        return msg.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        if (viewType == Msg.TYPE_RECEIVED) { // message type -> received
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.msg_left_item, parent, false)
            LeftViewHolder(view)
        } else { // message type -> sent
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.msg_right_item, parent, false)
            RightViewHolder(view)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = msgList[position]
        when (holder) { // set the message according to different type
            is LeftViewHolder -> holder.leftMsg.text = msg.content
            is RightViewHolder -> holder.rightMsg.text = msg.content
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int = msgList.size
}