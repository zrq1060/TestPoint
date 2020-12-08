package com.zrq.test.point.compiler;

/**
 * 描述：常量
 *
 * @author zhangrq
 * createTime 2020/12/7 17:22
 */
public class Constants {

    public static final String ACTIVITY = "android.app.Activity";// 原生-Activity全路径名
    public static final String FRAGMENT = "android.app.Fragment";// 原生-Fragment全路径名
    public static final String FRAGMENT_SUPPORT_ANDROIDX = "androidx.fragment.app.Fragment";// Support-androidx-Fragment全路径名
    public static final String FRAGMENT_SUPPORT_V4 = "android.support.v4.app.Fragment";// Support-v4-Fragment全路径名

    public static final String TEST_LIST_ACTIVITY = "com.zrq.test.point.TestListActivity";// TestListActivity全路径名
    public static final String TEST_ENTRY_POINT_INFO = "com.zrq.test.point.TestEntryPointInfo";// TestEntryPointInfo全路径名

    public static final String HELPER_PACKAGE_PREFIX = "com.zrq.test.point.";// Helper包前缀名
    public static final String HELPER_CLASS_NAME = "TestEntryPointHelper";// Helper类名
    public static final String HELPER_METHOD_NAME = "getAllTestEntryPointInfo";// Helper方法名

}
