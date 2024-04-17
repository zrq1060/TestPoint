package com.zrq.test.point.compiler.ksp.process

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.validate
import com.zrq.test.point.annotation.TestEntryPointModules
import com.zrq.test.point.compiler.common.Constants
import com.zrq.test.point.compiler.common.createClassTestEntryPointModulesHelper
import com.zrq.test.point.compiler.ksp.ktx.createJavaClass

@OptIn(KspExperimental::class)
fun processTestEntryPointModules(
    testModelName: String,
    logger: KSPLogger,
    codeGenerator: CodeGenerator,
    resolver: Resolver,
): List<KSAnnotated> {
    val sequenceKSAnnotated =
        resolver.getSymbolsWithAnnotation(TestEntryPointModules::class.qualifiedName!!)

    val count = sequenceKSAnnotated.count()
    if (count > 0) {
        // 有数据，判断是否符合规则
        val iterator = sequenceKSAnnotated.iterator()
        val firstElement = iterator.next()
        if (count == 1) {
            // 只有一个ok，创建类
            val annotationValue =
                firstElement.getAnnotationsByType(TestEntryPointModules::class).first().value
            createClass(testModelName, codeGenerator, annotationValue)
        } else {
            // 错误，提示，只支持有一个
            val secondElement = iterator.next()
            logger.printMessageError("只支持有一个", secondElement)
        }
    }
    return sequenceKSAnnotated.filter { !it.validate() }.toList()
}

private fun createClass(
    testModelName: String,
    codeGenerator: CodeGenerator,
    moduleNames: Array<String>,
) {
    codeGenerator.createJavaClass(
        classFile = createClassTestEntryPointModulesHelper(testModelName, moduleNames),
        packageName = Constants.TEST_ENTRY_POINT_MODULES_HELPER_PACKAGE,
        className = Constants.TEST_ENTRY_POINT_MODULES_HELPER_CLASS_NAME,
    )
}

private fun KSPLogger.printMessageError(message: String, symbol: KSAnnotated) {
    error("【" + TestEntryPointModules::class.simpleName + "】注解" + message, symbol)
}