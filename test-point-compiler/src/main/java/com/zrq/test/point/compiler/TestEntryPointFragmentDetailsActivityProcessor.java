package com.zrq.test.point.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.zrq.test.point.annotation.TestEntryPointFragmentDetailsActivity;

import java.io.IOException;
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
 * 描述：生成找到自定义【TestFragmentDetailsActivity】的Helper类
 *
 * @author zhangrq
 * createTime 2020/12/22 16:20
 */
@AutoService(Processor.class)
public class TestEntryPointFragmentDetailsActivityProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(TestEntryPointFragmentDetailsActivity.class);
        if (elements != null && elements.size() > 0) {
            // 有数据，判断是否符合规则
            Iterator<? extends Element> iterator = elements.iterator();
            TypeElement firstElement = (TypeElement) iterator.next();
            if (elements.size() == 1) {
                // 只有一个ok，判断是否是TestFragmentDetailsActivity子类
                if (isSubtypeTestFragmentDetailsActivity(firstElement)) {
                    // 是，生成Helper类
                    createClass(firstElement.getQualifiedName().toString());
                } else {
                    // 否，不支持，报错提示
                    printMessageError("只支持继承【TestFragmentDetailsActivity】的类", firstElement);
                }
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
     * @param customClassName 自定义类名
     */
    private void createClass(String customClassName) {
        // -编辑方法
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(Constants.CUSTOM_FRAGMENT_DETAILS_HELPER_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(String.class);
        methodBuilder.addStatement("return $S", customClassName);
        // -编辑类
        TypeSpec classSpec = TypeSpec.classBuilder(Constants.CUSTOM_FRAGMENT_DETAILS_HELPER_CLASS_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(methodBuilder.build())
                .build();
        // -编辑JavaFile
        JavaFile javaFile = JavaFile.builder(Constants.CUSTOM_FRAGMENT_DETAILS_HELPER_PACKAGE, classSpec)
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
        return Collections.singleton(TestEntryPointFragmentDetailsActivity.class.getCanonicalName());
    }

    // 是否是TestFragmentDetailsActivity子类型
    private boolean isSubtypeTestFragmentDetailsActivity(TypeElement element) {
        return processingEnv.getTypeUtils().isSubtype(element.asType(), processingEnv.getElementUtils().getTypeElement(Constants.TEST_FRAGMENT_DETAILS_ACTIVITY).asType());
    }

    private void printMessageError(String message, Element element) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "【" + TestEntryPointFragmentDetailsActivity.class.getSimpleName() + "】注解" + message, element);
    }
}
