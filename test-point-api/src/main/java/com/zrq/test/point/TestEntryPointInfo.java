package com.zrq.test.point;

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/7 14:23
 */
public class TestEntryPointInfo {
    int type;// 类型，1：Activity、2：Fragment、3：Support Fragment、4：静态无参方法（全部）、5：非静态无参方法（TestListActivity子类）
    String name;//
    String className;
    String methodName;

    public TestEntryPointInfo(int type, String name, String className, String methodName) {
        this.type = type;
        this.name = name;
        this.className = className;
        this.methodName = methodName;
    }
}
