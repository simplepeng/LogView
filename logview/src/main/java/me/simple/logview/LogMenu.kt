package me.simple.logview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.Toast

internal class LogMenu(context: Context) : ImageView(context) {

    init {
        setImageResource(R.drawable.ic_log_menu)
        setBackgroundColor(Color.RED)
        setOnClickListener {
            Toast.makeText(context, "LogMenu", Toast.LENGTH_SHORT).show()
        }
    }

    private var downX: Int = 0
    private var downY: Int = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
            MotionEvent.ACTION_MOVE -> {

            }
            MotionEvent.ACTION_UP -> {

            }
        }
        return true
    }
}