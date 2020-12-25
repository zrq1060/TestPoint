package com.zrq.test.point.demo.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.zrq.test.point.annotation.TestEntryPointFragmentDetailsActivity;
import com.zrq.test.point.details.TestFragmentDetailsActivity;

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/23 14:40
 */
@TestEntryPointFragmentDetailsActivity
public class MyTestFragmentDetailsActivity extends TestFragmentDetailsActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getApplicationContext(), "MyTestFragmentDetailsActivity", Toast.LENGTH_SHORT).show();
    }
}
