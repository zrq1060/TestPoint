package com.zrq.test.point.demo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zrq.test.point.annotation.TestEntryPoint;
import com.zrq.test.point.demo.User;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/7 16:52
 */
@TestEntryPoint("App-Support-Activity")
@AndroidEntryPoint
public class Activity1 extends AppCompatActivity {
    @Inject
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(getApplicationContext());
        textView.setText("App-Support-Activity");
        textView.setTextSize(100);
        textView.setTextColor(Color.RED);
        setContentView(textView);

        Toast.makeText(getApplicationContext(), user.toString(), Toast.LENGTH_SHORT).show();
    }
}
