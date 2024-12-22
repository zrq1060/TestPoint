package com.zrq.test.point.entity;

import android.view.View;

/**
 * 描述：测试列表Item信息
 *
 * @author zhangrq
 * createTime 2020/12/22 16:55
 */
public class TestListItem {
    private final int itemType;// item类型，1：title（标题）、2：child（注解生成信息）、3：custom（手动添加点击）
    private final String title;// 标题
    private final TestEntryPointInfo annotationsInfo;// 注解生成信息
    private final View.OnClickListener clickListener;// 手动添加点击监听

    public TestListItem(int itemType, String title, TestEntryPointInfo annotationsInfo, View.OnClickListener clickListener) {
        this.itemType = itemType;
        this.title = title;
        this.annotationsInfo = annotationsInfo;
        this.clickListener = clickListener;
    }

    public int getItemType() {
        return itemType;
    }

    public String getTitle() {
        return title;
    }

    public TestEntryPointInfo getAnnotationsInfo() {
        return annotationsInfo;
    }

    public View.OnClickListener getClickListener() {
        return clickListener;
    }
}
