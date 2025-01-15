package com.zrq.test.point.demo.java.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zrq.test.point.annotation.TestEntryPoint;

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/7 16:52
 */
@TestEntryPoint("module-java-Support-Activity")
public class Activity1 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        TextView textView = new TextView(getApplicationContext());
        textView.setText("module-java-Support-Activity");
        textView.setTextSize(70);
        textView.setTextColor(Color.RED);
        setContentView(textView);
    }
}
