package com.zrq.test.point.demo.kotlin.activity

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import com.zrq.test.point.annotation.TestEntryPoint

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/7 16:52
 */
@TestEntryPoint("module-kotlin-原生-Activity")
class Activity2 : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(applicationContext)
        textView.text = "module-kotlin-原生-Activity"
        textView.textSize = 70f
        textView.setTextColor(Color.RED)
        setContentView(textView)
    }
}