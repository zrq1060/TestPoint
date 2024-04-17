package com.zrq.test.point.compiler.annotationprocessor.process

import com.zrq.test.point.annotation.TestEntryPointFragmentDetailsActivity
import com.zrq.test.point.compiler.annotationprocessor.ktx.createJavaClass
import com.zrq.test.point.compiler.common.createClassTestEntryPointFragmentDetailsActivityHelper
import com.zrq.test.point.compiler.annotationprocessor.ktx.isSubtypeTestFragmentDetailsActivity
import com.zrq.test.point.compiler.annotationprocessor.ktx.printMessageError
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

/**
 * 描述：生成找到自定义【TestFragmentDetailsActivity】的Helper类
 *
 * @author zhangrq
 * createTime 2020/12/22 16:20
 */
fun processTestEntryPointFragmentDetailsActivity(
    processingEnv: ProcessingEnvironment,
    roundEnv: RoundEnvironment,
) {
    val elements = roundEnv.getElementsAnnotatedWith(
        TestEntryPointFragmentDetailsActivity::class.java
    )
    if (elements != null && elements.isNotEmpty()) {
        // 有数据，判断是否符合规则
        val iterator: Iterator<Element> = elements.iterator()
        val firstElement = iterator.next() as TypeElement
        if (elements.size == 1) {
            // 只有一个ok，判断是否是TestFragmentDetailsActivity子类
            if (processingEnv.isSubtypeTestFragmentDetailsActivity(firstElement)) {
                // 是，生成Helper类
                createClass(processingEnv, firstElement.qualifiedName.toString())
            } else {
                // 否，不支持，报错提示
                processingEnv.printMessageError(
                    TestEntryPointFragmentDetailsActivity::class,
                    "只支持继承【TestFragmentDetailsActivity】的类",
                    firstElement
                )
            }
        } else {
            // 错误，提示，只支持有一个
            val secondElement = iterator.next() as TypeElement
            processingEnv.printMessageError(
                TestEntryPointFragmentDetailsActivity::class,
                "只支持有一个",
                secondElement
            )
        }
    }
}

/**
 * 创建类
 *
 * @param processingEnv
 * @param customClassName 自定义类名
 */
private fun createClass(processingEnv: ProcessingEnvironment, customClassName: String) {
    processingEnv.createJavaClass(createClassTestEntryPointFragmentDetailsActivityHelper(customClassName))
}