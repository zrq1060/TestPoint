package com.zrq.test.point.init

import com.zrq.test.point.entity.CustomTestListFragmentInfo
import com.zrq.test.point.entity.TestListItem
import com.zrq.test.point.utils.GeneratedClassesUtils

/**
 * 描述：项目初始化
 *
 * @author zhangrq
 * createTime 2020/12/7 14:23
 */
object TestEntryPointInit {
    val allModuleTestListData: ArrayList<TestListItem> = ArrayList()
    val allCustomTestListFragmentListData: ArrayList<CustomTestListFragmentInfo> = ArrayList()

    fun init() {
        val moduleNames = GeneratedClassesUtils.getTestModuleNameList()
        if (moduleNames.isEmpty()) {
            return
        }
        for (moduleName in moduleNames) {
            // 增加title
            allModuleTestListData.add(TestListItem(1, moduleName, null, null))
            // 增加child（注解生成信息）
            val testEntryPointModuleList =
                GeneratedClassesUtils.getTestEntryPointModuleList(moduleName)
            for (info in testEntryPointModuleList) {
                allModuleTestListData.add(TestListItem(2, null, info, null))
            }
            // CustomTestListFragment
            val customTestListFragmentClassNameList =
                GeneratedClassesUtils.getCustomTestListFragmentClassName(moduleName)
            for (fragmentClassName in customTestListFragmentClassNameList) {
                allCustomTestListFragmentListData.add(
                    CustomTestListFragmentInfo(
                        moduleName,
                        fragmentClassName
                    )
                )
            }
        }
    }
}
