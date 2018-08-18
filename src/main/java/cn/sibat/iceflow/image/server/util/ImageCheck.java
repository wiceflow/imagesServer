package cn.sibat.iceflow.image.server.util;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;

/**
 * http://blog.csdn.net/yjw123456/article/details/70312887
 * @author BF
 */
public enum  ImageCheck {

    /**
     * 检查是否为图片
     */
    IS_IMAGE;

    private MimetypesFileTypeMap mimetypesFileTypeMap;

    ImageCheck(){
        mimetypesFileTypeMap = new MimetypesFileTypeMap();
        /* 不添加下面的类型会造成误判
        详见：http://stackoverflow.com/questions/4855627/java-mimetypesfiletypemap-always-returning-application-octet-stream-on-android-e*/
        mimetypesFileTypeMap.addMimeTypes("image png tif jpg jpeg bmp JPEG PNG JPG BMP TIF");
    }

    public boolean isImage(File file) {
        String mimeType = mimetypesFileTypeMap.getContentType(file);
        String type = mimeType.split("/")[0];
        return !"image".equals(type);
    }
}