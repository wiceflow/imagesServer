package cn.sibat.iceflow.image.server.service;

import cn.sibat.iceflow.image.server.vo.ImageVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author iceflow
 * @date 2018/8/2
 */
public interface ImageUploadService {

    /**
     * 上传一组图片
     * @param imageFiles        [MultipartFile] 文件
     * @param projectName       [String] 项目名称
     * @param structure         [String...] 分级目录   可以为空
     * @return                  返回一个路径的List
     */
    List<ImageVO> uploadImages(MultipartFile[] imageFiles , String projectName,List<String> structure);


    /**
     * 上传一张图片
     * @param imageFile     [MultipartFile] 文件
     * @param projectName   [String] 项目名称
     * @param structure     [String...] 分级目录   可以为空
     * @return              [String] 返回图片相对路径
     */
    ImageVO uploadImageCallable(MultipartFile imageFile, String projectName, List<String> structure);

    /**
     * 上传一张图片
     * @param imageFile     [MultipartFile] 文件
     * @param projectName   [String] 项目名称
     * @param structure     [String...] 分级目录   可以为空
     * @return              [String] 返回图片相对路径
     */
    String uploadImageRunnable(MultipartFile imageFile,String projectName,List<String> structure );
}
