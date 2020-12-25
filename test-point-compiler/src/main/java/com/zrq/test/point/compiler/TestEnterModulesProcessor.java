package com.zrq.test.point.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.zrq.test.point.annotation.TestEntryPointModules;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * 描述：生成获取配置模块名列表的Helper类
 *
 * @author zhangrq
 * createTime 2020/12/7 16:20
 */
@AutoService(Processor.class)
public class TestEnterModulesProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(TestEntryPointModules.class);
        if (elements != null && elements.size() > 0) {
            // 有数据，判断是否符合规则
            Iterator<? extends Element> iterator = elements.iterator();
            TypeElement firstElement = (TypeElement) iterator.next();
            if (elements.size() == 1) {
                // 只有一个ok，创建类
                TestEntryPointModules annotation = firstElement.getAnnotation(TestEntryPointModules.class);
                createClass(annotation.value());
            } else {
                // 错误，提示，只支持有一个
                TypeElement secondElement = (TypeElement) iterator.next();
                printMessageError("只支持有一个", secondElement);
            }
        }
        return false;
    }

    /**
     * 创建类
     *
     * @param moduleNames 自定义类名
     */
    private void createClass(String[] moduleNames) {
        // -编辑方法
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(Constants.MODULES_NAME_HELPER_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ArrayList.class)
                .addStatement("ArrayList<$T> list = new ArrayList<>()", String.class);
        for (String moduleName : moduleNames) {
            methodBuilder.addStatement("list.add($S)", moduleName);
        }
        methodBuilder.addStatement("return list");
        // -编辑类
        TypeSpec classSpec = TypeSpec.classBuilder(Constants.MODULES_NAME_HELPER_CLASS_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(methodBuilder.build())
                .build();
        // -编辑JavaFile
        JavaFile javaFile = JavaFile.builder(Constants.MODULES_NAME_HELPER_PACKAGE, classSpec)
                .build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 此Processor支持的java版本
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    // 支持的注解类型
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(TestEntryPointModules.class.getCanonicalName());
    }

    private void printMessageError(String message, Element element) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "【" + TestEntryPointModules.class.getSimpleName() + "】注解" + message, element);
    }
}
