package me.zy.sports.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 项目名：sports
 * 包名：me.zy.sports.utils
 * Created by Administrator on 2019/5/5.
 * 描述：
 */
public class Utils {
    public static boolean isPhone(String inputText) {
        Pattern pat = Pattern.compile("^((14[0-9])|(13[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");
        Matcher mat = pat.matcher(inputText);
        return mat.matches();
    }
}
