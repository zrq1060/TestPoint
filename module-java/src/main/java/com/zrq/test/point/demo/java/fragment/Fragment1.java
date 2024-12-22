package com.zrq.test.point.demo.java.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zrq.test.point.annotation.TestEntryPoint;

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/7 16:48
 */
@TestEntryPoint("module-java-Support-Fragment")
public class Fragment1 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(container.getContext());
        textView.setText("module-java-Support-Fragment");
        textView.setTextSize(70);
        textView.setTextColor(Color.RED);
        return textView;
    }
}
