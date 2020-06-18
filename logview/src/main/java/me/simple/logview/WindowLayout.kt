package me.simple.logview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_log_view.view.*


class WindowLayout(context: Context) : FrameLayout(context) {

    private val mWM = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private val mParams = WindowManager.LayoutParams()
    private val mIvMenu by lazy { findViewById<ImageView>(R.id.ivMenu) }
    private val mViewContent by lazy { findViewById<View>(R.id.viewContent) }
    private val mRecyclerView by lazy { findViewById<RecyclerView>(R.id.rvLog) }
    private val mResizeView by lazy { findViewById<View>(R.id.resizeView) }

    private val mLogList = mutableListOf<LogBean>()
    private val mAdapter = LogAdapter(mLogList)

    init {
        View.inflate(context, R.layout.layout_log_view, this)
//        mIvMenu.setOnClickListener {
//            clickMenu()
//        }
        mRecyclerView.layoutManager = LinearLayoutManager(context).apply {
            stackFromEnd = true
        }
        mRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        mRecyclerView.adapter = mAdapter

        setMenuTouch()
        setResizeViewTouch()
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
            mViewContent.visibility = View.GONE
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

    private fun resizeHeight(moveY: Int): WindowManager.LayoutParams {
        val height = mParams.height
        mParams.height = height + moveY
        return mParams
    }

    private var downX = 0f
    private var downY = 0f
    private var lastY = 0f

    fun add(logBean: LogBean) {
        mLogList.add(logBean)
        mAdapter.notifyItemInserted(mLogList.lastIndex)
        mRecyclerView.scrollToPosition(mLogList.lastIndex)
    }

    private fun clickMenu() {
        if (mViewContent.visibility == View.GONE) {
            mViewContent.visibility = View.VISIBLE
            mResizeView.visibility = View.VISIBLE
            mWM.updateViewLayout(this, matchLayoutParams())
        } else {
            mViewContent.visibility = View.GONE
            mResizeView.visibility = View.INVISIBLE
            mWM.updateViewLayout(this, wrapLayoutParams())
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setMenuTouch() {
        var downTime = 0L
        ivMenu.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downTime = System.currentTimeMillis()
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
                    val upTime = System.currentTimeMillis() - downTime
                    if (upTime < 100) {
                        clickMenu()
                    }
                }
            }
            true
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setResizeViewTouch() {
        var downY = 0
        mResizeView.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downY = event.y.toInt()
                }
                MotionEvent.ACTION_MOVE -> {
                    val moveY = event.y.toInt() - downY
                    mWM.updateViewLayout(this, resizeHeight(moveY))
                    downY = event.y.toInt()
                }
                MotionEvent.ACTION_UP -> {
                }
            }
            true
        }
    }
}