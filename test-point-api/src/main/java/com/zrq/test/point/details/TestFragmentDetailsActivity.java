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
    private static final String KEY_FRAGMENT_BUNDLE = "key_fragment_bundle";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_test_fragment_details);
        if (savedInstanceState != null) return; // 配置变更，则不重新进行替换布局，Fragment会自恢复。
        String fragmentClassName = getIntent().getStringExtra(KEY_FRAGMENT_CLASS_NAME);
        Bundle fragmentBundle = getIntent().getBundleExtra(KEY_FRAGMENT_BUNDLE);
        try {
            Object instance = Class.forName(fragmentClassName).newInstance();
            if (instance instanceof androidx.fragment.app.Fragment) {
                // Support Fragment
                ((androidx.fragment.app.Fragment) instance).setArguments(fragmentBundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_test_details, (androidx.fragment.app.Fragment) instance)
                        .commitNow();
            } else {
                // Fragment
                ((Fragment) instance).setArguments(fragmentBundle);
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
    public static Intent newIntent(Context context, String fragmentClassName, Bundle fragmentBundle) {
        Intent intent = new Intent(context, TestFragmentDetailsActivity.class);
        intent.putExtras(newBundle(fragmentClassName, fragmentBundle));
        return intent;
    }

    /**
     * 创建跳到【TestFragmentDetailsActivity】需要的Bundle
     */
    public static Bundle newBundle(String fragmentClassName, Bundle fragmentBundle) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_FRAGMENT_CLASS_NAME, fragmentClassName);
        bundle.putBundle(KEY_FRAGMENT_BUNDLE, fragmentBundle);
        return bundle;
    }
}
