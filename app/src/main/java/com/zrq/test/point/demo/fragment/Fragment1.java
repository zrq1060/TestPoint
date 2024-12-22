package com.zrq.test.point.demo.fragment;

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
@TestEntryPoint("App-Support-Fragment")
public class Fragment1 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(container.getContext());
        textView.setText("App-Support-Fragment，name=" + getArguments().getString("name"));
        textView.setTextSize(100);
        textView.setTextColor(Color.RED);
        return textView;
    }
}
