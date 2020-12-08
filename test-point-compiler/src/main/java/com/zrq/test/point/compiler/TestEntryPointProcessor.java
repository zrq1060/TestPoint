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
 * 描述：
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
        testModelName = processingEnv.getOptions().get("TEST_MODULE_NAME");
        printMessageInfo("init=testModelName=" + testModelName);
        if (testModelName == null || testModelName.length() == 0) {
            // 未设置TEST_MODEL_NAME，报错提醒
            printMessageError("TestEntryPoint module未设置TEST_MODEL_NAME", null);
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        printMessageInfo("process=start");
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(TestEntryPoint.class);
        if (elements != null && elements.size() > 0) {
            // 有数据，创建类
            // -编辑方法
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(Constants.HELPER_METHOD_NAME)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(ArrayList.class)
                    .addStatement("ArrayList<" + Constants.TEST_ENTRY_POINT_INFO + "> list = new ArrayList<>()");
            printMessageInfo("process=for=start");
            for (Element element : elements) {
                printMessageInfo("process=for=item=" + element.getSimpleName().toString());
                int type = 0;
                String className = null;
                String methodName = null;
                if (element.getKind() == ElementKind.CLASS) {
                    // 类或者接口
                    TypeElement typeElement = (TypeElement) element;
                    className = typeElement.getQualifiedName().toString();
                    printMessageInfo("process=for=item=class=className=" + className);
                    if (isSubtypeActivity(typeElement)) {
                        // Activity
                        type = 1;
                        printMessageInfo("process=for=item=class=activity");
                    } else if (isSubtypeFragment(typeElement)) {
                        // Fragment
                        type = 2;
                        printMessageInfo("process=for=item=class=Fragment");
                    } else if (isSubtypeSupportFragment(typeElement)) {
                        // Support Fragment
                        type = 3;
                        printMessageInfo("process=for=item=class=Support Fragment");
                    } else {
                        // 其它类型，不支持，报错提示
                        printMessageError("TestEntryPoint 类注解只支持Activity、Fragment", element);
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
                    printMessageInfo("process=for=item=method=name=" + element.getSimpleName().toString());
                    if (parameters == null || parameters.size() == 0) {
                        // 无参方法
                        Set<Modifier> modifierSet = element.getModifiers();
                        if (modifierSet.contains(Modifier.STATIC)) {
                            // 静态方法
                            type = 4;
                        } else if (isSubtypeTestListActivity(typeElement)) {
                            // 非静态方法，并且是TestListActivity的子类
                            type = 5;
                        } else {
                            // 其它类型，不支持，报错提示
                            printMessageError("TestEntryPoint 方法注解只支持静态无参方法、非静态无参方法（必须是TestListActivity的子类）", element);
                        }
                        printMessageInfo("process=for=item=method=methodName=" + methodName);
                    } else {
                        // 有参方法，不支持，报错提示
                        printMessageError("TestEntryPoint 方法注解只支持无参方法", element);
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
            printMessageInfo("class=start");
            TypeSpec classSpec = TypeSpec.classBuilder(Constants.HELPER_CLASS_NAME)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(methodBuilder.build())
                    .build();
            // -编辑JavaFile
            printMessageInfo("File=start");
            String packageName = Constants.HELPER_PACKAGE_PREFIX + testModelName.replaceAll("[-_]", ".");// replaceAll解决名称含有[-][_]
            JavaFile javaFile = JavaFile.builder(packageName, classSpec)
                    .build();
            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
            printMessageInfo("File=end");
        }
        printMessageInfo("process=end");
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

    // 是否是TestListActivity子类型
    private boolean isSubtypeTestListActivity(TypeElement element) {
        return processingEnv.getTypeUtils().isSubtype(element.asType(), processingEnv.getElementUtils().getTypeElement(Constants.TEST_LIST_ACTIVITY).asType());
    }

    private void printMessageError(String message, Element element) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }

    private void printMessageInfo(String message) {
//        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "TestPoint::Compiler " + message);
    }
}
