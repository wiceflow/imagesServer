package cn.sibat.iceflow.image.util;

import org.junit.Test;

import java.io.File;

/**
 * @author iceflow
 * @date 2018/8/10
 */
public class FileUtilTest {

    @Test
    public void filePathTest(){
        String filePath = "/home/ftpimages/www/images/";
        File file = new File(filePath);

        file.mkdirs();
        System.out.println(file.getAbsolutePath());
    }
}
