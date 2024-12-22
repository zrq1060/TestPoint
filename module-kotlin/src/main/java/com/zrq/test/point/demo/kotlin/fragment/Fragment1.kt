package com.zrq.test.point.demo.kotlin.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zrq.test.point.annotation.TestEntryPoint
import com.zrq.test.point.demo.kotlin.TestViewModel

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/7 16:48
 */
@TestEntryPoint("module-kotlin-Support-Fragment")
class Fragment1 : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this)[TestViewModel::class.java] }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val textView = TextView(container!!.context)
        textView.text = "module-kotlin-Support-Fragment=${viewModel.num}"
        textView.textSize = 70f
        textView.setTextColor(Color.RED)
        return textView
    }
}