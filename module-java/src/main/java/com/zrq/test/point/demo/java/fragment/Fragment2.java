package com.zrq.test.point.demo.java.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zrq.test.point.annotation.TestEntryPoint;

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/7 16:50
 */
@TestEntryPoint("module-java-原生-Fragment")
public class Fragment2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(container.getContext());
        textView.setText("module-java-原生-Fragment");
        textView.setTextSize(70);
        textView.setTextColor(Color.RED);
        return textView;
    }
}
