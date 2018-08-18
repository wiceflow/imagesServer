package cn.sibat.iceflow.image.server.factory;
import java.util.concurrent.*;


/**
 * 并发线程执行对象工厂方法
 * 单例
 *      这种方式是Effective Java作者Josh Bloch 提倡的方式，它不仅能避免多线程同步问题，而且还能防止反序列化重新创建新的对象
 *      https://blog.csdn.net/gavin_dyson/article/details/70832185  这篇博客有详解
 * @author iceflow
 * @date 2018/8/02
 */
public enum ExecutorFactory {

    /**
     * 获取单利线程池
     */
    THREAD_POOL;

    private ExecutorService executorService;

    ExecutorFactory(){
        final int maxPoolSize = 2000;
        // 线程创建工厂
        MyThreadFactory myThreadFactory = MyThreadFactory.MY_THREAD_FACTORY;
        // 定义并发执行服务
        executorService = new ThreadPoolExecutor(50,maxPoolSize,200, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(),myThreadFactory);
    }

    /**
     * 获取执行类
     * @return
     */
    public ExecutorService getExecutor(){
        return executorService;
    }

}
