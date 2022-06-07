package cn.com.twoke.kit.runnable;

/**
* 携带返回值的 runnable
* @author TwoKe
* @param <T> 返回值
*/
public interface RunnableWithReturn<T> {
    /**
     * 执行体
     * @return 返回值
     */
    T run();
}
