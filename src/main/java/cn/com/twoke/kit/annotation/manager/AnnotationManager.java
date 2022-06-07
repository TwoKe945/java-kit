package cn.com.twoke.kit.annotation.manager;

import cn.com.twoke.kit.annotation.AnnotationMetadata;
import cn.com.twoke.kit.runnable.RunnableWithParameterAndReturn;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 注解管理器
 * @author TwoKe
 */
public class AnnotationManager<T> extends HashMap<Class<? extends Annotation>, ExtensionField> implements AnnotationCompatibleManager<T> {

        private Class<T> annotationInterface;

        private AnnotationManager(Class<T> annotationInterface) {
            this.annotationInterface = annotationInterface;
        }

        public static <D> AnnotationManager build(Class<D> annotationInterface) {
            return new AnnotationManager(annotationInterface);
        }

        @Override
        public void register(Class<? extends Annotation> annotationType) {
            put(annotationType, null);
        }

        @Override
        public void register(Class<? extends Annotation> annotationType, ExtensionField extensionField) {
            put(annotationType, extensionField);
        }

        @Override
        public T getAnnotationBy(Method method) {
            return getAnnotationInterfaceInstance(clazz -> method.getDeclaredAnnotation(clazz));
        }


        @Override
        public T getAnnotationBy(Constructor<?> constructor) {
            return getAnnotationInterfaceInstance(clazz -> constructor.getDeclaredAnnotation(clazz));
        }

        @Override
        public T getAnnotationBy(Field field) {
            return getAnnotationInterfaceInstance(clazz -> field.getDeclaredAnnotation(clazz));
        }

        @Override
        public T getAnnotationBy(Class<?> method) {
            return getAnnotationInterfaceInstance(clazz -> method.getDeclaredAnnotation(clazz));
        }

        @Override
        public T getAnnotationBy(Parameter parameter) {
            return getAnnotationInterfaceInstance(clazz -> parameter.getDeclaredAnnotation(clazz));
        }

        private T getAnnotationInterfaceInstance(RunnableWithParameterAndReturn<Class<? extends Annotation>, Annotation> runnableWithReturn) {
            AtomicReference<AnnotationMetadata> annotationMetadataAtomicReference = new AtomicReference<>();
            keySet().forEach(annotationClass -> {
                Annotation annotation = runnableWithReturn.run(annotationClass);
                if (Objects.nonNull(annotation)) {
                    AnnotationMetadata annotationMetadata = AnnotationMetadata.build(annotation);
                    ExtensionField extensionField = get(annotationClass);
                    if (Objects.nonNull(extensionField)) {
                        annotationMetadata = extensionField.run(annotationMetadata);
                    }
                    annotationMetadataAtomicReference.set(annotationMetadata);
                    return;
                }
            });
            AnnotationMetadata annotationMetadata = annotationMetadataAtomicReference.get();
            if (Objects.isNull(annotationMetadata)) {
                throw new IllegalStateException("没有使用注解接口");
            }
            return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{annotationInterface}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    String methodName = method.getName();
                    return annotationMetadata.get(methodName);
                }
            });
        }

    }
