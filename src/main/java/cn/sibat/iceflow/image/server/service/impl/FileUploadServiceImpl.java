package cn.sibat.iceflow.image.server.service.impl;

import cn.sibat.iceflow.image.server.controller.exception.RRException;
import cn.sibat.iceflow.image.server.service.FileUploadService;
import cn.sibat.iceflow.image.server.thread.UploadThreadCallable;
import cn.sibat.iceflow.image.server.thread.UploadThreadRunnable;
import cn.sibat.iceflow.image.server.util.Constants;
import cn.sibat.iceflow.image.server.util.ExecuteServiceUtil;
import cn.sibat.iceflow.image.server.vo.FileVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * cmd
 *
 * @author iceflow
 * @date 2018/8/2
 * 图片上传服务层
 */
@Service
public class FileUploadServiceImpl extends AbstractService implements FileUploadService {


    /**
     * 上传一组图片
     *
     * @param imageFiles  [MultipartFile] 文件
     * @param projectName [String] 项目名称
     * @param structure   [String...] 分级目录   可以为空
     * @param path        [String] 路径
     * @return 返回一个路径的List
     */
    @Override
    public final List<FileVO> uploadImages(MultipartFile[] imageFiles, String projectName, List<String> structure, String path) {
        List<FileVO> list = new ArrayList<>();
        for (MultipartFile imageFile : imageFiles) {
            // 在callable方法中，当出现不为图片的文件，则需要判断返回值是否为null
            FileVO imageVO = uploadImageCallable(imageFile, projectName, structure, path);
            if (imageVO != null) {
                list.add(imageVO);
            }
        }
        return list;
    }

    /**
     * 上传一张图片
     *
     * @param imageFile   [MultipartFile] 文件
     * @param projectName [String] 项目名称
     * @param structure   [String...] 分级目录   可以为空
     * @param path        [String] 路径
     * @return [String] 返回图片相对路径
     */
    @Override
    public final FileVO uploadImageCallable(MultipartFile imageFile, String projectName, List<String> structure, String path) {
        try {
            // 工具类执行多线程上传
            return ExecuteServiceUtil.execute(new UploadThreadCallable(imageFile, projectName, structure, path));
        } catch (Exception e) {
            logger.error("多线程上传文件失败");
            e.printStackTrace();
            throw new RRException("上传文件失败", e);
        }
    }

    /**
     * 上传一张图片
     *
     * @param imageFile   [MultipartFile] 文件
     * @param projectName [String] 项目名称
     * @param structure   [String...] 分级目录   可以为空
     * @param path        [String] 路径
     * @return [String] 返回图片相对路径
     */
    @Override
    public final String uploadImageRunnable(MultipartFile imageFile, String projectName, List<String> structure, String path) {
        // 构建分级目录
        StringBuffer otherPath = new StringBuffer();
        if (structure != null && structure.size() != 0) {
            for (String s : structure) {
                // File.separator 实际上是一个路径分隔符  兼容window和linux
                otherPath = otherPath.append(File.separator).append(s);
            }
        }
        // 存放图片的目录 当分级目录参数为空时，这里不会出现异常
        File filePath = new File(path + projectName + otherPath.toString());
        // 如果目录不存在，则创建   mkdirs()方法不是线程安全的，需要加锁 -- 这里没有放入线程中进行
        // 这一段代码先不删除
//        if (!filePath.exists()){
//            boolean isMkdirs = filePath.mkdirs();
//            if (!isMkdirs){
//                throw new RRException("文件创建目录失败");
//            }
//        }

        // 后缀
        String suffix = Objects.requireNonNull(imageFile.getOriginalFilename()).split("\\.")[1];
        // UUID 做主键
        String fileName = UUID.randomUUID() + Constants.DOT_SEPARATOR + suffix;
        try {
            ExecuteServiceUtil.execute(new UploadThreadRunnable(imageFile.getInputStream(), fileName, filePath));
        } catch (IOException e) {
            logger.error("获取文件流失败");
            e.printStackTrace();
        }
        return projectName + otherPath + File.separator + fileName;
    }
}
