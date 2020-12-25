package com.zrq.test.point.compiler;

/**
 * 描述：常量
 *
 * @author zhangrq
 * createTime 2020/12/7 17:22
 */
public class Constants {

    public static final String TEST_MODULE_NAME = "TEST_MODULE_NAME";// 参数-模块名称
    // 系统类信息
    public static final String ACTIVITY = "android.app.Activity";// 原生-Activity全路径名
    public static final String FRAGMENT = "android.app.Fragment";// 原生-Fragment全路径名
    public static final String FRAGMENT_SUPPORT_ANDROIDX = "androidx.fragment.app.Fragment";// Support-androidx-Fragment全路径名
    public static final String FRAGMENT_SUPPORT_V4 = "android.support.v4.app.Fragment";// Support-v4-Fragment全路径名
    // 自己已有类信息
    public static final String TEST_LIST_FRAGMENT = "com.zrq.test.point.list.TestListFragment";// TestListFragment全路径名
    public static final String TEST_FRAGMENT_DETAILS_ACTIVITY = "com.zrq.test.point.details.TestFragmentDetailsActivity";// TestFragmentDetailsActivity全路径名
    public static final String TEST_ENTRY_POINT_INFO = "com.zrq.test.point.entity.TestEntryPointInfo";// TestEntryPointInfo全路径名
    // 自己生成类信息
    // -Point Module Helper
    public static final String POINT_MODULE_HELPER_PACKAGE_PREFIX = "com.zrq.test.point.";// 包前缀名
    public static final String POINT_MODULE_HELPER_CLASS_NAME = "TestEnterPointModuleHelper";// 类名
    public static final String POINT_MODULE_HELPER_METHOD_NAME = "getList";// 方法名
    // -Modules Name Helper
    public static final String MODULES_NAME_HELPER_PACKAGE = "com.zrq.test.point";// 包名
    public static final String MODULES_NAME_HELPER_CLASS_NAME = "TestEnterModulesNameHelper";// 类名
    public static final String MODULES_NAME_HELPER_METHOD_NAME = "getList";// 方法名
    // -Custom TestListFragment Helper
    public static final String CUSTOM_LIST_FRAGMENT_HELPER_PACKAGE = "com.zrq.test.point";// 包名
    public static final String CUSTOM_LIST_FRAGMENT_HELPER_CLASS_NAME = "CustomTestListFragmentHelper";// 类名
    public static final String CUSTOM_LIST_FRAGMENT_HELPER_METHOD_NAME = "getName";// 方法名
    // -Custom TestFragmentDetailsActivity Helper
    public static final String CUSTOM_FRAGMENT_DETAILS_HELPER_PACKAGE = "com.zrq.test.point";// 包名
    public static final String CUSTOM_FRAGMENT_DETAILS_HELPER_CLASS_NAME = "CustomTestFragmentDetailsActivityHelper";// 类名
    public static final String CUSTOM_FRAGMENT_DETAILS_HELPER_METHOD_NAME = "getName";// 方法名

}
