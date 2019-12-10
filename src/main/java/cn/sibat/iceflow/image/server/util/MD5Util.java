package cn.sibat.iceflow.image.server.util;

import cn.sibat.iceflow.image.server.controller.exception.RRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author iceflow
 * @date 2018/8/8
 *      利用MD5生成文件的Hash码
 *      检验方法是在window的cmd窗口中输入
 *      certutil -hashfile 文件路径 MD5
 */
public enum MD5Util {

    /**
     * 获取MD5的值
     */
    MD5;

    /**
     * MD5 转换器
     */
    private MessageDigest messageDigest;

    private Logger logger = LoggerFactory.getLogger(MD5Util.class);

    MD5Util(){
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            logger.error("获取MD5转换器失败");
            throw new RRException("获取MD5转换器失败");
        }
    }

    /**
     * java计算文件32位md5值
     * @param inputStream 输入流
     * @return  [String] 返回文件的Hash值  发生错误时返回null
     */
    public String md5HashCode32(InputStream inputStream) {
        try {
            //分多次将一个文件读入，对于大型文件而言，比较推荐这种方式，占用内存比较少。
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = inputStream.read(buffer, 0, 1024)) != -1) {
                messageDigest.update(buffer, 0, length);
            }
            inputStream.close();
            //转换并返回包含16个元素字节数组,返回数值范围为-128到127
            byte[] md5Bytes  = messageDigest.digest();
            StringBuffer hexValue = new StringBuffer();
            for (byte md5Byte : md5Bytes) {
                //解释参见最下方
                int val = ((int) md5Byte) & 0xff;
                if (val < 16) {
                    /*
                      如果小于16，那么val值的16进制形式必然为一位，
                      因为十进制0,1...9,10,11,12,13,14,15 对应的 16进制为 0,1...9,a,b,c,d,e,f;
                      此处高位补0。
                     */
                    hexValue.append("0");
                }
                //这里借助了Integer类的方法实现16进制的转换
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            logger.error("文件流转换MD5失败");
            e.printStackTrace();
            return null;
        }
    }
}
