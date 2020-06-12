package me.simple.logview

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

internal object Utils {

    fun getActivityRoot(activity: Activity): FrameLayout? {
        return try {
            activity.window.decorView
                    .findViewById(android.R.id.content) as FrameLayout
        } catch (e: Exception) {
            null
        }
    }

    private var logViewMenu: LogMenu? = null

    @Synchronized
    fun getLogViewMenu(context: Context): LogMenu {
        if (logViewMenu == null) {
            logViewMenu = LogMenu(context)
        }
        return logViewMenu!!
    }

    fun createLayoutParams(height: Int = FrameLayout.LayoutParams.WRAP_CONTENT): FrameLayout.LayoutParams {
        return FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                height
        ).apply {
//            gravity = Gravity.TOP
        }
    }

    fun getLogWindow(
            context: Context,
            parent: ViewGroup
    ): View {
        return LayoutInflater.from(context).inflate(R.layout.layout_log_window, parent, false)
    }

    fun getScreenHeight(context: Context) = context.resources.displayMetrics.heightPixels

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}