package com.zrq.test.point.list

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zrq.test.point.R
import com.zrq.test.point.entity.TestEntryPointInfo
import com.zrq.test.point.entity.TestListItem
import com.zrq.test.point.listener.OnItemClickListener

/**
 * 描述：测试列表Adapter
 *
 * @author zhangrq
 * createTime 2020/12/22 16:25
 */
class TestListAdapter : RecyclerView.Adapter<TestListViewHolder>() {
    private val list: ArrayList<TestListItem> = arrayListOf()
    private var onAnnotationsItemClickListener: OnItemClickListener<TestEntryPointInfo>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestListViewHolder {
        return TestListViewHolder(View.inflate(parent.context, R.layout.test_item_test_list, null))
    }

    override fun onBindViewHolder(holder: TestListViewHolder, position: Int) {
        val item = list[position]
        // ItemType：item类型，1：title（标题）、2：child（注解生成信息）、3：custom（手动添加点击）
        when (item.itemType) {
            1 -> {
                // title（标题）
                holder.itemTitle.visibility = View.VISIBLE
                holder.itemContent.visibility = View.GONE
                holder.itemTitle.text = item.title
            }

            2 -> {
                // child（注解生成信息）
                holder.itemTitle.visibility = View.GONE
                holder.itemContent.visibility = View.VISIBLE
                val annotationsInfo = item.annotationsInfo
                holder.itemContent.text = annotationsInfo?.name
                holder.itemContent.setOnClickListener { v: View ->
                    if (onAnnotationsItemClickListener != null && annotationsInfo != null)
                        onAnnotationsItemClickListener!!.onItemClick(v, annotationsInfo)
                }
            }

            else -> {
                // custom（手动添加点击）
                holder.itemTitle.visibility = View.GONE
                holder.itemContent.visibility = View.VISIBLE
                holder.itemContent.text = item.title
                holder.itemContent.setOnClickListener(item.clickListener)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (list[position].itemType != 1) 1 else layoutManager.spanCount
                }
            }
        }
    }

    fun submitList(list: List<TestListItem>) {
        this.list.clear()
        this.list.addAll(list)
    }

    /**
     * 设置【注解生成信息item】的点击监听
     */
    fun setOnAnnotationsItemClickListener(onAnnotationsItemClickListener: OnItemClickListener<TestEntryPointInfo>?) {
        this.onAnnotationsItemClickListener = onAnnotationsItemClickListener
    }

    /**
     * 手动调用，增加一条数据
     */
    @SuppressLint("NotifyDataSetChanged")
    fun addItem(moduleName: String, title: String?, clickListener: View.OnClickListener?) {
        list.add(getAddPosition(moduleName), TestListItem(3, title, null, clickListener))
        notifyDataSetChanged()
    }

    private fun getAddPosition(moduleName: String): Int {
        var isFindNextTitle = false
        for (i in list.indices) {
            val item = list[i]
            if (item.itemType == 1 && TextUtils.equals(item.title, moduleName)) {
                // 是title类型并且是此title，获取此Title下的最后一个
                isFindNextTitle = true
                continue  // 退出此次循环，不走下面逻辑
            }
            if (isFindNextTitle && item.itemType == 1) {
                // 找到了下一个Title，返回此位置，即在此位置新增
                return i
            }
        }
        return list.size
    }
}
