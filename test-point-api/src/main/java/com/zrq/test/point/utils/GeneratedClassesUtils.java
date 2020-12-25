package com.zrq.test.point.utils;

import android.text.TextUtils;

import com.zrq.test.point.Constants;
import com.zrq.test.point.entity.TestEntryPointInfo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 描述：获取生成的类的信息-工具
 *
 * @author zhangrq
 * createTime 2020/12/22 16:32
 */
public class GeneratedClassesUtils {

    /**
     * 获取自定义的【TestListFragment】类名
     */
    public static String getCustomTestListFragmentClassName() {
        // CustomTestListFragmentHelper getName
        try {
            Class<?> helperClass = Class.forName(Constants.CUSTOM_LIST_FRAGMENT_HELPER_PACKAGE + "." + Constants.CUSTOM_LIST_FRAGMENT_HELPER_CLASS_NAME);
            Method helperMethod = helperClass.getMethod(Constants.CUSTOM_LIST_FRAGMENT_HELPER_METHOD_NAME);
            return (String) helperMethod.invoke(helperClass);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * 获取自定义的【TestFragmentDetailsActivity】类名
     */
    public static String getCustomTestFragmentDetailsActivityClassName() {
        // CustomTestFragmentDetailsActivityHelper getName
        try {
            Class<?> helperClass = Class.forName(Constants.CUSTOM_FRAGMENT_DETAILS_HELPER_PACKAGE + "." + Constants.CUSTOM_FRAGMENT_DETAILS_HELPER_CLASS_NAME);
            Method helperMethod = helperClass.getMethod(Constants.CUSTOM_FRAGMENT_DETAILS_HELPER_METHOD_NAME);
            return (String) helperMethod.invoke(helperClass);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * 获取测试模块名列表
     */
    public static ArrayList<String> getTestModuleNameList() {
        // TestEnterModulesNameHelper getList
        ArrayList<String> list = new ArrayList<>();
        try {
            Class<?> helperClass = Class.forName(Constants.MODULES_NAME_HELPER_PACKAGE + "." + Constants.MODULES_NAME_HELPER_CLASS_NAME);
            Method helperMethod = helperClass.getMethod(Constants.MODULES_NAME_HELPER_METHOD_NAME);
            list.addAll((Collection<String>) helperMethod.invoke(helperClass));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取此模块下的【TestEntryPointInfo】列表
     */
    public static ArrayList<TestEntryPointInfo> getTestEnterPointModuleList(String moduleName) {
        // TestEnterPointModuleHelper getList
        ArrayList<TestEntryPointInfo> list = new ArrayList<>();
        if (!TextUtils.isEmpty(moduleName)) {
            // 数据ok，获取
            try {
                Class<?> helperClass = Class.forName(Constants.POINT_MODULE_HELPER_PACKAGE_PREFIX
                        + moduleName.replaceAll("[-_]", ".")// replaceAll解决名称含有[-][_]
                        + "."
                        + Constants.POINT_MODULE_HELPER_CLASS_NAME);
                Method helperMethod = helperClass.getMethod(Constants.POINT_MODULE_HELPER_METHOD_NAME);
                list.addAll((Collection<TestEntryPointInfo>) helperMethod.invoke(helperClass));
            } catch (Exception ignored) {
            }
        }
        return list;
    }
}
