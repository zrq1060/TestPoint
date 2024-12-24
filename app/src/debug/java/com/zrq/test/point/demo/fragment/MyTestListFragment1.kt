package com.zrq.test.point.demo.fragment

import com.zrq.test.point.annotation.TestEntryPointListFragment
import com.zrq.test.point.demo.activity.Activity1
import com.zrq.test.point.list.TestListFragment

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/23 10:06
 */
@TestEntryPointListFragment
class MyTestListFragment1 : TestListFragment() {

    override fun onAddTestItems() {
        addItem("Activity1-无参", Activity1::class.java)
        addItem("Fragment1-无参", Fragment1::class.java)

        addItem("Activity1-有参", Activity1::class.java, "name" to "张三", "age" to 20)
        addItem("Fragment1-有参", Fragment1::class.java, "name" to "张三", "age" to 20)

        addItem("自定义") {
            // 点击此按钮，执行此方法。
//            SharedPreferences

        }
    }
}

