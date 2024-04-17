package com.zrq.test.point.compiler.annotationprocessor.process

import com.zrq.test.point.annotation.TestEntryPointModules
import com.zrq.test.point.compiler.annotationprocessor.ktx.createJavaClass
import com.zrq.test.point.compiler.common.createClassTestEntryPointModulesHelper
import com.zrq.test.point.compiler.annotationprocessor.ktx.printMessageError
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

/**
 * 描述：生成获取配置模块名列表的Helper类
 *
 * @author zhangrq
 * createTime 2020/12/7 16:20
 */
fun processTestEntryPointModules(
    processingEnv: ProcessingEnvironment,
    testModelName: String,
    roundEnv: RoundEnvironment,
) {
    val elements = roundEnv.getElementsAnnotatedWith(TestEntryPointModules::class.java)
    if (elements != null && elements.isNotEmpty()) {
        // 有数据，判断是否符合规则
        val iterator: Iterator<Element> = elements.iterator()
        val firstElement = iterator.next() as TypeElement
        if (elements.size == 1) {
            // 只有一个ok，创建类
            val annotation = firstElement.getAnnotation(TestEntryPointModules::class.java)
            createClass(processingEnv, testModelName, annotation.value)
        } else {
            // 错误，提示，只支持有一个
            val secondElement = iterator.next() as TypeElement
            processingEnv.printMessageError(
                TestEntryPointModules::class,
                "只支持有一个",
                secondElement
            )
        }
    }
}

private fun createClass(
    processingEnv: ProcessingEnvironment,
    testModelName: String,
    moduleNames: Array<String>,
) {
    processingEnv.createJavaClass(createClassTestEntryPointModulesHelper(testModelName, moduleNames))
}