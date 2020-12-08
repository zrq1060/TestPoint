package com.zrq.test.point.demo;

import android.util.Log;

import com.zrq.test.point.annotation.TestEntryPoint;

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/7 17:52
 */
public class Test {

    @TestEntryPoint("App-静态方法")
    public static void test1() {
        Log.e("TestEntryPoint", "App-静态方法");
    }

}
