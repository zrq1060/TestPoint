# TestPoint

> 一个用于帮写测试列表的库

## 功能介绍

* **使用注解添加标注，方便简单**
* **支持Activity、Fragment、方法**
* **支持测试多个模块、指定特定模块**
* **多模块支持未使用apk dex解析，性能优化**

## 基础功能

### 1.添加依赖和配置
#### java

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
    implementation 'com.zrq:test-point-api:1.0.0-alpha01'
    annotationProcessor 'com.zrq:test-point-compiler:1.0.0-alpha01'
    ...
}
```
#### kotlin

```
// 可以参考 module-kotlin 模块中的写法
apply plugin: 'kotlin-kapt'

kapt {
    arguments {
        arg("TEST_MODULE_NAME", project.getName())
    }
}

dependencies {
	implementation 'com.zrq:test-point-api:1.0.0-alpha01'
    kapt 'com.zrq:test-point-compiler:1.0.0-alpha01'
    ...
}
```
### 2.标记注解

```
@TestEntryPoint("登录")
public class LoginActivity extends AppCompatActivity {
	...
}
```
```
@TestEntryPoint("我的")
public class MineActivity extends Fragment {
	...
}
```
### 3.指定启动TestListActivity

```
<activity android:name="com.zrq.test.point.TestListActivity">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />

        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
```
### 4.初始化SDK，指定要测试模块

```
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TestEntryPointInit.init("app", "module-java", "module-kotlin");// 传入模块的名称
    }
}
```

## 进阶用法
### 1.指定静态方法

```
public class Test {

    @TestEntryPoint("App-静态方法")
    public static void test1() {
        Log.e("TestEntryPoint", "App-静态方法");
    }
}
```
### 2.指定非静态方法
**必须是`TestListActivity`子类里面的方法**

1.`TestListActivity`子类方法
```
public class MyTestListActivity extends TestListActivity {

    @TestEntryPoint("App-非静态方法")
    public void test1() {
        Toast.makeText(getApplicationContext(), "App-非静态方法", Toast.LENGTH_SHORT).show();
    }
}
```
2.指定启动`MyTestListActivity`
```
<activity android:name="com.zrq.test.point.demo.activity.MyTestListActivity">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />

        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
```
## Q&A
* 1.之前`build.gradle`已配置`arguments`（如ARouter），该怎么配置？
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
* 2.`Build Output`栏中文乱码
	解决办法见：https://blog.csdn.net/zhang5690800/article/details/104502632


## 沟通和交流

**QQ 交流群：**793823227

**QQ：**273902141