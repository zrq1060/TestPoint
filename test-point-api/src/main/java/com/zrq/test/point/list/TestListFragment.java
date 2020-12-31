package com.zrq.test.point.list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 描述：测试列表Fragment
 *
 * @author zhangrq
 * createTime 2020/12/22 17:23
 */
public class TestListFragment extends Fragment {
    private static final String KEY_MODULE_NAME = "module_name";
    private String moduleName;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        moduleName = getArguments().getString(KEY_MODULE_NAME);
    }

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onAddTestViews();
    }

    /**
     * 增加测试Item，内部调用【addItem】
     */
    public void onAddTestViews() {

    }

    /**
     * 增加Item
     *
     * @param title         名称
     * @param clickListener 点击事件
     */
    public void addItem(String title, View.OnClickListener clickListener) {
        ((TestListActivity) getActivity()).adapter.addItem(moduleName, title, clickListener);
    }

    /**
     * 创建此类所需的Bundle
     *
     * @param moduleName 模块名称
     */
    public static Bundle newBundle(String moduleName) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_MODULE_NAME, moduleName);
        return bundle;
    }
}
