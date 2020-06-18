package me.simple.logview

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.provider.Settings
import java.text.SimpleDateFormat
import java.util.*


internal object Utils {

    private lateinit var context: Context
    private lateinit var config: Config
    private val df = SimpleDateFormat("yyyy-MM-dd mm:hh:ss", Locale.CHINA)
    private val mWindowLayout: WindowLayout by lazy { WindowLayout(Utils.context) }

    @TargetApi(Build.VERSION_CODES.M)
    fun reqWindowPermission() {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:" + context.packageName)
        )
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    private fun canShowWindow(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(context)
        }
        return true
    }

    fun init(context: Context, config: Config) {
        this.context = context
        this.config = config
    }

    fun getScreenHeight() = context.resources.displayMetrics.heightPixels

    fun getTime(): String = df.format(Date())

    fun show() {
        if (!canShowWindow()) {
            reqWindowPermission()
            return
        }

        mWindowLayout.show()
    }

    fun dismiss() {
        mWindowLayout.dismiss()
    }

    fun add(logBean: LogBean) {
        mWindowLayout.add(logBean)
    }

    fun getColor(priority: String) = when (priority) {
        LogBean.VERBOSE -> config.vColorInt
        LogBean.DEBUG -> config.dColorInt
        LogBean.INFO -> config.iColorInt
        LogBean.WARN -> config.wColorInt
        LogBean.ERROR -> config.eColorInt
        else -> Color.parseColor("#999999")
    }
}