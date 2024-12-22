package com.zrq.test.point.listener

import android.view.View

/**
 * 描述：通用Item点击监听
 *
 * @author zhangrq
 * createTime 2020/12/22 18:35
 */
interface OnItemClickListener<T> {
    fun onItemClick(view: View, item: T)
}