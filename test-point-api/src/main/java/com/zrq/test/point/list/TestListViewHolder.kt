package com.zrq.test.point.list;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zrq.test.point.R;


/**
 * 描述：测试列表ViewHolder
 *
 * @author zhangrq
 * createTime 2020/12/22 16:23
 */
public class TestListViewHolder extends RecyclerView.ViewHolder {
    TextView itemTitle;
    TextView itemContent;

    public TestListViewHolder(@NonNull View itemView) {
        super(itemView);
        itemTitle = itemView.findViewById(R.id.tv_item_title);
        itemContent = itemView.findViewById(R.id.tv_item_content);
    }
}