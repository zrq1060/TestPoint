

> 一个用于帮助 Android App 进行测试的框架

#### 最新版本
[![Download](https://api.bintray.com/packages/zrq/TestPoint/test-point-api/images/download.svg)](https://bintray.com/zrq/TestPoint/test-point-api/_latestVersion)

#### Demo展示
![demo](https://github.com/zrq1060/TestPoint/blob/master/screenshots/test_point_demo.gif)

### 一、功能介绍

* **使用注解添加标注，方便简单**
* **支持Activity、Fragment、方法**
* **支持测试多个模块、指定测试模块**
* **未使用apk dex解析，性能提升**
* **可实现release环境微侵入**

### 二、基础功能

#### 1.添加依赖和配置

* **java**

	```
	android {
	    defaultConfig {
	        ...
	        javaCompileOptions {
	            annotationProcessorOptions {
	                arguments = [TEST_MODULE_NAME: project.getName()]
	            }
	        }
	    }
	}

	dependencies {
	    // xxx替换成最新版本, 各库版本相同
	    implementation 'com.zrq:test-point-annotation:xxx'
	    debugImplementation 'com.zrq:test-point-api:xxx'
	    debugAnnotationProcessor 'com.zrq:test-point-compiler:xxx'
	    ...
	}
	```

* **kotlin**

	```
	// 可以参考 module-kotlin 模块中的写法
	apply plugin: 'kotlin-kapt'

	kapt {
	    arguments {
	        arg("TEST_MODULE_NAME", project.getName())
	    }
	}

	dependencies {
	    // xxx替换成最新版本, 各库版本相同
	    implementation 'com.zrq:test-point-annotation:xxx'
	    debugImplementation 'com.zrq:test-point-api:xxx'
	    kaptDebug 'com.zrq:test-point-compiler:xxx'
	    ...
	}
	```

#### 2.标记要测试的模块

在任意类上添加`TestEntryPointModules`注解，标明要测试的`module名`。例如在`App`上添加

```
@TestEntryPointModules({"app", "module-java", "module-kotlin"})
public class App extends Application {

}
```

#### 3.标记要测试的Activity、Fragment

在`Activity`、`Fragment`类上添加`TestEntryPoint`注解，标明要测试的点

```
@TestEntryPoint("登录")
public class LoginActivity extends AppCompatActivity {
    ...
}
```
```
@TestEntryPoint("我的")
public class MineFragment extends Fragment {
    ...
}
```

#### 4.运行项目

运行项目，运行成功后，在`启动桌面`多了一个名为`Test`的启动图标，打开即可看到`测试列表`页面，`点击`对应的按钮即可跳到刚标注的`Activity`、`Fragment`

### 三、进阶用法

#### 1.指定静态无参方法

如果你想测试下和`Android无关`的（如，`测试字符串裁剪是否正确`等），可以用此方法
```
public class Test {

    @TestEntryPoint("App-静态方法")
    public static void test1() {
        ...
    }
}
```
#### 2.指定非静态无参方法

如果你想测试下和`Android相关`的（如，`开启Activity并传参`等），可以用此方法

##### 2.1.创建类

在`src`/`debug`/`java`/`包名`下，创建一个类，并继承`TestListFragment` ，如

![demo](https://github.com/zrq1060/TestPoint/blob/master/screenshots/create_debug_test_list_fragment.jpg)

##### 2.2.给类添加`TestEntryPointListFragment`注解

```
@TestEntryPointListFragment
public class MyTestListFragment extends TestListFragment {
    ...
}
```

##### 2.3.给方法添加`TestEntryPoint`注解

```
@TestEntryPointListFragment
public class MyTestListFragment extends TestListFragment {

    @TestEntryPoint("App-非静态方法")
    public void test1() {
        ...
    }
}
```

#### 3.指定手动添加方法
`需求场景同【2】`

在步骤`2.2`基础上，复写`onAddTestViews()`方法，在其内部调用`addItem`方法进行增加

```
@TestEntryPointListFragment
public class MyTestListFragment extends TestListFragment {

    @Override
    public void onAddTestViews() {
        addItem("app-手动添加方法", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ...
            }
        });
    }
}
```

### 四、注解说明

| 注解 | 功能说明 | 使用约束 | 使用范围 |
| :-- | :-- | :-- | :-- |
| TestEntryPoint | 标记测试进入点 | Activity、<br/>Fragment、<br/>静态无参方法、<br/>TestListFragment子类非静态无参方法 | 任意位置使用 |
| TestEntryPointModules | 标记要测试的模块名称 | 可放到任意类上 | 全局唯一 |
| TestEntryPointListFragment | 标记自定义<br/>TestListFragment点 | 只能是继承<br/>TestListFragment的类 | module下唯一 |
| TestEntryPointFragmentDetailsActivity | 标记自定义<br/>TestFragmentDetailsActivity点 | 只能是继承<br/>TestFragmentDetailsActivity的类 | 全局唯一 |

### 五、Q&A

1.之前`build.gradle`已配置`arguments`（如ARouter），该怎么配置？

* java
	```
	javaCompileOptions {
	    annotationProcessorOptions {
	        arguments = [AROUTER_MODULE_NAME: project.getName(),
	                     TEST_MODULE_NAME   : project.getName()]
	    }
	}
	```

* kotlin

	```
	kapt {
	    arguments {
	        arg("AROUTER_MODULE_NAME", project.getName())
	        arg("TEST_MODULE_NAME", project.getName())
	    }
	}
	```

2.`Build Output`栏中文乱码

解决办法见：https://blog.csdn.net/zhang5690800/article/details/104502632


### 六、沟通和交流

**QQ 交流群：** 793823227

**QQ：** 273902141