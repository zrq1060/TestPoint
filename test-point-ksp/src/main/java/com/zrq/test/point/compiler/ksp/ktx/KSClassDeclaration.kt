package com.zrq.test.point.compiler.ksp.ktx

import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.javapoet.JavaFile
import com.zrq.test.point.compiler.common.Constants


// 是否是Activity子类型
fun isSubtypeActivity(ksClassDeclaration: KSClassDeclaration) =
    ksClassDeclaration.isSubtypeTargetClass(Constants.ACTIVITY)

// 是否是Fragment子类型
fun isSubtypeFragment(ksClassDeclaration: KSClassDeclaration) =
    ksClassDeclaration.isSubtypeTargetClass(Constants.FRAGMENT)

// 是否是 Support Fragment子类型
fun isSubtypeSupportFragment(ksClassDeclaration: KSClassDeclaration) =
    ksClassDeclaration.isSubtypeTargetClass(Constants.FRAGMENT_SUPPORT_ANDROIDX)
            || ksClassDeclaration.isSubtypeTargetClass(Constants.FRAGMENT_SUPPORT_V4)

// 是否是TestListFragment子类型
fun isSubtypeTestListFragment(ksClassDeclaration: KSClassDeclaration) =
    ksClassDeclaration.isSubtypeTargetClass(Constants.TEST_LIST_FRAGMENT)

// 是否是TestFragmentDetailsActivity子类型
fun isSubtypeTestFragmentDetailsActivity(ksClassDeclaration: KSClassDeclaration) =
    ksClassDeclaration.isSubtypeTargetClass(Constants.TEST_FRAGMENT_DETAILS_ACTIVITY)

private fun KSClassDeclaration.isSubtypeTargetClass(targetClassName: String): Boolean {
    return getAllSuperTypes().any {
        it.declaration.qualifiedName?.asString() == targetClassName
    }
}

// ==================================CodeGenerator==================================
fun CodeGenerator.createJavaClass(classFile: JavaFile, packageName: String, className: String) {
    createNewFile(Dependencies(true), packageName, className, "java")
        .writer()
        .use { classFile.writeTo(it) }
}