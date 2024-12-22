package com.zrq.test.point.demo.fragment

import com.zrq.test.point.annotation.TestEntryPointListFragment
import com.zrq.test.point.demo.activity.Activity1
import com.zrq.test.point.demo.activity.Activity2
import com.zrq.test.point.list.TestListFragment

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/23 10:06
 */
@TestEntryPointListFragment
class MyTestListFragment1 : TestListFragment() {

    override fun onAddTestViews() {
        addItem("Activity1-无参", Activity1::class.java)
        addItem("Fragment1-无参", Fragment1::class.java)

        addItem("Activity2-有参", Activity2::class.java, "name" to "张三", "age" to 20)
        addItem("Fragment2-有参", Fragment2::class.java, "name" to "张三", "age" to 20)
    }
}

