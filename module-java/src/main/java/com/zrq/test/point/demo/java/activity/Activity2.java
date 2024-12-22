package com.zrq.test.point.demo.java.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zrq.test.point.annotation.TestEntryPoint;

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/7 16:52
 */
@TestEntryPoint("module-java-原生-Activity")
public class Activity2 extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(getApplicationContext());
        textView.setText("module-java-原生-Activity");
        textView.setTextSize(70);
        textView.setTextColor(Color.RED);
        setContentView(textView);
    }
}
