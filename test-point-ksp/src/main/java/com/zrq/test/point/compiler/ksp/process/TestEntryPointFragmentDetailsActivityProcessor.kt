package com.zrq.test.point.compiler.ksp.process


import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate
import com.zrq.test.point.annotation.TestEntryPointFragmentDetailsActivity
import com.zrq.test.point.compiler.common.Constants
import com.zrq.test.point.compiler.common.createClassTestEntryPointFragmentDetailsActivityHelper
import com.zrq.test.point.compiler.ksp.ktx.createJavaClass
import com.zrq.test.point.compiler.ksp.ktx.isSubtypeTestFragmentDetailsActivity

fun processTestEntryPointFragmentDetailsActivity(
    logger: KSPLogger,
    codeGenerator: CodeGenerator,
    resolver: Resolver,
): List<KSAnnotated> {
    val sequenceKSAnnotated =
        resolver.getSymbolsWithAnnotation(TestEntryPointFragmentDetailsActivity::class.qualifiedName!!)

    val count = sequenceKSAnnotated.count()
    if (count > 0) {
        // 有数据，判断是否符合规则
        val iterator = sequenceKSAnnotated.iterator()
        val firstElement = iterator.next()
        if (count == 1) {
            // 只有一个ok，判断是否是TestFragmentDetailsActivity子类
            if (isSubtypeTestFragmentDetailsActivity(firstElement as KSClassDeclaration)) {
                // 是，生成Helper类
                createClass(codeGenerator, firstElement.qualifiedName?.asString()!!)
            } else {
                // 否，不支持，报错提示
                logger.printMessageError(
                    "只支持继承【TestFragmentDetailsActivity】的类",
                    firstElement
                )
            }
        } else {
            // 错误，提示，只支持有一个
            val secondElement = iterator.next()
            logger.printMessageError("只支持有一个", secondElement)
        }
    }
    return sequenceKSAnnotated.filter { !it.validate() }.toList()
}

private fun createClass(codeGenerator: CodeGenerator, customClassName: String) {
    codeGenerator.createJavaClass(
        classFile = createClassTestEntryPointFragmentDetailsActivityHelper(customClassName),
        packageName = Constants.TEST_ENTRY_POINT_FRAGMENT_DETAILS_HELPER_PACKAGE,
        className = Constants.TEST_ENTRY_POINT_FRAGMENT_DETAILS_HELPER_CLASS_NAME,
    )
}

private fun KSPLogger.printMessageError(message: String, symbol: KSAnnotated) {
    error("【" + TestEntryPointFragmentDetailsActivity::class.simpleName + "】注解" + message, symbol)
}