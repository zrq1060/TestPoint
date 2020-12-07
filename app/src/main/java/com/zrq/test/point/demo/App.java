package com.zrq.test.point.demo;

import android.app.Application;

import com.zrq.test.point.TestEntryPointInit;

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/7 16:41
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TestEntryPointInit.init("app");
    }
}
