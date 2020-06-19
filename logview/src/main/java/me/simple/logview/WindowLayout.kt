package me.simple.logview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class WindowLayout(context: Context) : FrameLayout(context) {

    private val mWM = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private val mParams = WindowManager.LayoutParams()

    private val mIvMenu by lazy { findViewById<ImageView>(R.id.ivMenu) }
    private val mViewContent by lazy { findViewById<View>(R.id.viewContent) }
    private val mRecyclerView by lazy { findViewById<RecyclerView>(R.id.rvLog) }
    private val mResizeView by lazy { findViewById<View>(R.id.resizeView) }
    private val mEtTag by lazy { findViewById<EditText>(R.id.etTag) }
    private val mIvClose by lazy { findViewById<ImageView>(R.id.ivClose) }

    private var mResizedHeight = 0

    private val mLogList = mutableListOf<LogBean>()
    private val mAdapter = LogAdapter(mLogList)

    init {
        View.inflate(context, R.layout.layoutview_window_layout, this)
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
        setCloseViewTouch()

        mEtTag.setOnFocusChangeListener { v, hasFocus ->
            Log.d(TAG, "hasFocus == $hasFocus")
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
            hideContent()
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
        mParams.height = if (mResizedHeight == 0) {
            Utils.getScreenHeight() / 2
        } else {
            mResizedHeight
        }
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
        val height = mParams.height + moveY
        this.mResizedHeight = height
        mParams.height = height
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
        showContent()
    }

    private fun clickClose() {
        hideContent()
    }

    private fun showContent() {
        mViewContent.visibility = View.VISIBLE
        mResizeView.visibility = View.VISIBLE
        mIvMenu.visibility = View.GONE
        mIvClose.visibility = View.VISIBLE
        mWM.updateViewLayout(this, matchLayoutParams())
    }

    private fun hideContent() {
        mViewContent.visibility = View.GONE
        mResizeView.visibility = View.INVISIBLE
        mIvMenu.visibility = View.VISIBLE
        mIvClose.visibility = View.GONE
        mWM.updateViewLayout(this, wrapLayoutParams())
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setMenuTouch() {
        var downTime = 0L
        mIvMenu.setOnTouchListener { _, event ->
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
                    if (upTime < 150) {
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
                    downY = event.rawY.toInt()
                }
                MotionEvent.ACTION_MOVE -> {
                    val moveY = event.rawY.toInt() - downY
                    mWM.updateViewLayout(this, resizeHeight(moveY))
                    downY = event.rawY.toInt()
                }
                MotionEvent.ACTION_UP -> {
                }
            }
            true
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setCloseViewTouch() {
        var downTime = 0L
        mIvClose.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downTime = System.currentTimeMillis()
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
                    if (upTime < 150) {
                        clickClose()
                    }
                }
            }
            true
        }
    }

    companion object {
        const val TAG = "WindowLayout"
    }
}