package me.simple.logview

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings


internal object Utils {

    lateinit var context: Context

    @TargetApi(Build.VERSION_CODES.M)
    fun reqWindowPermission() {
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.packageName))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun canShowWindow(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(context)
        }
        return true
    }

    fun init(context: Context) {
        this.context = context
    }

    fun getScreenHeight() = context.resources.displayMetrics.heightPixels
}