package com.zrq.test.point.demo.java;

import android.util.Log;

import com.zrq.test.point.annotation.TestEntryPoint;

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/7 17:52
 */
public class Test {

    @TestEntryPoint("module-java-静态方法")
    public static void test1() {
        Log.e("TestEntryPoint", "module-java-静态方法");
    }
}
