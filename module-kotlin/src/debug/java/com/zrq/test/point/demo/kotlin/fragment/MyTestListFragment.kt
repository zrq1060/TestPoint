package com.zrq.test.point.demo.kotlin.fragment

import android.widget.Toast
import com.zrq.test.point.annotation.TestEntryPoint
import com.zrq.test.point.annotation.TestEntryPointListFragment
import com.zrq.test.point.list.TestListFragment

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/23 10:06
 */
@TestEntryPointListFragment
class MyTestListFragment : TestListFragment() {
    override fun onAddTestItems() {
        addItem("module-kotlin-手动添加方法") {
            Toast.makeText(context, "module-kotlin-手动添加方法", Toast.LENGTH_SHORT).show()
        }
    }

    @TestEntryPoint("module-kotlin-非静态方法")
    fun test1() {
        Toast.makeText(context, "module-kotlin-非静态方法", Toast.LENGTH_SHORT).show()
    }
}