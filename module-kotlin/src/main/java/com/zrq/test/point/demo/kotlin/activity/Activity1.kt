package com.zrq.test.point.demo.kotlin.activity

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.zrq.test.point.annotation.TestEntryPoint

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/7 16:52
 */
@TestEntryPoint("module-kotlin-Support-Activity")
class Activity1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val textView = TextView(applicationContext)
        textView.text = "module-kotlin-Support-Activity"
        textView.textSize = 70f
        textView.setTextColor(Color.RED)
        setContentView(textView)
    }
}