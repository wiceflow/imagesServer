package cn.sibat.iceflow.image.server.util;

import cn.sibat.iceflow.image.server.factory.ExecutorFactory;
import cn.sibat.iceflow.image.server.vo.ImageVO;

import java.util.concurrent.*;

/**
 * 并发线程执行辅助类
 * @author BF
 * @date 2018/8/2
 */
public class ExecuteServiceUtil{

    /**
     * 保证了始终只有一个线程池操作
     */
    private static ExecutorService executorService = ExecutorFactory.THREAD_POOL.getExecutor();

    /**
     * 执行一个无返回值的线程任务
     * @param task
     */
    public static void execute(Runnable task){
        executorService.execute(task);
    }

    /**
     * 执行有返回值的线程任务
     *      返回项目路径
     * 超时100秒
     * @param task
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    public static ImageVO execute(Callable<ImageVO> task) throws InterruptedException, ExecutionException, TimeoutException {
        Future<ImageVO> result = executorService.submit(task);
        return result.get(100, TimeUnit.SECONDS);
    }
}
