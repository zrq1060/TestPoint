package com.zrq.test.point.demo.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
public class MyTestListFragment extends TestListFragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter.addItem("手动添加方法", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "手动添加方法", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @TestEntryPoint("App-非静态方法")
    public void test1() {
        Toast.makeText(getContext(), "App-非静态方法", Toast.LENGTH_SHORT).show();
    }
}
