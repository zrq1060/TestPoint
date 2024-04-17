package com.zrq.test.point.compiler.ksp

import com.google.auto.service.AutoService
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.zrq.test.point.compiler.common.Constants

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2024/4/12 上午11:23
 */
@AutoService(SymbolProcessorProvider::class)
class TestEntryPointAllSymbolProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        val logger = environment.logger
        val testModelName = environment.options[Constants.TEST_MODULE_NAME]
        if (testModelName.isNullOrEmpty()) {
            // 未设置TEST_MODEL_NAME，报错提醒
            logger.error(
                "此 module【11build.gradle】未设置【" + Constants.TEST_MODULE_NAME + "】参数",
                null
            )
        }
        return TestEntryPointAllSymbolProcessor(testModelName ?: "", environment)
    }

}