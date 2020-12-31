package com.zrq.test.point.entity;

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/28 16:17
 */
public class CustomTestListFragmentInfo {
    private final String moduleName;
    private final String fragmentClassName;

    public CustomTestListFragmentInfo(String moduleName, String fragmentClassName) {
        this.moduleName = moduleName;
        this.fragmentClassName = fragmentClassName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getFragmentClassName() {
        return fragmentClassName;
    }
}
