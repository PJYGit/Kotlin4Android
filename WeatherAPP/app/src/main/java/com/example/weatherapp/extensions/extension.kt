package com.example.weatherapp.extensions

import android.content.Context
import android.view.View
import android.widget.Toast

val View.ctx: Context
    get() = context

fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}