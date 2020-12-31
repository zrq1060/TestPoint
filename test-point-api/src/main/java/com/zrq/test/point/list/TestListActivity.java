package com.zrq.test.point.list;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zrq.test.point.R;
import com.zrq.test.point.details.TestFragmentDetailsActivity;
import com.zrq.test.point.entity.CustomTestListFragmentInfo;
import com.zrq.test.point.entity.TestEntryPointInfo;
import com.zrq.test.point.init.TestEntryPointInit;
import com.zrq.test.point.listener.OnItemClickListener;
import com.zrq.test.point.utils.GeneratedClassesUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * 描述：测试列表Activity
 *
 * @author zhangrq
 * createTime 2020/12/7 14:23
 */
public class TestListActivity extends AppCompatActivity {
    public TestListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_test_list);
        RecyclerView recyclerView = findViewById(R.id.rv_test_list_content);
        // 初始化内容RecyclerView
        initContentRecyclerView(recyclerView);// 重建后需要重新初始化，因为adapter等已丢失
        if (savedInstanceState == null) {
            // 初始化 CustomTestListFragment
            initCustomTestListFragment();// 重建后不需要重新初始化，因为Fragment已经增加
        }
    }

    // 初始化 CustomTestListFragment
    private void initCustomTestListFragment() {
        // 设置所有CustomTestListFragment显示
        ArrayList<CustomTestListFragmentInfo> customTestListFragmentInfoList = TestEntryPointInit.getAllCustomTestListFragmentListData();
        if (customTestListFragmentInfoList != null && customTestListFragmentInfoList.size() > 0) {
            // 有CustomTestListFragment，add到View
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            for (CustomTestListFragmentInfo customTestListFragmentInfo : customTestListFragmentInfoList) {
                try {
                    // 有约束，customClass 是TestListFragment的子类，所以用SupportFragmentManager进行add
                    Fragment fragment = (Fragment) Class.forName(customTestListFragmentInfo.getFragmentClassName()).newInstance();
                    fragment.setArguments(TestListFragment.newBundle(customTestListFragmentInfo.getModuleName()));
                    fragmentTransaction.add(R.id.fl_test_list_custom_layout, fragment, customTestListFragmentInfo.getFragmentClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    // 初始化内容RecyclerView
    private void initContentRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
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
                        startActivity(new Intent(getApplicationContext(), Class.forName(item.getClassName())));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (item.getType() == 2 || item.getType() == 3) {
                    // Fragment、Support Fragment
                    String customDetailsClassName = GeneratedClassesUtils.getCustomTestFragmentDetailsActivityClassName();
                    if (TextUtils.isEmpty(customDetailsClassName)) {
                        // 无自定义，使用TestFragmentDetailsActivity
                        startActivity(TestFragmentDetailsActivity.newIntent(getApplicationContext(), item.getClassName()));
                    } else {
                        // 有自定义，使用自定义
                        try {
                            Class<?> customDetailsClass = Class.forName(customDetailsClassName);
                            Intent intent = new Intent(getApplicationContext(), customDetailsClass);
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
                        method.invoke(item.getType() == 4 ? clazz : getSupportFragmentManager().findFragmentByTag(item.getClassName()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
