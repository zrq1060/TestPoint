package com.zrq.test.point.list;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zrq.test.point.details.TestFragmentDetailsActivity;
import com.zrq.test.point.entity.TestEntryPointInfo;
import com.zrq.test.point.init.TestEntryPointInit;
import com.zrq.test.point.listener.OnItemClickListener;
import com.zrq.test.point.utils.GeneratedClassesUtils;

import java.lang.reflect.Method;

/**
 * 描述：测试列表Fragment
 *
 * @author zhangrq
 * createTime 2020/12/22 17:23
 */
public class TestListFragment extends Fragment {

    public TestListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = new RecyclerView(getContext());
        initRecyclerView(recyclerView);
        return recyclerView;
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new TestListAdapter(TestEntryPointInit.getAllModuleTestListData());
        recyclerView.setAdapter(adapter);
        // 设置注解信息item点击监听
        adapter.setOnAnnotationsItemClickListener(new OnItemClickListener<TestEntryPointInfo>() {
            @Override
            public void onItemClick(View view, TestEntryPointInfo item) {
                // type：1：Activity、2：Fragment、3：Support Fragment、4：静态无参方法、5：非静态无参方法（TestListFragment子类）
                if (item.getType() == 1) {
                    // Activity
                    try {
                        startActivity(new Intent(getContext(), Class.forName(item.getClassName())));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (item.getType() == 2 || item.getType() == 3) {
                    // Fragment、Support Fragment
                    String customDetailsClassName = GeneratedClassesUtils.getCustomTestFragmentDetailsActivityClassName();
                    if (TextUtils.isEmpty(customDetailsClassName)) {
                        // 无自定义，使用TestFragmentDetailsActivity
                        startActivity(TestFragmentDetailsActivity.newIntent(getContext(), item.getClassName()));
                    } else {
                        // 有自定义，使用自定义
                        try {
                            Class<?> customDetailsClass = Class.forName(customDetailsClassName);
                            Intent intent = new Intent(getContext(), customDetailsClass);
                            intent.putExtras(TestFragmentDetailsActivity.newBundle(item.getClassName()));
                            startActivity(intent);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (item.getType() == 4 || item.getType() == 5) {
                    // 静态无参方法、非静态无参方法（TestListFragment子类）
                    try {
                        Class<?> clazz = Class.forName(item.getClassName());
                        Method method = clazz.getDeclaredMethod(item.getMethodName());
                        method.setAccessible(true);
                        method.invoke(item.getType() == 4 ? clazz : TestListFragment.this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
