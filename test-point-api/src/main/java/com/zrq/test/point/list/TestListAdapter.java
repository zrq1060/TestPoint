package com.zrq.test.point.list;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zrq.test.point.R;
import com.zrq.test.point.entity.TestEntryPointInfo;
import com.zrq.test.point.entity.TestListItem;
import com.zrq.test.point.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：测试列表Adapter
 *
 * @author zhangrq
 * createTime 2020/12/22 16:25
 */
public class TestListAdapter extends RecyclerView.Adapter<TestListViewHolder> {
    private final List<TestListItem> list = new ArrayList<>();
    private OnItemClickListener<TestEntryPointInfo> onAnnotationsItemClickListener;

    public TestListAdapter(List<TestListItem> list) {
        if (list != null && list.size() > 0)
            this.list.addAll(list);
    }

    @NonNull
    @Override
    public TestListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TestListViewHolder(View.inflate(parent.getContext(), R.layout.test_item_test_list, null));
    }

    @Override
    public void onBindViewHolder(@NonNull TestListViewHolder holder, int position) {
        TestListItem item = list.get(position);
        // ItemType：item类型，1：title（标题）、2：child（注解生成信息）、3：custom（手动添加点击）
        if (item.getItemType() == 1) {
            // title（标题）
            holder.itemTitle.setVisibility(View.VISIBLE);
            holder.itemContent.setVisibility(View.GONE);
            holder.itemTitle.setText(item.getTitle());
        } else if (item.getItemType() == 2) {
            // child（注解生成信息）
            holder.itemTitle.setVisibility(View.GONE);
            holder.itemContent.setVisibility(View.VISIBLE);
            final TestEntryPointInfo annotationsInfo = item.getAnnotationsInfo();
            holder.itemContent.setText(annotationsInfo != null ? annotationsInfo.getName() : null);
            holder.itemContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onAnnotationsItemClickListener != null && annotationsInfo != null)
                        onAnnotationsItemClickListener.onItemClick(v, annotationsInfo);
                }
            });
        } else {
            // custom（手动添加点击）
            holder.itemTitle.setVisibility(View.GONE);
            holder.itemContent.setVisibility(View.VISIBLE);
            holder.itemContent.setText(item.getTitle());
            holder.itemContent.setOnClickListener(item.getClickListener());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return list.get(position).getItemType() != 1 ? 1 : gridLayoutManager.getSpanCount();
                }
            });
        }
    }

    /**
     * 设置【注解生成信息item】的点击监听
     */
    public void setOnAnnotationsItemClickListener(OnItemClickListener<TestEntryPointInfo> onAnnotationsItemClickListener) {
        this.onAnnotationsItemClickListener = onAnnotationsItemClickListener;
    }

    /**
     * 手动调用，增加一条数据
     */
    public void addItem(String moduleName, String title, View.OnClickListener clickListener) {
        list.add(getAddPosition(moduleName), new TestListItem(3, title, null, clickListener));
        notifyDataSetChanged();
    }

    private int getAddPosition(String moduleName) {
        boolean isFindNextTitle = false;
        for (int i = 0; i < list.size(); i++) {
            TestListItem item = list.get(i);
            if (item.getItemType() == 1 && TextUtils.equals(item.getTitle(), moduleName)) {
                // 是title类型并且是此title，获取此Title下的最后一个
                isFindNextTitle = true;
                continue;// 退出此次循环，不走下面逻辑
            }
            if (isFindNextTitle && item.getItemType() == 1) {
                // 找到了下一个Title，返回此位置，即在此位置新增
                return i;
            }
        }
        return list.size();
    }
}
