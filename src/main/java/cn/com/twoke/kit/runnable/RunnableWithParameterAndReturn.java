package cn.com.twoke.kit.runnable;

/**
 * 携带参数和返回值的 runnable
 * @author TwoKe
 * @param <T> 参数值
 * @param <R> 返回值
 */
public interface RunnableWithParameterAndReturn<T, R> {
    /**
     *
     * @param t 参数
     * @return 返回值
     */
    R run(T t);
}