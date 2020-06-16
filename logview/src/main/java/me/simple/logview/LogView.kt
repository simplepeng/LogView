package me.simple.logview

import android.app.Application
import android.graphics.Color
import android.util.Log
import androidx.annotation.ColorInt
import me.simple.logview.base.ILogView


object LogView : ILogView {

    override fun v(tag: String, msg: String) {
        Utils.add(LogBean(LogBean.VERBOSE, tag, Utils.getTime(), msg))
    }

    override fun d(tag: String, msg: String) {
        Utils.add(LogBean(LogBean.DEBUG, tag, Utils.getTime(), msg))
    }

    override fun i(tag: String, msg: String) {
        Utils.add(LogBean(LogBean.INFO, tag, Utils.getTime(), msg))
    }

    override fun w(tag: String, msg: String) {
        Utils.add(LogBean(LogBean.WARN, tag, Utils.getTime(), msg))
    }

    override fun e(tag: String, msg: String) {
        Utils.add(LogBean(LogBean.ERROR, tag, Utils.getTime(), msg))
    }

    override fun init(app: Application) {
        Utils.init(app.applicationContext)
    }

    override fun show() {
        Utils.show()
    }

    override fun dismiss() {
        Utils.dismiss()
    }

    class Config {
        @ColorInt
        val vColorInt = Color.parseColor("#BBBBBB")

        @ColorInt
        val dColorInt = Color.parseColor("#59ABBB")

        @ColorInt
        val iColorInt = Color.parseColor("#59FB41")

        @ColorInt
        val wColorInt = Color.parseColor("#FFF56E")

        @ColorInt
        val eColorInt = Color.parseColor("#FF6B68")

        fun setVerboseColor(@ColorInt color: Int = vColorInt): Config {
            Utils.colorMap[LogBean.VERBOSE] = color
            return this
        }

        fun setDebugColor(@ColorInt color: Int = dColorInt): Config {
            Utils.colorMap[LogBean.DEBUG] = color
            return this
        }

        fun setInfoColor(@ColorInt color: Int = iColorInt): Config {
            Utils.colorMap[LogBean.INFO] = color
            return this
        }

        fun setWarnColor(@ColorInt color: Int = wColorInt): Config {
            Utils.colorMap[LogBean.WARN] = color
            return this
        }

        fun setErrorColor(@ColorInt color: Int = eColorInt): Config {
            Utils.colorMap[LogBean.ERROR] = color
            return this
        }
    }
}