package com.zrq.test.point.compiler.common

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import java.lang.reflect.Type
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Modifier

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2024/4/15 下午2:34
 */

/**
 * 创建TestEntryPointHelper类
 */
fun createClassTestEntryPointHelper(
    testModelName: String,
    testEntryPointInfoList: List<TestEntryPointInfo>,
): JavaFile {
    return createJavaClass(
        packageName = Constants.TEST_ENTRY_POINT_HELPER_PACKAGE_PREFIX + testModelName.formatToPackageName(),
        className = Constants.TEST_ENTRY_POINT_HELPER_CLASS_NAME,
        methodName = Constants.TEST_ENTRY_POINT_HELPER_METHOD_NAME,
        returnType = ArrayList::class.java
    ) {
        addStatement("ArrayList<" + Constants.TEST_ENTRY_POINT_INFO + "> list = new ArrayList<>()")
        for (testEntryPointInfo in testEntryPointInfoList) {
            addStatement(
                "list.add(new " + Constants.TEST_ENTRY_POINT_INFO + "(\$L,\$S,\$S,\$S))",
                testEntryPointInfo.type,
                testEntryPointInfo.name,
                testEntryPointInfo.className,
                testEntryPointInfo.methodName
            )
        }
        addStatement("return list")
    }
}

/**
 * 创建TestEntryPointModulesHelper类
 */
fun createClassTestEntryPointModulesHelper(
    testModelName: String,
    moduleNames: Array<String>,
): JavaFile {
    var moduleNames = moduleNames
    if (moduleNames.isEmpty()) {
        // TestEntryPointModules注解没传值，默认为当前模块的。
        moduleNames = arrayOf(testModelName.formatToPackageName())
    }
    return createJavaClass(
        packageName = Constants.TEST_ENTRY_POINT_MODULES_HELPER_PACKAGE,
        className = Constants.TEST_ENTRY_POINT_MODULES_HELPER_CLASS_NAME,
        methodName = Constants.TEST_ENTRY_POINT_MODULES_HELPER_METHOD_NAME,
        returnType = ArrayList::class.java,
    ) {
        addStatement("ArrayList<\$T> list = new ArrayList<>()", String::class.java)
        for (moduleName in moduleNames) {
            addStatement("list.add(\$S)", moduleName)
        }
        addStatement("return list")
    }
}

/**
 * 创建TestEntryPointListFragmentHelper类
 */
fun createClassTestEntryPointListFragmentHelper(
    testModelName: String,
    classNames: List<String>,
): JavaFile {
    return createJavaClass(
        packageName = Constants.TEST_ENTRY_POINT_LIST_FRAGMENT_HELPER_PACKAGE_PREFIX + testModelName.formatToPackageName(),
        className = Constants.TEST_ENTRY_POINT_LIST_FRAGMENT_HELPER_CLASS_NAME,
        methodName = Constants.TEST_ENTRY_POINT_LIST_FRAGMENT_HELPER_METHOD_NAME,
        returnType = ArrayList::class.java,
    ) {
        addStatement("ArrayList<\$T> list = new ArrayList<>()", String::class.java)
        for (className in classNames) {
            addStatement("list.add(\$S)", className)
        }
        addStatement("return list")
    }
}

/**
 * 创建TestEntryPointFragmentDetailsActivityHelper类
 */
fun createClassTestEntryPointFragmentDetailsActivityHelper(customClassName: String): JavaFile {
    return createJavaClass(
        packageName = Constants.TEST_ENTRY_POINT_FRAGMENT_DETAILS_HELPER_PACKAGE,
        className = Constants.TEST_ENTRY_POINT_FRAGMENT_DETAILS_HELPER_CLASS_NAME,
        methodName = Constants.TEST_ENTRY_POINT_FRAGMENT_DETAILS_HELPER_METHOD_NAME,
        returnType = String::class.java,
    ) {
        addStatement("return \$S", customClassName)
    }
}

private fun createJavaClass(
    packageName: String,
    className: String,
    methodName: String,
    returnType: Type,
    methodBlock: MethodSpec.Builder.() -> Unit,
): JavaFile {
    // 编辑方法
    val methodBuilder =
        MethodSpec.methodBuilder(methodName)
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .returns(returnType)
    methodBlock(methodBuilder)
    // 编辑类
    val classSpec =
        TypeSpec.classBuilder(className)
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .addMethod(methodBuilder.build())
            .build()
    // 编辑JavaFile
    return JavaFile.builder(packageName, classSpec)
        .build()
}



fun String.formatToPackageName() = replace("[-_]".toRegex(), ".")