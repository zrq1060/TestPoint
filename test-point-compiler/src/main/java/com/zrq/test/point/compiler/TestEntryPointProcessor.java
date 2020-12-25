package com.zrq.test.point.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.zrq.test.point.annotation.TestEntryPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;

/**
 * 描述：生成获取带有【TestEntryPoint】标注信息列表的Helper类
 *
 * @author zhangrq
 * createTime 2020/12/7 16:20
 */
@AutoService(Processor.class)
public class TestEntryPointProcessor extends AbstractProcessor {
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
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(TestEntryPoint.class);
        if (elements != null && elements.size() > 0) {
            // 有数据，创建类
            // -编辑方法
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(Constants.POINT_MODULE_HELPER_METHOD_NAME)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(ArrayList.class)
                    .addStatement("ArrayList<" + Constants.TEST_ENTRY_POINT_INFO + "> list = new ArrayList<>()");
            for (Element element : elements) {
                int type = 0;
                String className = null;
                String methodName = null;
                if (element.getKind() == ElementKind.CLASS) {
                    // 类或者接口
                    TypeElement typeElement = (TypeElement) element;
                    className = typeElement.getQualifiedName().toString();
                    if (isSubtypeActivity(typeElement)) {
                        // Activity
                        type = 1;
                    } else if (isSubtypeFragment(typeElement)) {
                        // Fragment
                        type = 2;
                    } else if (isSubtypeSupportFragment(typeElement)) {
                        // Support Fragment
                        type = 3;
                    } else {
                        // 其它类型，不支持，报错提示
                        printMessageError("标记在类上，只支持Activity、Fragment", element);
                    }
                } else if (element.getKind() == ElementKind.METHOD) {
                    // 方法
                    // -类名
                    TypeElement typeElement = (TypeElement) element.getEnclosingElement();// 父元素
                    className = typeElement.getQualifiedName().toString();
                    // -方法名
                    ExecutableElement executableElement = (ExecutableElement) element;
                    methodName = executableElement.getSimpleName().toString();
                    // -方法参数
                    List<? extends VariableElement> parameters = executableElement.getParameters();
                    if (parameters == null || parameters.size() == 0) {
                        // 无参方法
                        Set<Modifier> modifierSet = element.getModifiers();
                        if (modifierSet.contains(Modifier.STATIC)) {
                            // 静态方法
                            type = 4;
                        } else if (isSubtypeTestListFragment(typeElement)) {
                            // 非静态方法，并且是TestListActivity的子类
                            type = 5;
                        } else {
                            // 其它类型，不支持，报错提示
                            printMessageError("标记在【方法】上，只支持【静态无参方法】或者【继承TestListFragment类的，非静态无参方法】", element);
                        }
                    } else {
                        // 有参方法，不支持，报错提示
                        printMessageError("标记在【方法】上，只支持【静态无参方法】或者【继承TestListFragment类的，非静态无参方法】", element);
                    }
                }
                if (type != 0) {
                    // 要增加
                    TestEntryPoint annotation = element.getAnnotation(TestEntryPoint.class);
                    String name = annotation.value();
                    methodBuilder.addStatement("list.add(new " + Constants.TEST_ENTRY_POINT_INFO + "($L,$S,$S,$S))", type, name, className, methodName);
                }
            }
            methodBuilder.addStatement("return list");
            // -编辑类
            TypeSpec classSpec = TypeSpec.classBuilder(Constants.POINT_MODULE_HELPER_CLASS_NAME)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(methodBuilder.build())
                    .build();
            // -编辑JavaFile
            String packageName = Constants.POINT_MODULE_HELPER_PACKAGE_PREFIX
                    + testModelName.replaceAll("[-_]", ".");// replaceAll解决名称含有[-][_]
            JavaFile javaFile = JavaFile.builder(packageName, classSpec)
                    .build();
            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // 此Processor支持的java版本
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    // 支持的注解类型
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(TestEntryPoint.class.getCanonicalName());
    }

    // 是否是Activity子类型
    private boolean isSubtypeActivity(TypeElement element) {
        return processingEnv.getTypeUtils().isSubtype(element.asType(), processingEnv.getElementUtils().getTypeElement(Constants.ACTIVITY).asType());
    }

    // 是否是Fragment子类型
    private boolean isSubtypeFragment(TypeElement element) {
        return processingEnv.getTypeUtils().isSubtype(element.asType(), processingEnv.getElementUtils().getTypeElement(Constants.FRAGMENT).asType());
    }

    // 是否是 Support Fragment子类型
    private boolean isSubtypeSupportFragment(TypeElement element) {
        return processingEnv.getTypeUtils().isSubtype(element.asType(), processingEnv.getElementUtils().getTypeElement(Constants.FRAGMENT_SUPPORT_ANDROIDX).asType()) ||
                processingEnv.getTypeUtils().isSubtype(element.asType(), processingEnv.getElementUtils().getTypeElement(Constants.FRAGMENT_SUPPORT_V4).asType());
    }

    // 是否是TestListFragment子类型
    private boolean isSubtypeTestListFragment(TypeElement element) {
        return processingEnv.getTypeUtils().isSubtype(element.asType(), processingEnv.getElementUtils().getTypeElement(Constants.TEST_LIST_FRAGMENT).asType());
    }

    private void printMessageError(String message, Element element) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "【" + TestEntryPoint.class.getSimpleName() + "】注解" + message, element);
    }
}
