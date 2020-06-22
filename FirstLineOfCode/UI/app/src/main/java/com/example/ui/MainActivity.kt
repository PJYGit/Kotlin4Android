package com.example.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button -> {
                Log.d("Button", "button clicked")
                //val inputText = editText.text.toString()
                //Toast.makeText(this, inputText, Toast.LENGTH_SHORT).show()

                //imageView.setImageResource(R.drawable.black_stars)

//                if (progressBar.visibility == View.VISIBLE){
//                    progressBar.visibility = View.GONE
//                } else {
//                    progressBar.visibility = View.VISIBLE
//                }

                // progressBar.progress += 10

                AlertDialog.Builder(this).apply {
                    setTitle("Warning!")
                    setMessage("You really want to do this?")
                    setCancelable(false)
                    setPositiveButton("OK") { dialog, which -> } // operation after ok
                    setNegativeButton("CANCEL") { dialog, which -> } // operation after cancel
                    show()
                }
            }
            // else
        }
    }
}
