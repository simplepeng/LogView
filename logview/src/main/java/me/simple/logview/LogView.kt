package me.simple.logview

import android.app.Application
import me.simple.logview.base.ILogView


object LogView : ILogView {

    private val mWindowLayout: WindowLayout by lazy { WindowLayout(Utils.context) }

    override fun v(tag: String, msg: String) {
    }

    override fun d(tag: String, msg: String) {
    }

    override fun i(tag: String, msg: String) {
    }

    override fun w(tag: String, msg: String) {
    }

    override fun e(tag: String, msg: String) {
    }

    override fun init(app: Application) {
        Utils.init(app.applicationContext)
    }

    override fun show() {
        if (!Utils.canShowWindow()) {
            Utils.reqWindowPermission()
            return
        }

        mWindowLayout.show()
    }

}