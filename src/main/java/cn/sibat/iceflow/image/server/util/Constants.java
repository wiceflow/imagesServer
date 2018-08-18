package cn.sibat.iceflow.image.server.util;
import javax.ws.rs.core.MediaType;
/**
 * @author iceflow
 * @date 2018/8/1
 *      常量定义
 */
public class Constants {

    /**
     * 中文
     */
    public final static String CHARSET = ";charset=UTF-8";

    public final static String APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON + CHARSET;

    /**
     * 文件后缀前面的点
     */
    public final static String DOT_SEPARATOR = ".";

    /**
     * 线程池名称
     */
    public final static String IMAGE_SERVER_THREAD_POOL = "imageServerThreadPool";

}
