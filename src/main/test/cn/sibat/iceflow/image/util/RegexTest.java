package cn.sibat.iceflow.image.util;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author iceflow
 * @date 2018/8/8
 */
public class RegexTest  {

    @Test
    public void regexTest(){

        Pattern pattern = Pattern.compile("images/(\\S+)?width=(\\d+)&height=(\\d+)");
        Matcher matcher = pattern.matcher("http://www.wiceflow.com/images/iceflow/21.jpg?width=100&height=100");
        matcher.find();
        System.out.println(matcher.group());

        System.out.println("/21.jpg?w=100&h=200".matches("/(\\d+)\\.(jpg)\\?w=(\\d+)&h=(\\d+)"));
    }
}
