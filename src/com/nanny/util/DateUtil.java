package com.nanny.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	 /**
	  * 得到当前的时间
	  * @return
	  */
    public static Date getDate() {
        Calendar canlendar = Calendar.getInstance();
        return canlendar.getTime();
    }

    /**
     * 提到指定的millis得到时间
     */
    public static Date getDate(long millis) {
        Calendar canlendar = Calendar.getInstance();
        canlendar.clear();
        canlendar.setTimeInMillis(millis);
        return canlendar.getTime();
    }

    public static long getMillis() {
        return Calendar.getInstance().getTimeInMillis();
    }

    /**
     * 得到指定日期的字符串(yyyy-MM-dd HH:mm:ss.SSS)
     */
    public static String getDateFormate(Date date, String formate) {
        try {
            SimpleDateFormat simpleDateFormate = new SimpleDateFormat(formate);
            return simpleDateFormate.format(date);
        } catch (Exception e) {
        }
        return "";
    }

    
    /** 
     * 得到YYYY-MM-DD HH:MM:SS.SSS 格式时间
     * @return
     */
    public static String get4yMdHmsS() {
        return DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
    }

    
    /**
     *  得到YYYY-MM-DD HH:MM:SS格式时间
     * @return 
     */
    public static String get4yMdHms() {
        return DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    
    /**
     *  得到YYYY-MM-DD HH:MM格式时间
     * @return
     */
    public static String get4yMdHm() {
        return DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm");
    }

    
    /**
     *  得到YYYY-MM-DD格式格式时间
     * @return
     */
    public static String get4yMd() {
        return DateUtil.getDateFormate(new Date(), "yyyy-MM-dd");
    }

    /**
     * 把指定字符(yyyy-MM-dd HH:mm:ss.SSS)串转成Date
     * @param sDate
     * @return
     */
    public static Date parse4yMdHmsS(String sDate) {
        return DateUtil.parseDate(sDate, "yyyy-MM-dd HH:mm:ss.SSS");
    }

    /**
     * 把指定字符(yyyy-MM-dd HH:mm:ss)串转成Date
     */
    public static Date parse4yMdHms(String sDate) {
        return DateUtil.parseDate(sDate, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 把指定字符(yyyy-MM-dd HH:mm)串转成Date
     */
    public static Date parse4yMdHm(String sDate) {
        return DateUtil.parseDate(sDate, "yyyy-MM-dd HH:mm");
    }

    /**
     * 把指定字符(yyyy-MM-dd)串转成Date
     */
    public static Date parse4yMd(String sDate) {
        return DateUtil.parseDate(sDate, "yyyy-MM-dd");
    }

    /**
     * 根据指定格式,把字符串转成日期
     */
    public static Date parseDate(String sDate, String formate) {
        SimpleDateFormat simpleDateFormate = new SimpleDateFormat(formate);
        try {
            return simpleDateFormate.parse(sDate);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 两个长整型的时间相差(时间的毫秒数),可以得到指定的毫秒数,秒数,分钟数,天数
     */
    public static double getDifTwoTime(Date minuendTime, Date subtrahendTime, String tdatestr) {
        if (minuendTime == null || subtrahendTime != null) {
            return DateUtil.getDifTwoTime(minuendTime.getTime(), subtrahendTime.getTime(), tdatestr);
        }
        return 0;
    }

    /**
     * 两个长整型的时间相差(时间的毫秒数),可以得到指定的毫秒数,秒数,分钟数,天数
     */
    public static double getDifTwoTime(long minuendTime, long subtrahendTime, String tdatestr) {
        if (tdatestr == null || tdatestr.equals("")) {
            tdatestr = "MS";
        }
        double temp = 1;
        /** 毫秒数 */
        if ("MS".equalsIgnoreCase(tdatestr)) {
            temp = 1;
        }
        /** 得到秒 */
        if ("S".equalsIgnoreCase(tdatestr)) {
            temp = 1000;
        }
        /** 得到分 */
        if ("M".equalsIgnoreCase(tdatestr)) {
            temp = 1000 * 60;
        }
        /** 得到小时 */
        if ("H".equalsIgnoreCase(tdatestr)) {
            temp = 1000 * 60 * 60;
        }
        /** 得到天 */
        if ("D".equalsIgnoreCase(tdatestr)) {
            temp = 1000 * 60 * 60 * 24;
        }
        return (minuendTime - subtrahendTime) / temp;
    }

    /**从日期中得到指定部分(YYYY/MM/DD/HH/SS/SSS)数字
     * 
     * @param part
     * @return
     */
    public static int getPartOfTime(String part) {
        Calendar canlendar = Calendar.getInstance();
        canlendar.clear();
        canlendar.setTime(new Date());
        if (part.equalsIgnoreCase("Y")) {//得到年
            return canlendar.get(Calendar.YEAR);
        }
        if (part.equalsIgnoreCase("M")) {//得到月
            return canlendar.get(Calendar.MONTH) + 1;
        }
        if (part.equalsIgnoreCase("D")) {//得到日
            return canlendar.get(Calendar.DAY_OF_MONTH);
        }
        if (part.equalsIgnoreCase("H")) {//得到时
            return canlendar.get(Calendar.HOUR_OF_DAY);
        }
        if (part.equalsIgnoreCase("M")) {//得到分
            return canlendar.get(Calendar.MINUTE);
        }
        if (part.equalsIgnoreCase("S")) {//得到秒
            return canlendar.get(Calendar.SECOND);
        }
        if (part.equalsIgnoreCase("MS")) {//得到毫秒
            return canlendar.get(Calendar.MILLISECOND);
        }
        return -1;
    }
}
