package com.zrq.test.point.details;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zrq.test.point.R;

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/7 14:23
 */
public class TestFragmentDetailsActivity extends AppCompatActivity {
    private static final String KEY_FRAGMENT_CLASS_NAME = "fragment_Class_Name";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_test_fragment_details);
        if (savedInstanceState != null) return; // 配置变更，则不重新进行替换布局，Fragment会自恢复。
        String fragmentClassName = getIntent().getStringExtra(KEY_FRAGMENT_CLASS_NAME);
        try {
            Object instance = Class.forName(fragmentClassName).newInstance();
            if (instance instanceof androidx.fragment.app.Fragment) {
                // Support Fragment
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_test_details, (androidx.fragment.app.Fragment) instance)
                        .commitNow();
            } else {
                // Fragment
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_test_details, (Fragment) instance)
                        .commitAllowingStateLoss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建跳到【TestFragmentDetailsActivity】需要的Intent
     */
    public static Intent newIntent(Context context, String fragmentClassName) {
        Intent intent = new Intent(context, TestFragmentDetailsActivity.class);
        intent.putExtras(newBundle(fragmentClassName));
        return intent;
    }

    /**
     * 创建跳到【TestFragmentDetailsActivity】需要的Bundle
     */
    public static Bundle newBundle(String fragmentClassName) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_FRAGMENT_CLASS_NAME, fragmentClassName);
        return bundle;
    }
}
