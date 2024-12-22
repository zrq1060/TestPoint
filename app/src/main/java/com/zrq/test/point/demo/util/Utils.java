package com.zrq.test.point.demo.util;

import android.content.Intent;
import android.os.Bundle;

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/7 17:52
 */
public class Utils {
    public static String getActivityArgs(Intent intent) {
        if (intent == null) {
            return "，args=null";
        } else {
            return "，args=name=" + intent.getStringExtra("name") + "=age=" + intent.getIntExtra("age", 0);
        }
    }

    public static String getFragmentArgs(Bundle bundle) {
        if (bundle == null) {
            return "，args=null";
        } else {
            return "，args=name=" + bundle.getString("name") + "=age=" + bundle.getInt("age", 0);
        }
    }
}
