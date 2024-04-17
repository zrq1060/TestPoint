package com.zrq.test.point.compiler.ksp.process

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.Modifier
import com.google.devtools.ksp.validate
import com.zrq.test.point.annotation.TestEntryPoint
import com.zrq.test.point.compiler.common.Constants
import com.zrq.test.point.compiler.common.TestEntryPointInfo
import com.zrq.test.point.compiler.common.createClassTestEntryPointHelper
import com.zrq.test.point.compiler.ksp.ktx.createJavaClass
import com.zrq.test.point.compiler.common.formatToPackageName
import com.zrq.test.point.compiler.ksp.ktx.isSubtypeActivity
import com.zrq.test.point.compiler.ksp.ktx.isSubtypeFragment
import com.zrq.test.point.compiler.ksp.ktx.isSubtypeSupportFragment

fun processTestEntryPoint(
    testModelName: String,
    logger: KSPLogger,
    codeGenerator: CodeGenerator,
    resolver: Resolver,
): List<KSAnnotated> {
    val sequenceKSAnnotated =
        resolver.getSymbolsWithAnnotation(TestEntryPoint::class.qualifiedName!!)
    if (sequenceKSAnnotated.count() > 0) {
        // 有数据
        createClass(testModelName, codeGenerator, sequenceKSAnnotated, logger)
    }
    return sequenceKSAnnotated.filter { !it.validate() }.toList()
}

@OptIn(KspExperimental::class)
private fun createClass(
    testModelName: String,
    codeGenerator: CodeGenerator,
    sequenceKSAnnotated: Sequence<KSAnnotated>,
    logger: KSPLogger,
) {
    val testEntryPointInfoList = mutableListOf<TestEntryPointInfo>()

    // 方法内容-Item
    for (ksAnnotated in sequenceKSAnnotated) {
        var type = 0
        var targetClassName: String? = null
        var targetMethodName: String? = null
        if (ksAnnotated is KSClassDeclaration) {
            // 类或者接口
            targetClassName = ksAnnotated.qualifiedName?.asString()
            if (isSubtypeActivity(ksAnnotated)) {
                // Activity
                type = 1
            } else if (isSubtypeFragment(ksAnnotated)) {
                // Fragment
                type = 2
            } else if (isSubtypeSupportFragment(ksAnnotated)) {
                // Support Fragment
                type = 3
            } else {
                // 其它类型，不支持，报错提示
                logger.printMessageError("标记在类上，只支持Activity、Fragment", ksAnnotated)
            }
        } else if (ksAnnotated is KSFunctionDeclaration) {
            // 方法
            ksAnnotated.parentDeclaration

            // Java类，OK
            //  方法参数：parameters
            //  类名用：parentDeclaration
            //  修饰符用：modifiers
            // Kotlin类，
            //  方法参数：parameters
            //  --问题：扩展方法，参数也是0。加extensionReceiver不为空判断。
            //  类名用：parentDeclaration，
            //  --问题：顶层方法，返回空。
            //  修饰符用：modifiers
            //  --问题：kotlin无修饰符。

            //            ksAnnotated.declarations
//            ksAnnotated.extensionReceiver
//            ksAnnotated.parent
            val parent = ksAnnotated.parent
            logger.info(
                "aaaaaaaaaaaaa=方法=，parameters=${ksAnnotated.parameters}，extensionReceiver==${ksAnnotated.extensionReceiver == null}，modifiers=${ksAnnotated.modifiers}，parent=${parent}，parentDeclaration=${ksAnnotated.parentDeclaration}",
                ksAnnotated
            )
            // -方法参数
//            if (ksAnnotated.isFunParametersEmpty()) {
//                // 方法参数为空
//                // -类名
//                val typeElement = ksAnnotated.parentDeclaration!! // 父元素
//                val ksClassDeclaration = typeElement as KSClassDeclaration
//                ksClassDeclaration.isCompanionObject
//                ksClassDeclaration.isCompanionObject
//
//                targetClassName = typeElement.qualifiedName?.asString()
//                typeElement.parent?.origin
//                logger.info(
//                    "aaaaaaaaaaaaa2222=方法=，parent=${parent}，parent.origin=${parent?.origin}，annotations=${ksAnnotated.annotations}，parentDeclaration=${ksAnnotated.parentDeclaration}",
//                    ksAnnotated
//                )
//                // -方法名
//                targetMethodName = ksAnnotated.simpleName.asString()
//                // 无参方法
//                if (ksAnnotated.isFunStatic()) {
//                    // 静态方法
//                    type = 4
//                } else if (isSubtypeTestListFragment(typeElement as KSClassDeclaration)) {
//                    // 非静态方法，并且是TestListActivity的子类
//                    type = 5
//                } else {
//                    // 其它类型，不支持，报错提示
//                    logger.printMessageError(
//                        "标记在【方法】上，只支持【静态无参方法】或者【继承TestListFragment类的，非静态无参方法】",
//                        ksAnnotated
//                    )
//                }
//            } else {
//                // 有参方法，不支持，报错提示
//                logger.printMessageError(
//                    "标记在【方法】上，只支持【静态无参方法】或者【继承TestListFragment类的，非静态无参方法】",
//                    ksAnnotated
//                )
//            }
        }

        if (type != 0) {
            // 要增加
            val name = ksAnnotated.getAnnotationsByType(TestEntryPoint::class).first().value
            testEntryPointInfoList.add(
                TestEntryPointInfo(
                    type, name, targetClassName ?: "", targetMethodName
                )
            )
        }
    }

    codeGenerator.createJavaClass(
        classFile = createClassTestEntryPointHelper(testModelName, testEntryPointInfoList),
        packageName = Constants.TEST_ENTRY_POINT_HELPER_PACKAGE_PREFIX + testModelName.formatToPackageName(),
        className = Constants.TEST_ENTRY_POINT_HELPER_CLASS_NAME,
    )
}

private fun KSFunctionDeclaration.isFunParametersEmpty() =
    // 参数不为空，并且不是Kotlin的扩展方法。
    parameters.isEmpty() && extensionReceiver == null

private fun KSFunctionDeclaration.isFunStatic() =
    // 修饰符里包含静态修饰符，兼容是Java类。
    modifiers.contains(Modifier.JAVA_STATIC)

private fun KSPLogger.printMessageError(message: String, symbol: KSAnnotated) {
    error("【" + TestEntryPoint::class.simpleName + "】注解" + message, symbol)
}