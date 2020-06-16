package com.chaolifang.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期转换
 */
public final class ToolDate {
    private ToolDate() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    private static String FILE_NAME = "MMddHHmmssSSS";
    private static String DEFAULT_PATTERN = "yyyy-MM-dd";
    private static String DIR_PATTERN = "yyyy/MM/dd/";
    private static String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static String TIMES_PATTERN = "HH:mm:ss";
    private static String NOCHAR_PATTERN = "yyyyMMddHHmmss";

    /**
     * 获取当前时间戳
     *
     * @return
     */
    public static String formatDefaultFileName() {
        return formatDateByFormat(new Date(), FILE_NAME);
    }

    /**
     * 日期转换为字符串
     *
     * @param date   日期
     * @param format 日期格式
     * @return 指定格式的日期字符串
     */
    public static String formatDateByFormat(Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 校验日期格式
     *
     * @param sourceDate
     * @param format
     * @return
     */
    public static boolean checkDate(String sourceDate, String format) {
        if (sourceDate == null) {
            return false;
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            dateFormat.setLenient(false);
            dateFormat.parse(sourceDate);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 转换为默认格式(yyyy-MM-dd)的日期字符串
     *
     * @param date
     * @return
     */
    public static String formatDefaultDate(Date date) {
        return formatDateByFormat(date, DEFAULT_PATTERN);
    }

    // 不通用 一般别用
    public static String formatDefaultDateNullable(Date date) {
        if(date == null){
            return null;
        }
        return "'" + formatDateByFormat(date, DEFAULT_PATTERN) + "'";
    }

    /**
     * 转换为目录格式(yyyy/MM/dd/)的日期字符串
     *
     * @param date
     * @return
     */
    public static String formatDirDate(Date date) {
        return formatDateByFormat(date, DIR_PATTERN);
    }

    /**
     * 转换为完整格式(yyyy-MM-dd HH:mm:ss)的日期字符串
     *
     * @param date
     * @return
     */
    public static String formatTimesTampDate(Date date) {
        return formatDateByFormat(date, TIMESTAMP_PATTERN);
    }

    public static String formatUtcTime(Date date){
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                result = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 转换为时分秒格式(HH:mm:ss)的日期字符串
     *
     * @param date
     * @return
     */
    public static String formatTimesDate(Date date) {
        return formatDateByFormat(date, TIMES_PATTERN);
    }

    /**
     * 转换为时分秒格式(HH:mm:ss)的日期字符串
     *
     * @param date
     * @return
     */
    public static String formatNoCharDate(Date date) {
        return formatDateByFormat(date, NOCHAR_PATTERN);
    }

    /**
     * 日期格式字符串转换为日期对象
     *
     * @param strDate 日期格式字符串
     * @param pattern 日期对象
     * @return
     */
    public static Date parseDate(String strDate, String pattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            Date nowDate = format.parse(strDate);
            return nowDate;
        } catch (Exception e) {
            return null;
        }
    }

    public static String transferDateFormat(String strDate, String fromFormat, String toFormat) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(fromFormat);
            Date nowDate = format.parse(strDate);
            return ToolDate.formatDateByFormat(nowDate, toFormat);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 字符串转换为默认格式(yyyy-MM-dd)日期对象
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static Date parseDefaultDate(String date) {
        return parseDate(date, DEFAULT_PATTERN);
    }

    /**
     * 字符串转换为完整格式(yyyy-MM-dd HH:mm:ss)日期对象
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static Date parseTimesTampDate(String date) {
        return parseDate(date, TIMESTAMP_PATTERN);
    }

    /**
     * 获得当前时间
     *
     * @return
     */
    public static Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    /**
     * sql Date 转 util Date
     *
     * @param date java.sql.Date日期
     * @return java.util.Date
     */
    public static Date parseUtilDate(java.sql.Date date) {
        return date;
    }

    /**
     * util Date 转 sql Date
     *
     * @param date java.sql.Date日期
     * @return
     */
    public static java.sql.Date parseSqlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * 获取年份
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取月份
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取星期
     *
     * @param date
     * @return
     */
    public static int getWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = dayOfWeek - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        return dayOfWeek;
    }

    /**
     * 获取当前周开始时间
     *
     * @return
     */
    public static Date getCurrentWeekStartTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        //设置星期一为一周开始的第一天
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        //获得当前的时间戳
        calendar.setTimeInMillis(date.getTime());
        //获得当前的年
        int weekYear = calendar.get(Calendar.YEAR);
        //获得当前日期属于今年的第几周
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);

        //获得指定年的第几周的开始日期
        calendar.setWeekDate(weekYear, weekOfYear, 2);
        //创建日期的时间该周的第一天
        long starttime = calendar.getTime().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = simpleDateFormat.format(starttime);//将时间戳格式化为指定格式
        System.out.println(dateStart);
        return parseTimesTampDate(dateStart + " 00:00:00");
    }

    /**
     * 根据日期获取星期
     *
     * @param datetime
     * @return
     */
    public static String dateToWeek(Date datetime) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(datetime);

        // 指示一个星期中的某天
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 获取日期(多少号)
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前时间(小时)
     *
     * @param date
     * @return
     */
    public static int getHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取当前时间(分)
     *
     * @param date
     * @return
     */
    public static int getMinute(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    /**
     * 获取当前时间(秒)
     *
     * @param date
     * @return
     */
    public static int getSecond(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.SECOND);
    }

    /**
     * 获取当前毫秒
     *
     * @param date
     * @return
     */
    public static long getMillis(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    /**
     * 日期增加
     *
     * @param date Date
     * @param day  int
     * @return Date
     */
    public static Date addDate(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
        return c.getTime();
    }

    /**
     * 秒增加
     *
     * @param date    Date
     * @param seconds int
     * @return Date
     */
    public static Date addSeconds(Date date, int seconds) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) seconds) * 1000);
        return c.getTime();
    }

    /**
     * 分钟增加
     *
     * @param date    Date
     * @param minutes int
     * @return Date
     */
    public static Date addMinutes(Date date, int minutes) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) minutes) * 60 * 1000);
        return c.getTime();
    }

    /**
     * 分钟增加
     *
     * @param date  Date
     * @param hours int
     * @return Date
     */
    public static Date addHours(Date date, int hours) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) hours) * 3600 * 1000);
        return c.getTime();
    }

    /**
     * 日期相减(返回天数)
     *
     * @param date  Date
     * @param date1 Date
     * @return int 相差的天数
     */
    public static int diffDate(Date date, Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }

    public static int diffDays(Date date, Date date1) {
        date = parseDefaultDate(formatDefaultDate(date));
        date1 = parseDefaultDate(formatDefaultDate(date1));
        return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }

    /**
     * 日期相减(返回月数)
     *
     * @param date  Date
     * @param date1 Date
     * @return int 相差的月数
     */
    public static int diffDateMonth(Date date, Date date1) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date);
        c2.setTime(date1);
        int result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        int month = (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) * 12;
        return Math.abs(month + result);
    }

    /**
     * 日期相减(返回秒值)
     *
     * @param date  Date
     * @param date1 Date
     * @return int
     * @author
     */
    public static Long diffDateTime(Date date, Date date1) {
        return (Long) ((getMillis(date) - getMillis(date1)) / 1000);
    }

    /**
     * 获取某月第一天
     */
    public static Date getFirstDayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        //设置年份
        c.set(Calendar.YEAR, year);
        //设置月份
        c.set(Calendar.MONTH, month - 1);
        int firstDay = c.getActualMinimum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最小天数
        c.set(Calendar.DAY_OF_MONTH, firstDay);
        //格式化日期
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        return sdf.format(c.getTime());
        return c.getTime();
    }

    /**
     * 获取本月第一天
     */
    public static Date getFirstDayCurrentMonth() {
        Calendar c = Calendar.getInstance();
        // 设置日历中月份的最小天数
        c.set(Calendar.DAY_OF_MONTH, 1);
        //格式化日期
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        return sdf.format(c.getTime());
        return c.getTime();
    }

    /**
     * 获取某月最后一天
     */
    public static Date getLastDayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        //设置年份
        c.set(Calendar.YEAR, year);
        //设置月份
        c.set(Calendar.MONTH, month - 1);
        int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最大天数
        c.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        return sdf.format(c.getTime());
        return c.getTime();
    }

}
