package com.zrq.test.point.compiler.common

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2024/4/15 下午3:36
 */
data class TestEntryPointInfo(
    val type: Int,
    val name: String,
    val className: String,
    val methodName: String?,
)
