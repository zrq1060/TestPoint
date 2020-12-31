package com.zrq.test.point.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.zrq.test.point.annotation.TestEntryPointListFragment;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * 描述：生成找到自定义【TestListFragment】的Helper类
 *
 * @author zhangrq
 * createTime 2020/12/7 16:20
 */
@AutoService(Processor.class)
public class TestEntryPointListFragmentProcessor extends AbstractProcessor {
    private String testModelName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        testModelName = processingEnv.getOptions().get(Constants.TEST_MODULE_NAME);
        if (testModelName == null || testModelName.length() == 0) {
            // 未设置TEST_MODEL_NAME，报错提醒
            printMessageError("此 module【build.gradle】未设置【" + Constants.TEST_MODULE_NAME + "】参数", null);
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(TestEntryPointListFragment.class);
        if (elements != null && elements.size() > 0) {
            // 有数据，判断是否符合规则
            Iterator<? extends Element> iterator = elements.iterator();
            TypeElement firstElement = (TypeElement) iterator.next();
            if (elements.size() == 1) {
                // 只有一个ok，判断是否是TestListFragment子类
                if (isSubtypeTestListFragment(firstElement)) {
                    // 是，生成Helper类
                    createClass(firstElement.getQualifiedName().toString());
                } else {
                    // 否，不支持，报错提示
                    printMessageError("只支持继承【TestListFragment】的类", firstElement);
                }
            } else {
                // 错误，提示，只支持有一个
                TypeElement secondElement = (TypeElement) iterator.next();
                printMessageError("只支持一个module下只能有一个", secondElement);
            }
        }
        return false;
    }

    /**
     * 创建类
     *
     * @param customTestListFragmentClassName 自定义类名
     */
    private void createClass(String customTestListFragmentClassName) {
        // -编辑方法
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(Constants.CUSTOM_LIST_FRAGMENT_HELPER_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(String.class);
        methodBuilder.addStatement("return $S", customTestListFragmentClassName);
        // -编辑类
        TypeSpec classSpec = TypeSpec.classBuilder(Constants.CUSTOM_LIST_FRAGMENT_HELPER_CLASS_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(methodBuilder.build())
                .build();
        // -编辑JavaFile
        String packageName = Constants.CUSTOM_LIST_FRAGMENT_HELPER_PACKAGE_PREFIX
                + testModelName.replaceAll("[-_]", ".");// replaceAll解决名称含有[-][_]
        JavaFile javaFile = JavaFile.builder(packageName, classSpec)
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
        return Collections.singleton(TestEntryPointListFragment.class.getCanonicalName());
    }

    // 是否是TestListFragment子类型
    private boolean isSubtypeTestListFragment(TypeElement element) {
        return processingEnv.getTypeUtils().isSubtype(element.asType(), processingEnv.getElementUtils().getTypeElement(Constants.TEST_LIST_FRAGMENT).asType());
    }

    private void printMessageError(String message, Element element) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "【" + TestEntryPointListFragment.class.getSimpleName() + "】注解" + message, element);
    }
}
