package com.zrq.test.point.compiler.annotationprocessor

import com.google.auto.service.AutoService
import com.zrq.test.point.annotation.TestEntryPoint
import com.zrq.test.point.annotation.TestEntryPointFragmentDetailsActivity
import com.zrq.test.point.annotation.TestEntryPointListFragment
import com.zrq.test.point.annotation.TestEntryPointModules
import com.zrq.test.point.compiler.common.Constants
import com.zrq.test.point.compiler.annotationprocessor.process.processTestEntryPoint
import com.zrq.test.point.compiler.annotationprocessor.process.processTestEntryPointFragmentDetailsActivity
import com.zrq.test.point.compiler.annotationprocessor.process.processTestEntryPointListFragment
import com.zrq.test.point.compiler.annotationprocessor.process.processTestEntryPointModules
import com.zrq.test.point.compiler.annotationprocessor.ktx.printMessageError
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

/**
 * 描述：【TestEntryPoint】此项目的所有处理者。
 *
 * @author zhangrq
 * createTime 2020/12/7 16:20
 */
@AutoService(Processor::class)
class TestEntryPointAllProcessor : AbstractProcessor() {
    private var testModelName: String? = null

    @Synchronized
    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        testModelName = processingEnv.options[Constants.TEST_MODULE_NAME]
        if (testModelName.isNullOrEmpty()) {
            // 未设置TEST_MODEL_NAME，报错提醒
            processingEnv.printMessageError(
                TestEntryPoint::class,
                "此 module【2222build.gradle】未设置【" + Constants.TEST_MODULE_NAME + "】参数",
                null
            )
        }
    }

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
        // TestEntryPoint
        processTestEntryPoint(processingEnv, testModelName!!, roundEnv)
        // TestEntryPointModules
        processTestEntryPointModules(processingEnv, testModelName!!, roundEnv)
        // TestEntryPointListFragment
        processTestEntryPointListFragment(processingEnv, testModelName!!, roundEnv)
        // TestEntryPointFragmentDetailsActivity
        processTestEntryPointFragmentDetailsActivity(processingEnv, roundEnv)
        return false
    }

    // 此Processor支持的java版本
    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    // 支持的注解类型
    override fun getSupportedAnnotationTypes(): Set<String> {
        val set = HashSet<String>()
        set.add(TestEntryPoint::class.java.canonicalName)
        set.add(TestEntryPointModules::class.java.canonicalName)
        set.add(TestEntryPointListFragment::class.java.canonicalName)
        set.add(TestEntryPointFragmentDetailsActivity::class.java.canonicalName)
        return set
    }
}