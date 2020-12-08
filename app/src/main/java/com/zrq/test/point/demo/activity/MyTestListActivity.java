package com.zrq.test.point.demo.activity;

import android.widget.Toast;

import com.zrq.test.point.TestListActivity;
import com.zrq.test.point.annotation.TestEntryPoint;

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/7 16:57
 */
public class MyTestListActivity extends TestListActivity {

    @TestEntryPoint("App-非静态方法")
    public void test1() {
        Toast.makeText(getApplicationContext(), "App-非静态方法", Toast.LENGTH_SHORT).show();
    }
}
