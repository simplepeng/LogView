package me.simple.logview

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView


class WindowLayout(context: Context) : FrameLayout(context) {

    private val mWindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private val mLayoutParams = WindowManager.LayoutParams()
    private val mIvMenu: ImageView by lazy { findViewById<ImageView>(R.id.ivMenu) }

    init {
        View.inflate(context, R.layout.layout_log_view, this)
    }

    fun show() {
        if (isShown) return
        try {
            mWindowManager.addView(this, wrapLayoutParams())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun dismiss() {
        try {

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun wrapLayoutParams(): WindowManager.LayoutParams {
        mLayoutParams.width = LayoutParams.WRAP_CONTENT
        mLayoutParams.height = LayoutParams.WRAP_CONTENT
        basicLayoutParams()
        return mLayoutParams
    }

    private fun matchLayoutParams(): WindowManager.LayoutParams {
        mLayoutParams.width = LayoutParams.MATCH_PARENT
        mLayoutParams.height = Utils.getScreenHeight() / 2
        basicLayoutParams()
        return mLayoutParams
    }

    private fun basicLayoutParams() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        }
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        mLayoutParams.format = PixelFormat.TRANSLUCENT
        mLayoutParams.gravity = Gravity.TOP or Gravity.LEFT
        //        mLayoutParams.x = 10
        //        mLayoutParams.y = attr.y
    }


}