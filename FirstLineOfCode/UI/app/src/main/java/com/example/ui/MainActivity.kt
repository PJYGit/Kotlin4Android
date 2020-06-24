package com.example.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ui.adapter.MsgAdapter
import com.example.ui.entity.Msg
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // message list
    private val msgList = ArrayList<Msg>()

    private var adapter: MsgAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init the original messages
        initMsg()

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        adapter = MsgAdapter(msgList)
        recyclerView.adapter = adapter
        send.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            send -> {
                // get the input message
                val content = inputText.text.toString()
                if (content.isNotEmpty()) { // if not empty
                    val msg = Msg(content, Msg.TYPE_SENT)
                    msgList.add(msg)

                    // notify the adapter that a new item is inserted
                    adapter?.notifyItemInserted(msgList.size - 1)
                    // scroll to the last item
                    recyclerView.scrollToPosition(msgList.size - 1)
                    inputText.setText("")
                }
            }
        }
    }

    private fun initMsg() {
        msgList.add(Msg("Hey, how you doing these days?", Msg.TYPE_RECEIVED))
        msgList.add(Msg("Oh, pretty good! How about you?", Msg.TYPE_SENT))
        msgList.add(Msg("Not so well, got a lot of work to do!", Msg.TYPE_RECEIVED))
        Log.d("Msg", "${msgList.size} original messages added.")
    }
}
