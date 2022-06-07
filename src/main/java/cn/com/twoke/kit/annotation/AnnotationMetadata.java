package cn.com.twoke.kit.annotation;


import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * 注解元数据
 * @author TwoKe
 */
public class AnnotationMetadata extends HashMap<String, Object> {

    private Class<? extends Annotation> annotationType;

    private  AnnotationMetadata(Annotation annotation) {
        // 获取注解类型
        annotationType = annotation.annotationType();
        Method[] methods = annotationType.getDeclaredMethods();
        for (Method method : methods) {
            try {
                put(method.getName(), method.invoke(annotation));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static AnnotationMetadata build(Annotation annotation) {
        return new AnnotationMetadata(annotation);
    }
}
    