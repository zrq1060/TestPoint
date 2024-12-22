package com.zrq.test.point.entity

/**
 * 描述：TestEntryPoint注解信息
 *
 * @author zhangrq
 * createTime 2020/12/7 14:23
 */
data class TestEntryPointInfo(
    val type: Int, // 类型，1：Activity、2：Fragment、3：Support Fragment、4：静态无参方法、5：非静态无参方法（TestListFragment子类）
    val name: String, // 按钮名称
    val className: String,
    val methodName: String?
)