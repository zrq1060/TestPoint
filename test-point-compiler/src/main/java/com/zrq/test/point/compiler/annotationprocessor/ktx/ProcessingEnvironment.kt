package com.zrq.test.point.compiler.annotationprocessor.ktx

import com.squareup.javapoet.JavaFile
import com.zrq.test.point.compiler.common.Constants
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic
import kotlin.reflect.KClass

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2024/4/15 上午10:57
 */
// 是否是Activity子类型
fun ProcessingEnvironment.isSubtypeActivity(element: TypeElement) =
    isSubtypeTargetClass(element, Constants.ACTIVITY)

// 是否是Fragment子类型
fun ProcessingEnvironment.isSubtypeFragment(element: TypeElement) =
    isSubtypeTargetClass(element, Constants.FRAGMENT)

// 是否是 Support Fragment子类型
fun ProcessingEnvironment.isSubtypeSupportFragment(element: TypeElement) =
    isSubtypeTargetClass(element, Constants.FRAGMENT_SUPPORT_ANDROIDX)
            || isSubtypeTargetClass(element, Constants.FRAGMENT_SUPPORT_V4)

// 是否是TestListFragment子类型
fun ProcessingEnvironment.isSubtypeTestListFragment(element: TypeElement) =
    isSubtypeTargetClass(element, Constants.TEST_LIST_FRAGMENT)

// 是否是TestFragmentDetailsActivity子类型
fun ProcessingEnvironment.isSubtypeTestFragmentDetailsActivity(element: TypeElement) =
    isSubtypeTargetClass(element, Constants.TEST_FRAGMENT_DETAILS_ACTIVITY)

private fun ProcessingEnvironment.isSubtypeTargetClass(
    element: TypeElement,
    targetClassName: String,
) = typeUtils.isSubtype(element.asType(), elementUtils.getTypeElement(targetClassName).asType())

fun ProcessingEnvironment.printMessageError(
    annotationKClass: KClass<*>,
    message: String,
    element: Element?,
) {
    messager.printMessage(
        Diagnostic.Kind.ERROR,
        "【" + annotationKClass.java.simpleName + "】注解" + message,
        element
    )
}
// ==================================ProcessingEnvironment==================================
fun ProcessingEnvironment.createJavaClass(classFile: JavaFile) {
    classFile.writeTo(filer)
}