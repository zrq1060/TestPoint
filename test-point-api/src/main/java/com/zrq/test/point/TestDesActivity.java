package com.zrq.test.point;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/7 14:23
 */
public class TestDesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_test_des);
        String className = getIntent().getStringExtra("className");
        boolean isSupport = getIntent().getBooleanExtra("isSupport", false);
        try {
            Object instance = Class.forName(className).newInstance();
            if (isSupport) {
                // Support Fragment
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, (androidx.fragment.app.Fragment) instance)
                        .commitAllowingStateLoss();
            } else {
                // Fragment
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, (Fragment) instance)
                        .commitAllowingStateLoss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Intent newIntent(Context context, boolean isSupport, String className) {
        Intent intent = new Intent(context, TestDesActivity.class);
        intent.putExtra("isSupport", isSupport);
        intent.putExtra("className", className);
        return intent;
    }
}
