package com.zrq.test.point.demo.kotlin.fragment

import android.app.Fragment
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zrq.test.point.annotation.TestEntryPoint

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/7 16:50
 */
@TestEntryPoint("module-kotlin-原生-Fragment")
class Fragment2 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val textView = TextView(container!!.context)
        textView.text = "module-kotlin-原生-Fragment"
        textView.textSize = 70f
        textView.setTextColor(Color.RED)
        return textView
    }
}