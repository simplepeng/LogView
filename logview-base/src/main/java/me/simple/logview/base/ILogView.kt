package me.simple.logview.base

import android.app.Application

interface ILogView {

    fun v(tag: String, msg: String)

    fun d(tag: String, msg: String)

    fun i(tag: String, msg: String)

    fun w(tag: String, msg: String)

    fun e(tag: String, msg: String)

    fun init(app: Application)

    fun show()

    fun dismiss()
}