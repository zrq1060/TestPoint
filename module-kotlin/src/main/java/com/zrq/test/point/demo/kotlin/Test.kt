package com.zrq.test.point.demo.kotlin

import android.util.Log
import com.zrq.test.point.annotation.TestEntryPoint

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/7 17:52
 */
object Test {

    @TestEntryPoint("module-kotlin-object-静态方法")
    @JvmStatic
    fun test1() {
        Log.e("TestEntryPoint", "module-kotlin-object-静态方法")
    }
//    @TestEntryPoint("module-kotlin-object-静态方法")
    // TODO 改
    @JvmStatic
    fun String.test2() {
        Log.e("TestEntryPoint", "module-kotlin-object-静态方法")
    }
}

// TODO 改
@TestEntryPoint("module-kotlin-包级函数-静态方法")
fun test2() {
    Log.e("TestEntryPoint", "module-kotlin-包级函数-静态方法")
}