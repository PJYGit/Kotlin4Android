package com.example.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.fragment.R
import com.example.fragment.fragment.LeftFragment
import com.example.fragment.fragment.RightFragment
import kotlinx.android.synthetic.main.left_fragment.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // replaceFragment(RightFragment())
//        button.setOnClickListener {
//            replaceFragment(LeftFragment())
//        }
    }

//    private fun replaceFragment(fragment: Fragment) {
//        val fragmentManager = supportFragmentManager
//        val transaction = fragmentManager.beginTransaction()
//        transaction.replace(R.id.rightLayout, fragment)
//        // transaction.addToBackStack(null)
//        transaction.commit()
//    }
}
