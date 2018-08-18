package cn.sibat.iceflow.image.util;

import cn.sibat.iceflow.image.server.util.MD5Util;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author iceflow
 * @date 2018/8/8
 */
public class MD5UtilTest {


    /**
     * 测试生成文件的HASH码
     * @throws FileNotFoundException
     */
    @Test
    public void get32MD5Test() throws FileNotFoundException {

        String filePath = "C:\\Users\\BF\\Desktop\\dao.jpg";

        FileInputStream fileInputStream = new FileInputStream(filePath);

        String fileMD5 = MD5Util.MD5.md5HashCode32(fileInputStream);
        System.out.println(fileMD5);
    }

    @Test
    public void fileIsExist(){
        String filePath = "C:\\Users\\BF\\Desktop\\dao.jpg";
        File file = new File(filePath);
        System.out.println(file.exists());
    }

}
