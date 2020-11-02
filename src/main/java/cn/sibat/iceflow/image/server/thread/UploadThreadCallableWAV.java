package cn.sibat.iceflow.image.server.thread;

import cn.sibat.iceflow.image.server.controller.exception.RRException;
import cn.sibat.iceflow.image.server.util.Constants;
import cn.sibat.iceflow.image.server.util.DateUtil;
import cn.sibat.iceflow.image.server.vo.FileVO;
import it.sauronsoftware.jave.AudioUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * @author iceflow
 * @date 2018/8/6
 * 图片上传线程Callable方法
 * <p>
 * 因业务需求，这个不再是图片上传服务，而是文件上传  -- 2019-11-05  截止需要上传功能的有 图片、文件、视屏
 * 断点续传功能可参考  https://blog.csdn.net/rainbow702/article/details/84014365
 * <p>
 * 专门针对事故应急所使用的  只要上传 wav 文件的话会同步生成一个 mp3 文件
 */
public class UploadThreadCallableWAV implements Callable<FileVO> {

    private Logger logger = LoggerFactory.getLogger(UploadThreadCallableWAV.class);

    /**
     * 文件流
     */
    private MultipartFile imageFile;
    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 保存路径
     */
    private String imagePath;

    /**
     * 父级目录
     */
    private List<String> structure;

    /**
     * 构造方法
     *
     * @param imageFile   [MultipartFile] 文件
     * @param projectName [String] 项目名
     * @param structure   [List]   文件夹目录  可为null
     */
    public UploadThreadCallableWAV(MultipartFile imageFile, String projectName, List<String> structure, String imagePath) {
        this.imageFile = imageFile;
        this.projectName = projectName;
        this.structure = structure;
        this.imagePath = imagePath;
    }

    @Override
    public final FileVO call() throws Exception {
        // 构建分级目录
        StringBuffer otherPath = new StringBuffer();
        if (structure != null && structure.size() != 0) {
            for (String s : structure) {
                // File.separator 实际上是一个路径分隔符  兼容window和linux
                otherPath = otherPath.append(File.separator).append(s);
            }
        }
        if (imageFile.getOriginalFilename() == null) {
            throw new RRException("没有文件名不行啊");
        }
        // 后缀
        String[] suffixs = imageFile.getOriginalFilename().split("\\.");
        if (suffixs.length == 1) {
            throw new RRException("文件名不符合格式，请添加后缀或写入文件名");
        }
        String suffix = suffixs[suffixs.length - 1];

        // 日期文件夹   以每一个月为单位创建文件夹
        String datePath = DateUtil.parseDateToString(new Date(), DateUtil.PATIERN_YYYYMM);
        // 利用UUID做主键
        String fileNameFirst = UUID.randomUUID() + Constants.DOT_SEPARATOR;
        String fileName = fileNameFirst + suffix;
        String mp3FileName = fileNameFirst + "mp3";
        // 文件路径
        String filePath = imagePath + projectName + File.separator + datePath + otherPath.toString() + File.separator + fileName;
        String mp3FilePath = imagePath + projectName + File.separator + datePath + otherPath.toString() + File.separator + mp3FileName;
        // 构建文件
        File originFile = new File(filePath);
        File mp3File = new File(mp3FilePath);
        logger.info(originFile.getAbsolutePath());
        // 创建文件目录
        fileMkdirs(originFile);
        // 先检查文件是否存在了，存在便不再上传 直接返回路径
        if (originFile.exists()) {
            logger.info("文件已存在，不再上传");
        } else {
            try {
                // 0.common-io下的工具类:根据上传文件，实例化新的文件(file是字节文件，需转换) 自动关闭流
                FileUtils.copyInputStreamToFile(imageFile.getInputStream(), originFile);
                AudioUtils.convert(originFile, mp3File, "mp3");
            } catch (Exception e) {
                logger.error("文件存入失败");
                e.printStackTrace();
                throw new RRException("文件存入失败");
            }
        }
        // 封装数据返回
        FileVO fileVO = new FileVO();
        fileVO.setName(imageFile.getOriginalFilename());
        fileVO.setPath(projectName + File.separator + datePath + otherPath.toString() + File.separator + fileName);
        return fileVO;
    }

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
