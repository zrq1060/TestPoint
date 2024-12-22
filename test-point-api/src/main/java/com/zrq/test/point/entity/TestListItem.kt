package com.zrq.test.point.entity

import android.view.View

/**
 * 描述：测试列表Item信息
 *
 * @author zhangrq
 * createTime 2020/12/22 16:55
 */
data class TestListItem(
    val itemType: Int, // item类型，1：title（标题）、2：child（注解生成信息）、3：custom（手动添加点击）
    val title: String?, // 标题，itemType为1、3时用。
    val annotationsInfo: TestEntryPointInfo?, // 注解生成信息，itemType为2时用。
    val clickListener: View.OnClickListener? // 手动添加点击监听，itemType为3时用。
)