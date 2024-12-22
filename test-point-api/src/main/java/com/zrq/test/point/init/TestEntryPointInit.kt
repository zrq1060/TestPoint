package com.zrq.test.point.init;

import com.zrq.test.point.entity.CustomTestListFragmentInfo;
import com.zrq.test.point.entity.TestEntryPointInfo;
import com.zrq.test.point.entity.TestListItem;
import com.zrq.test.point.utils.GeneratedClassesUtils;

import java.util.ArrayList;

/**
 * 描述：项目初始化
 *
 * @author zhangrq
 * createTime 2020/12/7 14:23
 */
public class TestEntryPointInit {
    private static final ArrayList<TestListItem> allModuleTestListData = new ArrayList<>();
    private static final ArrayList<CustomTestListFragmentInfo> allCustomTestListFragmentListData = new ArrayList<>();

    public static void init() {
        ArrayList<String> moduleNames = GeneratedClassesUtils.getTestModuleNameList();
        if (moduleNames.isEmpty()) {
            return;
        }
        for (String moduleName : moduleNames) {
            // 增加title
            allModuleTestListData.add(new TestListItem(1, moduleName, null, null));
            // 增加child（注解生成信息）
            ArrayList<TestEntryPointInfo> testEntryPointModuleList = GeneratedClassesUtils.getTestEntryPointModuleList(moduleName);
            for (TestEntryPointInfo info : testEntryPointModuleList) {
                allModuleTestListData.add(new TestListItem(2, null, info, null));
            }
            // CustomTestListFragment
            ArrayList<String> customTestListFragmentClassNameList = GeneratedClassesUtils.getCustomTestListFragmentClassName(moduleName);
            for (String fragmentClassName : customTestListFragmentClassNameList) {
                allCustomTestListFragmentListData.add(new CustomTestListFragmentInfo(moduleName, fragmentClassName));
            }
        }
    }

    public static ArrayList<TestListItem> getAllModuleTestListData() {
        return allModuleTestListData;
    }

    public static ArrayList<CustomTestListFragmentInfo> getAllCustomTestListFragmentListData() {
        return allCustomTestListFragmentListData;
    }
}
