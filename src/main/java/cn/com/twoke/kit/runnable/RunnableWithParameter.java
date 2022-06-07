package cn.com.twoke.kit.runnable;

/**
 * 携带参数值的 runable
 * @author TwoKe
 * @param <T> 参数值
 */
public interface RunnableWithParameter<T> {
    /**
     * 执行体
     * @param t 参数
     */
    void run(T t);
}