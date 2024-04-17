package com.zrq.test.point.compiler.annotationprocessor.process

import com.zrq.test.point.annotation.TestEntryPointListFragment
import com.zrq.test.point.compiler.annotationprocessor.ktx.createJavaClass
import com.zrq.test.point.compiler.common.createClassTestEntryPointListFragmentHelper
import com.zrq.test.point.compiler.annotationprocessor.ktx.isSubtypeTestListFragment
import com.zrq.test.point.compiler.annotationprocessor.ktx.printMessageError
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

/**
 * 描述：生成找到自定义【TestListFragment】的Helper类
 *
 * @author zhangrq
 * createTime 2020/12/7 16:20
 */
fun processTestEntryPointListFragment(
    processingEnv: ProcessingEnvironment,
    testModelName: String,
    roundEnv: RoundEnvironment,
) {
    val elements = roundEnv.getElementsAnnotatedWith(
        TestEntryPointListFragment::class.java
    )
    if (elements != null && elements.isNotEmpty()) {
        // 有数据，创建类
        createClass(processingEnv, testModelName, elements)
    }
}

/**
 * 创建类
 */
private fun createClass(
    processingEnv: ProcessingEnvironment,
    testModelName: String,
    elements: Set<Element>,
) {
    val classNames = mutableListOf<String>()

    for (element in elements) {
        // 只有一个ok，判断是否是TestListFragment子类
        val typeElement = element as TypeElement
        if (processingEnv.isSubtypeTestListFragment(typeElement)) {
            // 是，生成Helper类
            classNames.add(typeElement.qualifiedName.toString())
        } else {
            // 否，不支持，报错提示
            processingEnv.printMessageError(
                TestEntryPointListFragment::class,
                "只支持继承【TestListFragment】的类",
                element
            )
        }
    }

    processingEnv.createJavaClass(
        createClassTestEntryPointListFragmentHelper(
            testModelName,
            classNames
        )
    )
}