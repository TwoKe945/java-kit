package cn.com.twoke.kit.annotation.manager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 注解兼容管理器
 * @author TwoKe
 * @param <T> 注解兼容接口
 */
public interface AnnotationCompatibleManager<T> {
        /**
         * 注册
         * @param annotationType 注解类型
         */
        void register(Class<? extends Annotation> annotationType);

        /**
         * 注册
         * @param annotationType 注解类型
         * @param extensionField 兼容字段
         */
        void register(Class<? extends Annotation> annotationType, ExtensionField extensionField);

        /**
         * 获取方法上的注解接口
         * @param method
         * @return
         */
        T getAnnotationBy(Method method);

        /**
         * 获取构造函数上的注解接口
         * @param constructor
         * @return
         */
        T getAnnotationBy(Constructor<?> constructor);

        /**
         * 获取字段上的注解接口
         * @param field
         * @return
         */
        T getAnnotationBy(Field field);

        /**
         * 获取类上的注解接口
         * @param method
         * @return
         */
        T getAnnotationBy(Class<?> method);

        /**
         * 获取参数上的注解接口
         * @param method
         * @return
         */
        T getAnnotationBy(Parameter method);
}