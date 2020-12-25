package com.zrq.test.point.list;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.zrq.test.point.R;
import com.zrq.test.point.utils.GeneratedClassesUtils;

/**
 * 描述：测试列表Activity
 *
 * @author zhangrq
 * createTime 2020/12/7 14:23
 */
public class TestListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_test_list);
        // 设置内容Fragment
        String customClassName = GeneratedClassesUtils.getCustomTestListFragmentClassName();
        if (TextUtils.isEmpty(customClassName)) {
            // 无自定义TestListFragment，用TestListFragment
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_test_list, new TestListFragment()).commitAllowingStateLoss();
        } else {
            // 有自定义TestListFragment，用此进行显示
            try {
                Object instance = Class.forName(customClassName).newInstance();
                if (instance instanceof Fragment) {
                    // Support Fragment
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_test_list, (Fragment) instance).commitAllowingStateLoss();
                } else if (instance instanceof android.app.Fragment) {
                    // Fragment
                    getFragmentManager().beginTransaction().replace(R.id.fl_test_list, (android.app.Fragment) instance).commitAllowingStateLoss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
