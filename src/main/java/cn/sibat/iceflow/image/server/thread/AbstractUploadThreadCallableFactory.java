package cn.sibat.iceflow.image.server.thread;

import cn.sibat.iceflow.image.server.controller.exception.RRException;
import cn.sibat.iceflow.image.server.vo.FileVO;

import java.io.File;
import java.util.concurrent.Callable;

/**
 * @author BF
 * @date 2020/11/2 15:24
 */
public abstract class AbstractUploadThreadCallableFactory implements Callable<FileVO> {


    /**
     * 加锁创建文件夹
     *
     * @param file [File] 文件目录
     */
    private synchronized void fileMkdirs(File file) {
        File parentFile = file.getParentFile();
        if (parentFile == null) {
            throw new RRException("文件路径不合法");
        } else if (parentFile.isDirectory()) {
            // do nothing
        } else {
            boolean mkdirs = parentFile.mkdirs();
            if (!mkdirs) {
                if (!(parentFile.exists() && parentFile.isDirectory())) {
                    throw new RRException("创建文件夹失败");
                }
            }
        }
    }
}
