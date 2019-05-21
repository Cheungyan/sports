package me.zy.sports.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 项目名：sports
 * 包名：me.zy.sports.utils
 * Created by Administrator on 2019/5/10.
 * 描述：
 */
public class DecimalUtils {

    /**
     * 按四舍五入保留指定小数位数，位数不够用0补充
     *
     * @param o        格式化前的小数
     * @param newScale 保留小数位数
     * @return 格式化后的小数
     */
    public static String formatDecimalWithZero(Object o, int newScale) {
        return String.format("%." + newScale + "f", o);
    }

    /**
     * 按四舍五入保留指定小数位数，位数不够用0补充
     *
     * @param d        格式化前的小数
     * @param newScale 保留小数位数
     * @return 格式化后的小数
     */
    public static String formatDecimalWithZero(double d, int newScale) {
        String pattern = "0.";
        for (int i = 0; i < newScale; i++) {
            pattern += "0";
        }
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(d);
    }

    /**
     * 按四舍五入保留指定小数位数，位数不够用0补充
     *
     * @param f        格式化前的小数
     * @param newScale 保留小数位数
     * @return 格式化后的小数
     */
    public static String formatDecimalWithZero(float f, int newScale) {
        BigDecimal b = new BigDecimal(Float.toString(f));
        double d = b.doubleValue();
        String pattern = "0.";
        for (int i = 0; i < newScale; i++) {
            pattern += "0";
        }
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(d);
    }

    /**
     * 按四舍五入保留指定小数位数，位数不够用0补充
     *
     * @param f 格式化前的小数
     * @return 格式化后的小数
     */
    public static String formatDecimalWithZero2(float f) {
        BigDecimal b = new BigDecimal(Float.toString(f));
        double d = b.doubleValue();
        String pattern = "0.";
        for (int i = 0; i < 2; i++) {
            pattern += "0";
        }
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(d);
    }

    /**
     * 按四舍五入保留指定小数位数，位数不够用0补充
     *
     * @param f 格式化前的小数
     * @return 格式化后的小数
     */
    public static float formatDecimalWithZeroFloat2(float f) {
        BigDecimal b = new BigDecimal(Float.toString(f));
        double d = b.doubleValue();
        String pattern = "0.";
        for (int i = 0; i < 2; i++) {
            pattern += "0";
        }
        DecimalFormat df = new DecimalFormat(pattern);
        return Float.parseFloat(df.format(d));
    }

    /**
     * 按四舍五入保留指定小数位数，位数不够用0补充
     *
     * @param f        格式化前的小数
     * @param newScale 保留小数位数
     * @return 格式化后的小数
     */
    public static float formatDecimalWithZeroFloat(float f, int newScale) {
        BigDecimal b = new BigDecimal(Float.toString(f));
        double d = b.doubleValue();
        String pattern = "0.";
        for (int i = 0; i < newScale; i++) {
            pattern += "0";
        }
        DecimalFormat df = new DecimalFormat(pattern);
        return Float.parseFloat(df.format(d));
    }

    /**
     * 按四舍五入保留指定小数位数，位数不够用0补充
     *
     * @param d        格式化前的小数 String形式
     * @param newScale 保留小数位数
     * @return 格式化后的小数
     */
    public static String formatDecimalWithZero(String d, int newScale) {
        String pattern = "0.";
        for (int i = 0; i < newScale; i++) {
            pattern += "0";
        }
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(Double.valueOf(d));
    }

    /**
     * 按四舍五入保留指定小数位数，小数点后仅保留有效位数
     *
     * @param d        格式化前的小数
     * @param newScale 保留小数位数
     * @return 格式化后的小数
     */
    public static String formatDecimal(double d, int newScale) {
        String pattern = "#.";
        for (int i = 0; i < newScale; i++) {
            pattern += "#";
        }
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(d);
    }

    /**
     * 按四舍五入保留指定小数位数，小数点后仅保留有效位数
     *
     * @param d        格式化前的小数
     * @param newScale 保留小数位数
     * @return 格式化后的小数
     */
    public static String formatDecimal(String d, int newScale) {
        String pattern = "#.";
        for (int i = 0; i < newScale; i++) {
            pattern += "#";
        }
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(Double.valueOf(d));
    }

    /**
     * 按指定舍入模式保留指定小数位数
     *
     * @param d            格式化前的小数
     * @param newScale     保留小数位数
     * @param roundingMode 舍入模式
     *                     (RoundingMode.UP始终进一/DOWN直接舍弃/
     *                     CEILING正进负舍/FLOOR正舍负进/
     *                     HALF_UP四舍五入/HALF_DOWN五舍六进/
     *                     HALF_EVEN银行家舍入法/UNNECESSARY抛出异常)
     * @return 格式化后的小数
     */
    public static double formatDecimal(double d, int newScale, RoundingMode roundingMode) {
        BigDecimal bd = new BigDecimal(d).setScale(newScale, roundingMode);
        return bd.doubleValue();
    }

    /**
     * 按指定舍入模式保留指定小数位数
     *
     * @param d            格式化前的小数
     * @param newScale     保留小数位数
     * @param roundingMode 舍入模式
     *                     (RoundingMode.UP始终进一/DOWN直接舍弃/
     *                     CEILING正进负舍/FLOOR正舍负进/
     *                     HALF_UP四舍五入/HALF_DOWN五舍六进/
     *                     HALF_EVEN银行家舍入法/UNNECESSARY抛出异常)
     * @return 格式化后的小数
     */
    public static double formatDecimal(String d, int newScale, RoundingMode roundingMode) {
        BigDecimal bd = new BigDecimal(Double.valueOf(d)).setScale(newScale, roundingMode);
        return bd.doubleValue();
    }

    final static int[] sizeTable = {9, 99, 999, 9999, 99999, 999999, 9999999,
            99999999, 999999999, Integer.MAX_VALUE};

    public static int sizeOfInt(int x) {
        for (int i = 0; ; i++) {
            if (x <= sizeTable[i]) {
                return i + 1;
            }
        }
    }

}
