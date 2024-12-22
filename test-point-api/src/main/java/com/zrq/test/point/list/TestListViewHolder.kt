package com.zrq.test.point.list

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zrq.test.point.R

/**
 * 描述：测试列表ViewHolder
 *
 * @author zhangrq
 * createTime 2020/12/22 16:23
 */
class TestListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var itemTitle: TextView = itemView.findViewById(R.id.tv_item_title)
    var itemContent: TextView = itemView.findViewById(R.id.tv_item_content)
}