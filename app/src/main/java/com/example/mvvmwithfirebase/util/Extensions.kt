package com.example.mvvmwithfirebase.util

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun Fragment.toast(message:String?){
    Toast.makeText(context,message,Toast.LENGTH_LONG).show()
}