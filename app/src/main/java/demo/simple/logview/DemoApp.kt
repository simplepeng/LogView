package demo.simple.logview

import android.app.Application
import me.simple.logview.LogView

class DemoApp : Application() {

    override fun onCreate() {
        super.onCreate()
        LogView.init(this)
    }
}