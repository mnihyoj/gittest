package hy.plugin.common;


import hy.plugin.Tool.Tool;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @description: 时间相关操作
 * @author: Yuan Hong
 * @create: 2018-11-02 14:53
 */
public class TimeTool extends Tool {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String SHOW_DAY = "SHOW_DAY",
            STR_HOUR = "小时", STR_MIN = "分钟", STR_DAY = "天", STR_NOW = "刚刚", STR_AGO = "前",
            STR_TODAY = "今天", STR_YESTERDAY = "昨天", SIR_TWODAYS= "前天";


    public static final int TYPE_YEAR = 1,
            TYPE_MONTH = 2,
            YPE_WEEK = 3,
            TYPE_DAY = 6,
            TYPE_HOUR = 10;


    public Date getDay(long time) {
        try {
            if (time <= 0) {
                return null;
            }
            Date date = new Date(time);
            return date;
        } catch (Exception e) {
            logger.error("Exception: {} , time={} ", e.toString(), time);
            return null;
        }
    }

    private Date getDay(String str, SimpleDateFormat dateFormat) {

        try {
            if (StringUtils.isBlank(str)) {
                return null;
            }
            return dateFormat.parse(str);
        } catch (Exception e) {

            logger.error("Exception: {} ,  str={},  dateFormat={}", e.toString(), str, dateFormat.toPattern());
            return null;
        }
    }


    private String getString(Date date, SimpleDateFormat dateFormat) {

        try {
            if (date == null) {
                return "";
            }
            return dateFormat.format(date);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }



    public Date getDay(String str, ShowType showType) {

        return this.getDay(str, showType.get());
    }


    public String getString(Date date, ShowType showType) {

       return this.getString(date, showType.get());
    }


    public String getString(Date date, String format) {
        ShowType showType =  ShowType.getShowType(format);
        if(showType!=null){
            return getString(date, showType);
        }
        return getString(date, new SimpleDateFormat(format));
    }


    public String showDay(Date date) {
        if (date == null) {
            return "";
        }
        Date now = new Date();
        long difTime = (now.getTime() - date.getTime())/1000;

        //大于等于48小时(48*60*60s)  肯定是昨天之前的日期 直接展示日期
        //小于0  也直接显示日期
        if(difTime>172800l||difTime<0){
            return getString(date, ShowType.MONTH_DAY);
        }

        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(now);

        //小于24小时(24*60*60)  只会是今天或者昨天
        if(difTime<=86400l){
            if(c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)){
                return STR_TODAY;
            }else{
                return STR_YESTERDAY;
            }

        }else{ //大于24小时 且小于等于48小时 只会是昨天或者昨天之前的日期
            c2.add(Calendar.DATE, -1);
            if(c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)){
                return STR_YESTERDAY;
            }else{
                return getString(date, ShowType.MONTH_DAY);
            }
        }
    }


    public String showTime(Date date) {
        if (date == null) {
            return "";
        }
        Date now = new Date();

        long difTime = (now.getTime() - date.getTime())/1000;

        //时间大于当前时间 直接显示yyyy-mm-dd
        if(difTime<0){
            return getString(date, ShowType.YEAR_DAY);
        }

        if (difTime < 60 ) {
            return STR_NOW;
        }

        if (60 <= difTime && difTime < 3600) {
            return difTime/60 + STR_MIN + STR_AGO;
        }
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(now);
        //两个时间的年份及以前 直接显示yyyy-mm-dd
        if(c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR)){
            return getString(date, ShowType.YEAR_DAY);
        }

        //两个日期的时间差（注意已经排除跨年）
        int difDay = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
        if(difDay >5){
            return getString(date, ShowType.MONTH_DAY);
        }
        if(difDay == 0){//今天
            return difTime/3600 + STR_HOUR + STR_AGO;
        }
        if(difDay == 1){//昨天
            return STR_YESTERDAY;
        }
        if(difDay == 2){//昨天
            return SIR_TWODAYS;
        }
        return difDay+STR_DAY+STR_AGO;


    }


//    public int getMinutes(Date startDate, Date endDate) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(startDate);
//        long timestart = calendar.getTimeInMillis();
//        calendar.setTime(endDate);
//        long timeend = calendar.getTimeInMillis();
//        long totalDate = Math.abs((timeend - timestart)) / (1000 * 60);
//        return (int) totalDate;
//    }
//
//    /**
//     * end_date - begin_date
//     * 如果  begin_date>  end_date则返回负数   如果  end_date>  begin_date则返回正数
//     *
//     * @param begin_date
//     * @param end_date
//     * @return
//     */
//    public Long getTwoTime(Date begin_date, Date end_date) {
//        return end_date.getTime() - begin_date.getTime();
//
//    }
//
//
//    public int getTwoDay(Date begin_date, Date end_date) {
//        return (int) ((end_date.getTime() - begin_date.getTime()) / (24 * 60 * 60 * 1000));
//
//    }
//
//    public Date changeDay(Date date, int type, int val) {
//        Calendar c = Calendar.getInstance();
//        c.setTime(date);
//        c.add(type, val);
//        return c.getTime();
//    }
//
//
//    public Date getMonthFristDate(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.set(Calendar.DAY_OF_MONTH, 1);
//        //将小时至0
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        //将分钟至0
//        calendar.set(Calendar.MINUTE, 0);
//        //将秒至0
//        calendar.set(Calendar.SECOND, 0);
//        //将毫秒至0
//        calendar.set(Calendar.MILLISECOND, 0);
//        return calendar.getTime();
//    }
//
//
//    public int getDistDate(Date startDate, Date endDate) {
//
//        try {
//
//            Calendar c1 = Calendar.getInstance();
//            c1.setTime(startDate);
//            Calendar c2 = Calendar.getInstance();
//            c2.setTime(endDate);
//
//
//            if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))) {      //今天 今天 hh:mm
//                return c2.get(Calendar.DAY_OF_MONTH) - c1.get(Calendar.DAY_OF_MONTH);
//            }
//
//            return -1;
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return -1;
//        }
//
//    }
//
//
//    public long getDistDates(long time) {
//        long nowtime = System.currentTimeMillis();
//        if (time <= 0) {
//            return nowtime;
//        }
//        return nowtime - time;
//
//    }
//
//    public boolean isSameDay(Date a, Date b) {
//        String astr = FORMAT_DAY.format(a);
//        String bstr = FORMAT_DAY.format(b);
//        if (astr.equals(bstr)) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public String secToStr(Long time) {
//        if (time == null || time <= 0) {
//            return stringTool.stringAdd("0", STR_MIN);
//        }
//
//        long hour = time / (60 * 60);
//        long min = (time % (60 * 60)) / 60;
//        String str = "";
//        if (hour > 0) {
//            str = stringTool.stringAdd(str, String.valueOf(hour), STR_HOUR);
//        }
//        if (min > 0) {
//            str = stringTool.stringAdd(str, String.valueOf(min), STR_MIN);
//        } else {
//            str = stringTool.stringAdd(str, "0", STR_MIN);
//        }
//        return str;
//    }
//
//
//    public String secToTime(int time) {
////		int time = 0;
////		try {
////			time = Integer.parseInt(secStr);
////		} catch (Exception e) {
////			time = 0;
////		}
//
//        String timeStr = null;
//        int hour = 0;
//        int minute = 0;
//        int second = 0;
//        if (time <= 0)
//            return "00:00";
//        else {
//            minute = time / 60;
//            if (minute < 60) {
//                second = time % 60;
//                timeStr = unitFormat(minute) + ":" + unitFormat(second);
//            } else {
//                hour = minute / 60;
//                if (hour > 99)
//                    return "99:59:59";
//                minute = minute % 60;
//                second = time - hour * 3600 - minute * 60;
//                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
//            }
//        }
//        return timeStr;
//    }
//
//    private String unitFormat(int i) {
//        String retStr = null;
//        if (i >= 0 && i < 10) {
//            retStr = "0" + Integer.toString(i);
//        } else {
//            retStr = "" + i;
//
//        }
//        return retStr;
//    }
//
//
//    public Long getTime(Date date) {
//        if (date == null) {
//            return 0l;
//        }
//
//        return date.getTime();
//    }
//
//    public String createTimestamp() {
//        return Long.toString(System.currentTimeMillis() / 1000);
//    }

}
