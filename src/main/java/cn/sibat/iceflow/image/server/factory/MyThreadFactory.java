package cn.sibat.iceflow.image.server.factory;

import cn.sibat.iceflow.image.server.util.Constants;

import java.util.concurrent.ThreadFactory;
/**
 * @author iceflow
 * @date 2018/8/02
 * 利用枚举方法创建线程安全的单利模式
 */
public enum MyThreadFactory implements ThreadFactory  {

    /**
     *  线程池名称
     */
    MY_THREAD_FACTORY(Constants.IMAGE_SERVER_THREAD_POOL);

    private int counter;
    private String prefix;

    MyThreadFactory(String prefix) {
        // 初始化线程数为0
        counter = 0;
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        counter ++ ;
        return new Thread(runnable,prefix + "-Thread-" + counter);
    }

}
