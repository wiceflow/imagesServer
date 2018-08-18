package cn.sibat.iceflow.image.server.thread;

import cn.sibat.iceflow.image.server.controller.exception.RRException;
import cn.sibat.iceflow.image.server.util.ImageCheck;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author iceflow
 * @date 2018/8/2
 *      图片上传线程Runnable方法
 */
public class UploadThreadRunnable implements Runnable {

    private Logger logger = LoggerFactory.getLogger(UploadThreadRunnable.class);

    /**
     * 文件流
     */
    private InputStream inputStream;
    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 父级目录
     */
    private File percentFile;

    /**
     * 构造方法
     * @param inputStream   [InputStream] 输入流
     * @param fileName      [String] 文件名  包含后缀
     * @param percentFile   [File] 父级目录 对应这是一个空的文件夹
     */
    public UploadThreadRunnable(InputStream inputStream, String fileName, File percentFile) {
        this.inputStream = inputStream;
        this.fileName = fileName;
        this.percentFile = percentFile;
    }

    @Override
    public void run() {
        // 打开新文件
        File file = new File(percentFile, fileName);
        // 在写入文件时检查该文件是不是图片  最后不提示用户了
        if (ImageCheck.IS_IMAGE.isImage(file)) {
            //throw new RRException("上传了不是图片的文件");
            logger.error("上传了不是图片的文件");
        } else {
            try {
                // 0.common-io下的工具类:根据上传文件，实例化新的文件(file是字节文件，需转换) 自动关闭流
                FileUtils.copyInputStreamToFile(inputStream, file);
            } catch (IOException e) {
                logger.error("文件存入失败");
                file.delete();
                e.printStackTrace();
                throw new RRException("文件存入失败");
            }
        }
    }
}
