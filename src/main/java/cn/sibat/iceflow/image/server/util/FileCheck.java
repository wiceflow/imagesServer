package cn.sibat.iceflow.image.server.util;

import cn.sibat.iceflow.image.server.controller.exception.RRException;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.util.Arrays;
import java.util.Objects;

/**
 * http://blog.csdn.net/yjw123456/article/details/70312887
 *
 * @author BF
 */
public enum FileCheck {

    /**
     * 检查是否为图片
     */
    CHECK;

    private MimetypesFileTypeMap imageFileTypeMap;
    private MimetypesFileTypeMap videoFileTypeMap;

    FileCheck() {
        imageFileTypeMap = new MimetypesFileTypeMap();
        videoFileTypeMap = new MimetypesFileTypeMap();
        /* 不添加下面的类型会造成误判
        详见：http://stackoverflow.com/questions/4855627/java-mimetypesfiletypemap-always-returning-application-octet-stream-on-android-e*/
        imageFileTypeMap.addMimeTypes("image png tif jpg jpeg bmp JPEG PNG JPG BMP TIF");
        videoFileTypeMap.addMimeTypes("video mp4");
    }

    /**
     * 检查是否为图片
     *
     * @param path [String] 文件
     * @return boolean
     */
    public void isImage(String path) {
        String mimeType = imageFileTypeMap.getContentType(path);
        String type = mimeType.split("/")[0];
        if (!"image".equals(type)) {
            throw new RRException("该文件不为图片");
        }
    }

    /**
     * 检查是否为视屏
     *
     * @param path [String] 文件
     * @return boolean
     */
    public void isVideo(String path) {

        String mimeType = videoFileTypeMap.getContentType(path);
        String type = mimeType.split("/")[0];
        if (!"video".equals(type)) {
            throw new RRException("只支持 mp4 格式视屏");
        }
    }

    public void checkFile(MultipartFile[] multipartFiles, Integer fileType) {
        if (Objects.equals(fileType, Constants.FILE_PATH)) {
            // do  nothing
        } else if (Objects.equals(fileType, Constants.IMAGE_PATH)) {
            Arrays.stream(multipartFiles)
                    .map(e -> checkNameNull(e.getOriginalFilename()))
                    .forEach(this::isImage);
        } else if (Objects.equals(fileType, Constants.VIDEO_PATH)) {
            Arrays.stream(multipartFiles)
                    .map(e -> checkNameNull(e.getOriginalFilename()))
                    .forEach(this::isVideo);
        }
    }


    private String checkNameNull(String fileName) {
        if (StringUtil.isEmpty(fileName)) {
            throw new RRException("名字居然为空？");
        }
        int dotPos = fileName.lastIndexOf(".");
        if (dotPos < 0){
            throw new RRException("文件名不符合规范");
        }
        return fileName;
    }


}