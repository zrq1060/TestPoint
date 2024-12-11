package com.zrq.test.point.demo.kotlin

import android.util.Log
import androidx.lifecycle.ViewModel
import java.util.Random

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2024/11/6 09:07
 */
class TestViewModel : ViewModel() {
    val num = Random().nextInt()
    override fun onCleared() {
        super.onCleared()
        Log.e("aaaa", "TestViewModel=onCleared")
    }
}