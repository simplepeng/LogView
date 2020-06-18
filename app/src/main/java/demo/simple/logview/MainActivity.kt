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
            LogView.v(tag, "创建入口点击 add.increase.C")
        }

        btnLogD.setOnClickListener {
            LogView.d(tag, "完成选择日期 add.SelectDate.IM")
        }

        btnLogI.setOnClickListener {
            LogView.i(tag, "选择情绪毛球01 add.selectmood.C[0]")
        }

        btnLogW.setOnClickListener {
            LogView.w(tag, "选择情绪毛球02 add.selectmood01.C[0]")
        }

        btnLogE.setOnClickListener {
            LogView.e(tag, "选择情绪毛球03 add.selectmood02.C[0]")
        }

        btnShow.performClick()
    }
}