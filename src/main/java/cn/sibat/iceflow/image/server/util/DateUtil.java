package cn.sibat.iceflow.image.server.util;

import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Date 日期组件
 *
 * @author iceflow
 */
public class DateUtil {
    Logger logger = Logger.getLogger(DateUtil.class);
    private final static String PATTERN_yyyy_MM_dd = "yyyy-MM-dd";
    private final static String PATTERN_YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    private final static String PATIERN_yyyy_MM_dd_HHmm = "yyyy-MM-dd HH:mm";
    public final static String PATIERN_YYYYMM = "yyyyMM";
    static DateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");

    public static List<String> getDates(Integer year, Integer month) {
        List<String> dates = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, 1);
        while (cal.get(Calendar.YEAR) == year &&
                cal.get(Calendar.MONTH) < month) {
            dates.add(yyyy_MM_dd.format(cal.getTime()));
            cal.add(Calendar.DATE, 1);
        }
        return dates;
    }

    public static String getCurrent_yyyy_MM_dd_HHmmss() {
        return parseDateToString(Calendar.getInstance().getTime(),
                PATTERN_YYYY_MM_DD_HHMMSS);
    }

    public static String getCurrent_yyyy_MM_dd_HHmm() {
        return parseDateToString(Calendar.getInstance().getTime(), PATIERN_yyyy_MM_dd_HHmm);
    }

    public static String getCurrent_yyyy_MM_dd_HHmm(Date date) {
        return parseDateToString(date, PATIERN_yyyy_MM_dd_HHmm);
    }

    public static String getCurrentTimeyyyy_MM_dd() {
        return parseDateToString(Calendar.getInstance().getTime(),
                PATTERN_yyyy_MM_dd);
    }

    public static String parseDateToString(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }
    /**
     * 获取上一个月
     *
     * @param time [String] String格式的时间
     * @return 返回上一个月的String格式
     */
    public static String getLastMonthTime(String time) {
        String lastTime = null;
        String[] months = {"01", "02", "03", "04", "05", "06", "07", "08",
                "09", "10", "11", "12"};
        String month = time.split("-")[1];
        for (int i = 0; i < months.length; i++) {
            if (month.equals("01")) {
                lastTime = (Integer.valueOf(time.split("-")[0]) - 1) + "-"
                        + months[11];
            } else if (months[i].equals(month)) {
                lastTime = time.split("-")[0] + "-" + months[i - 1];
            }
        }
        return lastTime;
    }

    /**
     * 获取上一天
     *
     * @param time [String] String格式的时间
     * @return 返回上一天的String格式
     */
    public static String getLastDay(String time) {
        String lastDayString = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        try {
            c1.setTime(sdf.parse(time));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        c1.add(Calendar.DATE, -1);
        lastDayString = sdf.format(c1.getTime());
        return lastDayString;
    }

    /**
     * 获取下一天
     *
     * @param time [String] String格式的时间
     * @return 返回下一天的String格式
     */
    public static String getNextDay(String time) {
        String lastDayString = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        try {
            c1.setTime(sdf.parse(time));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        c1.add(Calendar.DATE, 1);
        lastDayString = sdf.format(c1.getTime());
        return lastDayString;
    }

    /**
     * 获取当前时间的String格式
     *
     * @return
     */
    public static String getCurrentTimeString2() {
        return parseDateToString(Calendar.getInstance().getTime(),
                PATTERN_YYYY_MM_DD_HHMMSS);
    }

    /**
     * 获取当前时间的Timestamp，精确到毫秒
     *
     * @return Timestamp
     */
    public static Timestamp getCurrentTimestamp() {
        String strDate = parseDateToString(new Date(), PATTERN_YYYY_MM_DD_HHMMSS);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        ts = Timestamp.valueOf(strDate);
        return ts;
    }
}
