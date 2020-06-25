package com.example.broadcast

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Login page
 */
class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login.setOnClickListener {
            // get the account and password
            val account = accountEdit.text.toString()
            val password = passwordEdit.text.toString()

            if (account == "admin" && password == "123456") {
                // correct -> start main activity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // wrong -> toast error message
                Toast.makeText(
                    this, "Invalid account or password!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
