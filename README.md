# TestPoint

> 一个用于帮助 Android App 进行测试、修改配置、模块化的框架

### 最新版本
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.zrq1060/test-point-api/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.zrq1060/test-point-api)

### Demo展示
![demo](https://github.com/zrq1060/TestPoint/blob/master/screenshots/test_point_demo.gif)

# 一、功能介绍

* **使用注解添加标注，方便简单**
* **支持Activity、Fragment、方法**
* **支持测试多个模块、指定测试模块**
* **未使用apk dex解析，性能提升**
* **可实现release环境微侵入、零侵入**

# 二、基础功能

## 1.添加依赖和配置

* **kapt**

  ``` gradle
  // 可以参考 module-kotlin 模块中的写法
  plugins {
      id 'kotlin-kapt'
  }

  kapt {
      arguments {
          arg("TEST_MODULE_NAME", project.getName())
      }
  }

  dependencies {
      implementation 'io.github.zrq1060:test-point-annotation:0.0.1-alpha03'
      debugImplementation 'io.github.zrq1060:test-point-api:0.0.1-alpha03'
      kaptDebug 'io.github.zrq1060:test-point-compiler:0.0.1-alpha03'
  }
  ```

* **ksp（推荐）**

  ``` gradle
  // 可以参考 module-kotlin 模块中的写法
  plugins {
      alias(libs.plugins.ksp)
  }

  ksp {
      arg("TEST_MODULE_NAME", project.getName())
  }

  dependencies {
      implementation 'io.github.zrq1060:test-point-annotation:0.0.1-alpha03'
      debugImplementation 'io.github.zrq1060:test-point-api:0.0.1-alpha03'
      kspDebug 'io.github.zrq1060:test-point-ksp:0.0.1-alpha03'
  }
  ```
  并在`gradle.properties`内添加如下
  ```
  # 禁用增量
  ksp.incremental=false
  ```

## 2.标记要测试的模块

在任意类上添加`TestEntryPointModules`注解，标明要测试的`module名`（没传值，默认为`当前模块`）。例如在`App`上添加

```java
@TestEntryPointModules({"app", "module-java", "module-kotlin"})
public class App extends Application {

}
```

## 3.标记要测试的Activity、Fragment

在`Activity`、`Fragment`类上添加`TestEntryPoint`注解，标明要测试的点。

```java
@TestEntryPoint("登录")
public class LoginActivity extends AppCompatActivity {
    ...
}
```
```java
@TestEntryPoint("我的")
public class MineFragment extends Fragment {
    ...
}
```

## 4.运行项目

运行项目，运行成功后，在`启动桌面`多了一个名为`Test`的启动图标，打开即可看到`测试列表`页面，`点击`对应的按钮即可跳到刚标注的`Activity`、`Fragment`。

# 三、进阶用法

## 1.指定静态无参方法

如果你想测试和`Android无关`的（如，`测试字符串拼接是否正确`等），可以用此方法。
```java
public class Test {

    @TestEntryPoint("App-静态方法")
    public static void test1() {
        ...
    }
}
```
## 2.指定非静态无参方法

如果你想测试和`Android相关`的（如，`SharedPreferences获取的值是否正确`等），可以用此方法。

### 2.1.创建类

在`src`/`debug`/`java`/`包名`下，创建一个类，并继承`TestListFragment` ，如

![demo](https://github.com/zrq1060/TestPoint/blob/master/screenshots/create_debug_test_list_fragment.jpg)

### 2.2.给类添加`TestEntryPointListFragment`注解

```java
@TestEntryPointListFragment
public class MyTestListFragment extends TestListFragment {
    ...
}
```

### 2.3.给方法添加`TestEntryPoint`注解

```java
@TestEntryPointListFragment
public class MyTestListFragment extends TestListFragment {

    @TestEntryPoint("App-非静态方法")
    public void test1() {
        ...
    }
}
```

## 3.手动自定义添加
如果你想实现`零侵入`跳转、跳转时`传参`、`自定义`点击按钮逻辑，可以用此方法。

在步骤`2.2`基础上，复写`onAddTestItems()`方法，在其内部调用`addItem`方法进行增加。

```kotlin
@TestEntryPointListFragment
class MyTestListFragment : TestListFragment() {

    override fun onAddTestItems() {
        addItem("Activity1-无参", Activity1::class.java)
        addItem("Fragment1-无参", Fragment1::class.java)

        addItem("Activity2-有参", Activity2::class.java, "name" to "张三", "age" to 20)
        addItem("Fragment2-有参", Fragment2::class.java, "name" to "张三", "age" to 20)

        addItem("自定义"){
            // 点击此按钮，执行此方法。 
        }
    }
}
```

> 说明：
> - 如果不想在**目标类**使用`TestEntryPoint`注解，则使用此方式添加跳转逻辑，并修改上面引入`test-point-annotation`的依赖方式为`debugImplementation`，此为`零侵入`。
> - 如果可以接受在**目标类**使用`TestEntryPoint`注解，则是上面默认的引入方式，上面引入`test-point-annotation`依赖则继续使用`implementation`，此为`微侵入`。

## 4.其它
### 4.1.指定Fragment详情页
如果你要跳转的`Fragment`需要支持`Hilt`，则可以使用此方式指定使用自定义的`TestFragmentDetailsActivity`。

```java
@TestEntryPointFragmentDetailsActivity
public class MyTestFragmentDetailsActivity extends TestFragmentDetailsActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
```
### 4.2.定制Test应用的名字
覆盖`test_list_label`的字符串

`strings.xml`内添加
```
<resources>
    <string name="test_list_label">Test App Name</string>
</resources>
```

或`build.gradle`内添加
```gradle
resValue "string", "test_list_label", "Test App Name"
```


# 四、注解说明

| 注解 | 功能说明 | 使用约束 | 数量支持 |
| :-- | :-- | :-- | :-- |
| `TestEntryPoint` | 标记测试进入点 | `Activity`、<br/>`Fragment`、<br/>`静态无参方法`、<br/>`TestListFragment子类非静态无参方法` | 无数个 |
| `TestEntryPointModules` | 标记要测试的模块名称 | 可放到任意类上 | App内唯一 |
| `TestEntryPointListFragment` | 标记自定义的<br/>`TestListFragment` | 继承`TestListFragment`的类 | 无数个 |
| `TestEntryPointFragmentDetailsActivity` | 标记自定义的<br/>`TestFragmentDetailsActivity` | 继承`TestFragmentDetailsActivity`的类 | App内唯一 |