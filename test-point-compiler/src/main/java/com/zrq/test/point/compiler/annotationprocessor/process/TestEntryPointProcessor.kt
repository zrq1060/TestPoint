package com.zrq.test.point.compiler.annotationprocessor.process

import com.zrq.test.point.annotation.TestEntryPoint
import com.zrq.test.point.compiler.common.TestEntryPointInfo
import com.zrq.test.point.compiler.annotationprocessor.ktx.createJavaClass
import com.zrq.test.point.compiler.common.createClassTestEntryPointHelper
import com.zrq.test.point.compiler.annotationprocessor.ktx.isSubtypeActivity
import com.zrq.test.point.compiler.annotationprocessor.ktx.isSubtypeFragment
import com.zrq.test.point.compiler.annotationprocessor.ktx.isSubtypeSupportFragment
import com.zrq.test.point.compiler.annotationprocessor.ktx.isSubtypeTestListFragment
import com.zrq.test.point.compiler.annotationprocessor.ktx.printMessageError
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement

/**
 * 描述：生成获取带有【TestEntryPoint】标注信息列表的Helper类
 *
 * @author zhangrq
 * createTime 2020/12/7 16:20
 */
fun processTestEntryPoint(
    processingEnv: ProcessingEnvironment,
    testModelName: String,
    roundEnv: RoundEnvironment,
) {
    val elements = roundEnv.getElementsAnnotatedWith(TestEntryPoint::class.java)
    if (elements != null && elements.isNotEmpty()) {
        // 有数据，创建类
        createClass(processingEnv, testModelName, elements)
    }
}

private fun createClass(
    processingEnv: ProcessingEnvironment,
    testModelName: String,
    elements: Set<Element>,
) {
    val testEntryPointInfoList = mutableListOf<TestEntryPointInfo>()
    for (element in elements) {
        var type = 0
        var className: String? = null
        var methodName: String? = null
        if (element.kind == ElementKind.CLASS) {
            // 类或者接口
            val typeElement = element as TypeElement
            className = typeElement.qualifiedName.toString()
            if (processingEnv.isSubtypeActivity(typeElement)) {
                // Activity
                type = 1
            } else if (processingEnv.isSubtypeFragment(typeElement)) {
                // Fragment
                type = 2
            } else if (processingEnv.isSubtypeSupportFragment(typeElement)) {
                // Support Fragment
                type = 3
            } else {
                // 其它类型，不支持，报错提示
                processingEnv.printMessageError(
                    TestEntryPoint::class,
                    "标记在类上，只支持Activity、Fragment",
                    element
                )
            }
        } else if (element.kind == ElementKind.METHOD) {
            // 方法
            // -类名
            val typeElement = element.enclosingElement as TypeElement // 父元素
            className = typeElement.qualifiedName.toString()
            // -方法名
            val executableElement = element as ExecutableElement
            methodName = executableElement.simpleName.toString()
            // -方法参数
            val parameters = executableElement.parameters
            if (parameters == null || parameters.isEmpty()) {
                // 无参方法
                val modifierSet = element.getModifiers()
                if (modifierSet.contains(Modifier.STATIC)) {
                    // 静态方法
                    type = 4
                } else if (processingEnv.isSubtypeTestListFragment(typeElement)) {
                    // 非静态方法，并且是TestListActivity的子类
                    type = 5
                } else {
                    // 其它类型，不支持，报错提示
                    processingEnv.printMessageError(
                        TestEntryPoint::class,
                        "标记在【方法】上，只支持【静态无参方法】或者【继承TestListFragment类的，非静态无参方法】",
                        element
                    )
                }
            } else {
                // 有参方法，不支持，报错提示
                processingEnv.printMessageError(
                    TestEntryPoint::class,
                    "标记在【方法】上，只支持【静态无参方法】或者【继承TestListFragment类的，非静态无参方法】",
                    element
                )
            }
        }
        if (type != 0) {
            // 要增加
            val annotation = element.getAnnotation(TestEntryPoint::class.java)
            val name = annotation.value
            testEntryPointInfoList.add(TestEntryPointInfo(type, name, className ?: "", methodName))
        }
    }

    processingEnv.createJavaClass(createClassTestEntryPointHelper(testModelName, testEntryPointInfoList))
}