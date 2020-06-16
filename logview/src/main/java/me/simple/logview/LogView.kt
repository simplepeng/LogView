package me.simple.logview

import android.app.Application
import android.graphics.Color
import android.util.Log
import androidx.annotation.ColorInt
import me.simple.logview.base.Config
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

    override fun init(app: Application, config: Config) {
        Utils.init(app.applicationContext, config)
    }

    override fun init(app: Application) {
        Utils.init(app.applicationContext, Config())
    }

    override fun show() {
        Utils.show()
    }

    override fun dismiss() {
        Utils.dismiss()
    }


}