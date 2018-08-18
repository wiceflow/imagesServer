package cn.sibat.iceflow.image.server.factory;

import org.junit.Test;

/**
 * @author iceflow
 * @date 2018/8/3
 *      线程工厂Test
 */
public class MyThreadFactoryTest {

    /**
     * 比较枚举创建的单利模式是否相同对象
     */
    @Test
    public void test1(){
        MyThreadFactory t1 = MyThreadFactory.MY_THREAD_FACTORY;
        MyThreadFactory t2 = MyThreadFactory.MY_THREAD_FACTORY;

        System.out.println(t1==t2);
    }
}
