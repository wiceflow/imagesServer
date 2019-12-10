package cn.sibat.iceflow.image.server.service;

import cn.sibat.iceflow.image.server.vo.FileVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author iceflow
 * @date 2018/8/2
 */
public interface FileUploadService {

    /**
     * 上传一组图片
     *
     * @param imageFiles  [MultipartFile] 文件
     * @param projectName [String] 项目名称
     * @param structure   [String...] 分级目录   可以为空
     * @param path        [String] 路径
     * @return 返回一个路径的List
     */
    List<FileVO> uploadImages(MultipartFile[] imageFiles, String projectName, List<String> structure, String path);


    /**
     * 上传一张图片
     *
     * @param imageFile   [MultipartFile] 文件
     * @param projectName [String] 项目名称
     * @param structure   [String...] 分级目录   可以为空
     * @param path        [String] 路径
     * @return [String] 返回图片相对路径
     */
    FileVO uploadImageCallable(MultipartFile imageFile, String projectName, List<String> structure, String path);

    /**
     * 上传一张图片
     *
     * @param imageFile   [MultipartFile] 文件
     * @param projectName [String] 项目名称
     * @param structure   [String...] 分级目录   可以为空
     * @param path        [String] 路径
     * @return [String] 返回图片相对路径
     */
    String uploadImageRunnable(MultipartFile imageFile, String projectName, List<String> structure, String path);
}
