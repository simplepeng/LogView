package me.simple.logview

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import java.util.Collections.max


class WindowLayout(context: Context) : FrameLayout(context) {

    private val mWM = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private val mParams = WindowManager.LayoutParams()
    private val mIvMenu by lazy { findViewById<ImageView>(R.id.ivMenu) }
    private val mViewContent by lazy { findViewById<View>(R.id.viewContent) }

    init {
        View.inflate(context, R.layout.layout_log_view, this)
        mIvMenu.setOnClickListener {
            clickMenu()
        }
    }

    private fun clickMenu() {
        if (mViewContent.visibility == View.GONE) {
            mViewContent.visibility = View.VISIBLE
            mWM.updateViewLayout(this, matchLayoutParams())
        } else {
            mViewContent.visibility = View.GONE
            mWM.updateViewLayout(this, wrapLayoutParams())
        }
    }

    fun show() {
        if (isShown) return
        try {
            mWM.addView(this, wrapLayoutParams())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun dismiss() {
        try {
            mWM.removeView(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun wrapLayoutParams(): WindowManager.LayoutParams {
        mParams.width = LayoutParams.WRAP_CONTENT
        mParams.height = LayoutParams.WRAP_CONTENT
        basicLayoutParams()
        return mParams
    }

    private fun matchLayoutParams(): WindowManager.LayoutParams {
        mParams.width = LayoutParams.MATCH_PARENT
        mParams.height = Utils.getScreenHeight() / 2
        basicLayoutParams()
        return mParams
    }

    private fun basicLayoutParams() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        } else {
            mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        }
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        mParams.format = PixelFormat.TRANSLUCENT
        mParams.gravity = Gravity.TOP or Gravity.LEFT
        //        mLayoutParams.x = 10
        //        mLayoutParams.y = attr.y
    }

    private var downX = 0f
    private var downY = 0f
    private var lastY = 0f

    override fun onTouchEvent(event: MotionEvent): Boolean {
//        super.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.rawX
                downY = event.rawY
                lastY = downY
            }
            MotionEvent.ACTION_MOVE -> {
                mParams.y += (event.rawY - lastY).toInt()
                mParams.y = kotlin.math.max(0, mParams.y)
                mWM.updateViewLayout(this, mParams)
                lastY = event.rawY
            }
            MotionEvent.ACTION_UP -> {

            }
        }
        return true
    }
}