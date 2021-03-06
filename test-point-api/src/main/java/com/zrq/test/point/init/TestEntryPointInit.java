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
        if (moduleNames == null || moduleNames.size() == 0) {
            return;
        }
        for (String moduleName : moduleNames) {
            // 增加title
            allModuleTestListData.add(new TestListItem(1, moduleName, null, null));
            // 增加child（注解生成信息）
            ArrayList<TestEntryPointInfo> testEnterPointModuleList = GeneratedClassesUtils.getTestEnterPointModuleList(moduleName);
            for (TestEntryPointInfo info : testEnterPointModuleList) {
                allModuleTestListData.add(new TestListItem(2, null, info, null));
            }
            // CustomTestListFragment
            allCustomTestListFragmentListData.add(new CustomTestListFragmentInfo(moduleName, GeneratedClassesUtils.getCustomTestListFragmentClassName(moduleName)));
        }
    }

    public static ArrayList<TestListItem> getAllModuleTestListData() {
        return allModuleTestListData;
    }

    public static ArrayList<CustomTestListFragmentInfo> getAllCustomTestListFragmentListData() {
        return allCustomTestListFragmentListData;
    }
}
