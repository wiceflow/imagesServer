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
        String filePAth = "E:\\1.mp4";
        File file = new File(filePAth);
        if (file.exists()){
            System.out.println("yes");
        }
    }
}
