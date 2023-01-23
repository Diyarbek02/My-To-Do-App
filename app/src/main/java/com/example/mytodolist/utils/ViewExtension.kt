package com.example.mytodolist.utils

import android.graphics.*
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.showOrHideView(condition: Boolean) {
    if (condition) {
        this.show()
    } else {
        this.hide()
    }
}

fun TextView.underline() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun showSnackbar(string: String){

}

fun ViewPager2.autoScroll(interval: Long) {

    val handler = Handler(Looper.getMainLooper())
    var scrollPosition = 0

    val runnable = object : Runnable {

        override fun run() {

            val count = adapter?.itemCount ?: 0
            if (count != 0)
                setCurrentItem(scrollPosition++ % count, true)

            handler.postDelayed(this, interval)
        }
    }

    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            scrollPosition = position + 1
        }

        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }
    })

    handler.post(runnable)
}

