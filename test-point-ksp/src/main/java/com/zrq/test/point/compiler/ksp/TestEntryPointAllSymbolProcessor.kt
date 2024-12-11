package com.zrq.test.point.compiler.ksp

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.zrq.test.point.compiler.ksp.process.processTestEntryPoint
import com.zrq.test.point.compiler.ksp.process.processTestEntryPointFragmentDetailsActivity
import com.zrq.test.point.compiler.ksp.process.processTestEntryPointListFragment
import com.zrq.test.point.compiler.ksp.process.processTestEntryPointModules

/**
 * 描述：
 *
 * @author zhangrq
 * createTime 2024/4/12 上午11:23
 */
class TestEntryPointAllSymbolProcessor(
    private val testModelName: String,
    environment: SymbolProcessorEnvironment,
) : SymbolProcessor {
    private val logger = environment.logger
    private val codeGenerator = environment.codeGenerator
    override fun process(resolver: Resolver): List<KSAnnotated> {
        // TestEntryPoint
        val ret1 = processTestEntryPoint(testModelName, logger, codeGenerator, resolver)
        // TestEntryPointModules
        val ret2 = processTestEntryPointModules(testModelName, logger, codeGenerator, resolver)
        // TestEntryPointListFragment
        val ret3 = processTestEntryPointListFragment(testModelName, logger, codeGenerator, resolver)
        // TestEntryPointFragmentDetailsActivity
        val ret4 = processTestEntryPointFragmentDetailsActivity(logger, codeGenerator, resolver)

        val ret = mutableListOf<KSAnnotated>()
        ret.addAll(ret1)
        ret.addAll(ret2)
        ret.addAll(ret3)
        ret.addAll(ret4)
        return ret
    }
}