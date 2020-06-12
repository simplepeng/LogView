package me.simple.logview

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.widget.FrameLayout
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

    override fun init(app: Application) {
        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

            }

            override fun onActivityStarted(activity: Activity) {
                val root = Utils.getActivityRoot(activity) ?: return

                val menu = Utils.getLogViewMenu(activity)
                root.addView(menu)

//                val logWindow = Utils.getLogWindow(activity, root)
//                val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, Utils.getScreenHeight(activity))
//                root.addView(logWindow, lp)
            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {
                val root = Utils.getActivityRoot(activity) ?: return
                val menu = Utils.getLogViewMenu(activity)
                root.removeView(menu)
            }

            override fun onActivityDestroyed(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }
        })
    }

}