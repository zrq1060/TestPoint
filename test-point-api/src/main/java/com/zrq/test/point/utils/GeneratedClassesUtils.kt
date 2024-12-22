package com.zrq.test.point.utils

import android.text.TextUtils
import com.zrq.test.point.Constants
import com.zrq.test.point.entity.TestEntryPointInfo

/**
 * 描述：获取生成的类的信息-工具
 *
 * @author zhangrq
 * createTime 2020/12/22 16:32
 */
object GeneratedClassesUtils {
    /**
     * 获取自定义的【TestFragmentDetailsActivity】类名，只有一个。
     */
    fun getCustomTestFragmentDetailsActivityClassName(): String? = try {
        val helperClass =
            Class.forName(Constants.TEST_ENTRY_POINT_FRAGMENT_DETAILS_HELPER_PACKAGE + "." + Constants.TEST_ENTRY_POINT_FRAGMENT_DETAILS_HELPER_CLASS_NAME)
        val helperMethod =
            helperClass.getMethod(Constants.TEST_ENTRY_POINT_FRAGMENT_DETAILS_HELPER_METHOD_NAME)
        helperMethod.invoke(helperClass) as String
    } catch (ignored: Exception) {
        null
    }

    /**
     * 获取测试模块名列表，多个。
     */
    fun getTestModuleNameList(): List<String> = try {
        val helperClass =
            Class.forName(Constants.TEST_ENTRY_POINT_MODULES_HELPER_PACKAGE + "." + Constants.TEST_ENTRY_POINT_MODULES_HELPER_CLASS_NAME)
        val helperMethod =
            helperClass.getMethod(Constants.TEST_ENTRY_POINT_MODULES_HELPER_METHOD_NAME)
        (helperMethod.invoke(helperClass) as List<String>)
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }

    /**
     * 获取此模块下的【TestEntryPointInfo】列表，一个模块下多个。
     */
    fun getTestEntryPointModuleList(moduleName: String): ArrayList<TestEntryPointInfo> {
        val list = ArrayList<TestEntryPointInfo>()
        if (!TextUtils.isEmpty(moduleName)) {
            // 数据ok，获取
            try {
                val helperClass = Class.forName(
                    (Constants.TEST_ENTRY_POINT_HELPER_PACKAGE_PREFIX + moduleName.replace(
                        "[-_]".toRegex(),
                        "."
                    ) // replaceAll解决名称含有[-][_]
                            + "." + Constants.TEST_ENTRY_POINT_HELPER_CLASS_NAME)
                )
                val helperMethod =
                    helperClass.getMethod(Constants.TEST_ENTRY_POINT_HELPER_METHOD_NAME)
                list.addAll((helperMethod.invoke(helperClass) as Collection<TestEntryPointInfo>))
            } catch (ignored: Exception) {
            }
        }
        return list
    }


    /**
     * 获取此模块下自定义的【TestListFragment】类名，一个模块下多个。
     */
    fun getCustomTestListFragmentClassName(moduleName: String): ArrayList<String> {
        val list = ArrayList<String>()
        try {
            val helperClass = Class.forName(
                (Constants.TEST_ENTRY_POINT_LIST_FRAGMENT_HELPER_PACKAGE_PREFIX + moduleName.replace(
                    "[-_]".toRegex(),
                    "."
                ) // replaceAll解决名称含有[-][_]
                        + "." + Constants.TEST_ENTRY_POINT_LIST_FRAGMENT_HELPER_CLASS_NAME)
            )
            val helperMethod =
                helperClass.getMethod(Constants.TEST_ENTRY_POINT_LIST_FRAGMENT_HELPER_METHOD_NAME)
            list.addAll((helperMethod.invoke(helperClass) as Collection<String>))
        } catch (ignored: Exception) {
        }
        return list
    }
}
