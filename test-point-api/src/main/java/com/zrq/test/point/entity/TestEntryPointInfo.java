package com.zrq.test.point.entity;

/**
 * 描述：TestEntryPoint注解信息
 *
 * @author zhangrq
 * createTime 2020/12/7 14:23
 */
public class TestEntryPointInfo {
    private final int type;// 类型，1：Activity、2：Fragment、3：Support Fragment、4：静态无参方法、5：非静态无参方法（TestListFragment子类）
    private final String name;// 按钮名称
    private final String className;
    private final String methodName;

    public TestEntryPointInfo(int type, String name, String className, String methodName) {
        this.type = type;
        this.name = name;
        this.className = className;
        this.methodName = methodName;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }
}
