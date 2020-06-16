package demo.simple.logview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import me.simple.logview.LogView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tag = "MainActivity"

        btnShow.setOnClickListener {
            LogView.show()
        }

        btnDismiss.setOnClickListener {
            LogView.dismiss()
        }

        btnLogV.setOnClickListener {
            LogView.v(tag, "")
        }

        btnLogD.setOnClickListener {
            LogView.d(tag, "")
        }

        btnLogI.setOnClickListener {
            LogView.i(tag, "")
        }

        btnLogW.setOnClickListener {
            LogView.w(tag, "")
        }

        btnLogE.setOnClickListener {
            LogView.e(tag, "")
        }

        btnShow.performClick()
    }
}