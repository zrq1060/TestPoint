package com.zrq.test.point.demo.fragment;

import android.view.View;
import android.widget.Toast;

import com.zrq.test.point.annotation.TestEntryPoint;
import com.zrq.test.point.annotation.TestEntryPointListFragment;
import com.zrq.test.point.list.TestListFragment;

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/23 10:06
 */
@TestEntryPointListFragment
public class MyTestListFragment2 extends TestListFragment {

    @Override
    public void onAddTestItems() {
        addItem("app-手动添加方法-2", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "app-手动添加方法-2", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @TestEntryPoint("App-非静态方法-2")
    public void test1() {
        Toast.makeText(getContext(), "App-非静态方法-2", Toast.LENGTH_SHORT).show();
    }
}