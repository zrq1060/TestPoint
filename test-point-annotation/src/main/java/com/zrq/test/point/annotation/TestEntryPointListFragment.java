package com.zrq.test.point.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述：标记要测试的列表Fragment
 *
 * @author zhangrq
 * createTime 2020/12/22 15:17
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestEntryPointListFragment {
}