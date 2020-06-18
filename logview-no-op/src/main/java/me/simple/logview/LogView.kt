package me.simple.logview

import android.app.Application
import me.simple.logview.base.Config
import me.simple.logview.base.ILogView


object LogView : ILogView {

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

    override fun init(app: Application, config: Config) {
    }

    override fun init(app: Application) {
    }

    override fun show() {
    }

    override fun dismiss() {
    }

}