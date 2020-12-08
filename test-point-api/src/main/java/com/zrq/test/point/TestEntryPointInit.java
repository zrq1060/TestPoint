package com.zrq.test.point;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/7 14:23
 */
public class TestEntryPointInit {
    private static final ArrayList<TestEntryPointInfo> allModuleTestEntryPointInfoList = new ArrayList<>();

    public static void init(String... moduleNames) {
        if (moduleNames == null || moduleNames.length == 0) {
            return;
        }
        for (String moduleName : moduleNames) {
            try {
                Class<?> helperClass = Class.forName(Constants.HELPER_PACKAGE_PREFIX
                        + moduleName.replaceAll("[-_]", ".") + "." + Constants.HELPER_CLASS_NAME);// replaceAll解决名称含有[-][_]
                Method getAllTestEntryPointInfoMethod = helperClass.getMethod(Constants.HELPER_METHOD_NAME);
                allModuleTestEntryPointInfoList.addAll((Collection<TestEntryPointInfo>) getAllTestEntryPointInfoMethod.invoke(helperClass));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<TestEntryPointInfo> getAllModuleTestEntryPointInfoList() {
        return allModuleTestEntryPointInfoList;
    }
}
