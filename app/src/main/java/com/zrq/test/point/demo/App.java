package com.zrq.test.point.demo;

import android.app.Application;

import com.zrq.test.point.annotation.TestEntryPointModules;

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2020/12/7 16:41
 */
@TestEntryPointModules({"app", "module-java", "module-kotlin"})
public class App extends Application {

}