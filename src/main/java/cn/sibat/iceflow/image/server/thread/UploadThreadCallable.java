package cn.sibat.iceflow.image.server.thread;

import cn.sibat.iceflow.image.server.controller.exception.RRException;
import cn.sibat.iceflow.image.server.util.Constants;
import cn.sibat.iceflow.image.server.util.ImageCheck;
import cn.sibat.iceflow.image.server.vo.ImageVO;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * @author iceflow
 * @date 2018/8/6
 *      图片上传线程Callable方法
 */
public class UploadThreadCallable implements Callable<ImageVO> {

    private Logger logger = LoggerFactory.getLogger(UploadThreadCallable.class);

    /**
     * 文件流
     */
    private MultipartFile imageFile;
    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 图片保存路径
     */
    private String imagePath;

    /**
     * 父级目录
     */
    private List<String> structure;

    /**
     * 构造方法
     * @param imageFile     [MultipartFile] 文件
     * @param projectName   [String] 项目名
     * @param structure     [List]   文件夹目录  可为null
     */
    public UploadThreadCallable(MultipartFile imageFile, String projectName, List<String> structure,String imagePath) {
        this.imageFile = imageFile;
        this.projectName = projectName;
        this.structure = structure;
        this.imagePath = imagePath;
    }

    @Override
    public final ImageVO call() throws Exception {
        // 构建分级目录
        StringBuffer otherPath = new StringBuffer();
        if (structure != null && structure.size() != 0){
            for (String s : structure){
                // File.separator 实际上是一个路径分隔符  兼容window和linux
                otherPath = otherPath.append(File.separator).append(s);
            }
        }
        // 后缀
        String suffix = imageFile.getOriginalFilename().split("\\.")[1];
        // 利用MD5生成Hash值做主键  对于重复文件则不再上传  -- TODO 下一个版本
        // String fileName = MD5Util.MD5.md5HashCode32(imageFile.getInputStream()) + Constants.DOT_SEPARATOR + suffix;
        // 利用UUID做主键
        String fileName = UUID.randomUUID() + Constants.DOT_SEPARATOR + suffix;
        // 文件路径
        String filePath = imagePath + projectName + otherPath.toString() + File.separator + fileName;
        // 构建文件
        File file = new File(filePath);
        logger.info(file.getAbsolutePath());
        // 创建文件目录
        fileMkdirs(file);
        // 先检查文件是否存在了，存在便不再上传 直接返回路径
        if (file.exists()) {
            logger.info("文件已存在，不再上传");
        }else if (ImageCheck.IS_IMAGE.isImage(file)){
            // 在写入文件时检查该文件是不是图片  最后不提示用户了  ↑
            logger.error("上传了不是图片的文件");
            return null;
        }else {
            try {
                // 0.common-io下的工具类:根据上传文件，实例化新的文件(file是字节文件，需转换) 自动关闭流
                FileUtils.copyInputStreamToFile(imageFile.getInputStream(), file);
            } catch (Exception e) {
                logger.error("文件存入失败");
                e.printStackTrace();
                throw new RRException("文件存入失败");
            }
        }
        // 封装数据返回
        ImageVO imageVO = new ImageVO();
        imageVO.setName(imageFile.getOriginalFilename());
        imageVO.setPath(projectName + otherPath.toString() + File.separator + fileName);
        return imageVO;
    }

    /**
     * 加锁创建文件夹
     * @param file [File] 文件目录
     */
    private synchronized void fileMkdirs(File file){
        File parentFile = file.getParentFile();
        if (parentFile == null){
            throw new RRException("文件路径不合法");
        }else if (parentFile.isDirectory()){
            // do nothing
        }else {
            boolean mkdirs = parentFile.mkdirs();
            if (!mkdirs){
                if (!(parentFile.exists() && parentFile.isDirectory())) {
                    throw new RRException("创建文件夹失败");
                }
            }
        }
    }
}
