package me.simple.logview

import android.app.Application

object LogView {

    fun v(tag: String, msg: String) {
        Utils.add(LogBean(LogBean.VERBOSE, tag, Utils.getTime(), msg))
    }

    fun d(tag: String, msg: String) {
        Utils.add(LogBean(LogBean.DEBUG, tag, Utils.getTime(), msg))
    }

    fun i(tag: String, msg: String) {
        Utils.add(LogBean(LogBean.INFO, tag, Utils.getTime(), msg))
    }

    fun w(tag: String, msg: String) {
        Utils.add(LogBean(LogBean.WARN, tag, Utils.getTime(), msg))
    }

    fun e(tag: String, msg: String) {
        Utils.add(LogBean(LogBean.ERROR, tag, Utils.getTime(), msg))
    }

    fun init(app: Application, config: Config) {
        Utils.init(app.applicationContext, config)
    }

    fun init(app: Application) {
        Utils.init(app.applicationContext, Config())
    }

    fun show() {
        Utils.show()
    }

    fun showByHasPermissions() {
        Utils.showByHasPermissions()
    }

    fun dismiss() {
        Utils.dismiss()
    }


}