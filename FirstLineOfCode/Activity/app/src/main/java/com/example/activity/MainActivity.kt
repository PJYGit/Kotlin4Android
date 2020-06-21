package com.example.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        button1.setOnClickListener {
//            val intent = Intent("android.intent.action.ACTION_START")
//            intent.addCategory("android.intent.category.MY_CATEGORY")
//            startActivity(intent)
//        }

//        button1.setOnClickListener {
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.data = Uri.parse("https://www.bytedance.com/zh/")
//            startActivity(intent)
//        }

        button1.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> if (resultCode == Activity.RESULT_OK) {
                val back = data?.getStringExtra("backmsg")
                Log.d("MainActivity", back)
            }
        }
    }

    // create the menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_item -> Toast.makeText(
                this, "You added an item",
                Toast.LENGTH_SHORT
            ).show()
            R.id.remove_item -> Toast.makeText(
                this, "You removed an item",
                Toast.LENGTH_SHORT
            ).show()
        }

        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val state = "All the state information you need to save."
        outState.putString("state_str", state)
    }
}
