package me.zy.sports.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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

    public static String showTime(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);

        Date cTime = null;
        try {
            cTime = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (cTime == null) return "未知";

        long nowTimeLong = System.currentTimeMillis();
        long cTimeLong = cTime.getTime();
        long result = Math.abs(nowTimeLong - cTimeLong);

        if (result < 60000) {
            long seconds = result / 1000;
            if (seconds == 0) {
                return "刚刚";
            } else {
                return seconds + "秒前";
            }
        }
        if (result >= 60000 && result < 3600000) {
            long seconds = result / 60000;
            return seconds + "分钟前";
        }
        if (result >= 3600000 && result < 86400000) {
            long seconds = result / 3600000;
            return seconds + "小时前";
        }
        if (result >= 86400000 && result < 1702967296) {
            long seconds = result / 86400000;
            return seconds + "天前";
        }

        // 跨年
        sdf = new SimpleDateFormat("yyyy", Locale.SIMPLIFIED_CHINESE);
        long nowYearLong = 0;
        try {
            nowYearLong = sdf.parse(sdf.format(new Date())).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
        if (nowYearLong < cTimeLong){
            sdf = new SimpleDateFormat("MM-dd hh:mm", Locale.SIMPLIFIED_CHINESE);
        }
        return sdf.format(cTime);
    }


}
