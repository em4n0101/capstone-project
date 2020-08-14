package com.em4n0101.gamecollection.utils

import android.content.Context
import android.widget.Toast

fun Context.toast(message: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, length).show()
}