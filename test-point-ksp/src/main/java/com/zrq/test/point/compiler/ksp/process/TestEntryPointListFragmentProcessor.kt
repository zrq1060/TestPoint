package com.zrq.test.point.compiler.ksp.process

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate
import com.zrq.test.point.annotation.TestEntryPointListFragment
import com.zrq.test.point.compiler.common.Constants
import com.zrq.test.point.compiler.common.createClassTestEntryPointListFragmentHelper
import com.zrq.test.point.compiler.common.formatToPackageName
import com.zrq.test.point.compiler.ksp.ktx.createJavaClass
import com.zrq.test.point.compiler.ksp.ktx.isSubtypeTestListFragment


fun processTestEntryPointListFragment(
    testModelName: String,
    logger: KSPLogger,
    codeGenerator: CodeGenerator,
    resolver: Resolver,
): List<KSAnnotated> {
    val sequenceKSAnnotated =
        resolver.getSymbolsWithAnnotation(TestEntryPointListFragment::class.qualifiedName!!)
    if (sequenceKSAnnotated.count() > 0) {
        // 有数据
        createClass(testModelName, codeGenerator, sequenceKSAnnotated, logger)
    }
    return sequenceKSAnnotated.filter { !it.validate() }.toList()
}

private fun createClass(
    testModelName: String,
    codeGenerator: CodeGenerator,
    sequenceKSAnnotated: Sequence<KSAnnotated>,
    logger: KSPLogger,
) {
    val classNames = mutableListOf<String>()
    for (ksAnnotated in sequenceKSAnnotated) {
        if (isSubtypeTestListFragment(ksAnnotated as KSClassDeclaration)) {
            // 是，生成Helper类
            classNames.add(ksAnnotated.qualifiedName?.asString() ?: "")
        } else {
            // 否，不支持，报错提示
            logger.printMessageError("只支持继承【TestListFragment】的类", ksAnnotated)
        }
    }

    codeGenerator.createJavaClass(
        classFile = createClassTestEntryPointListFragmentHelper(testModelName, classNames),
        packageName = Constants.TEST_ENTRY_POINT_LIST_FRAGMENT_HELPER_PACKAGE_PREFIX + testModelName.formatToPackageName(),
        className = Constants.TEST_ENTRY_POINT_LIST_FRAGMENT_HELPER_CLASS_NAME,
    )
}

private fun KSPLogger.printMessageError(message: String, symbol: KSAnnotated) {
    error("【" + TestEntryPointListFragment::class.simpleName + "】注解" + message, symbol)
}