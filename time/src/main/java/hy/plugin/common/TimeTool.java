package hy.plugin.common;


import hy.plugin.common.Tool.Tool;
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
            STR_TODAY = "今天", STR_YESTERDAY = "昨天", SIR_TWODAYS = "前天";





//
//    public static final int TYPE_YEAR = 1,
//            TYPE_MONTH = 2,
//            YPE_WEEK = 3,
//            TYPE_DAY = 6,
//            TYPE_HOUR = 10;


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


    /**
     * @Description:
     * @Param: [str, dateFormat]
     * @return: java.util.Date
     * @Author: Yuan Hong
     * @Date: 2018/11/5
     */
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

    /**
     * @Description:
     * @Param: [date, dateFormat]
     * @return: java.lang.String
     * @Author: Yuan Hong
     * @Date: 2018/11/5
     */
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
        ShowType showType = ShowType.getShowType(format);
        if (showType != null) {
            return getString(date, showType);
        }
        return getString(date, new SimpleDateFormat(format));
    }


    public String showDay(Date date) {
        return showDay(date, null);
    }

    public String showDay(Date start, Date end) {

        if (start == null) {
            return "";
        }
        if (end == null) {
            end = new Date();
        }
        long difTime = (start.getTime() - end.getTime()) / 1000;

        //大于等于48小时(48*60*60s)  肯定是昨天之前的日期 直接展示日期
        //小于0  也直接显示日期
        if (difTime > 172800l || difTime < 0) {
            return getString(start, ShowType.MONTH_DAY);
        }

        Calendar c1 = Calendar.getInstance();
        c1.setTime(start);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(end);

        //小于24小时(24*60*60)  只会是今天或者昨天
        if (difTime <= 86400l) {
            if (c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)) {
                return STR_TODAY;
            } else {
                return STR_YESTERDAY;
            }

        } else { //大于24小时 且小于等于48小时 只会是昨天或者昨天之前的日期
            c2.add(Calendar.DATE, -1);
            if (c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)) {
                return STR_YESTERDAY;
            } else {
                return getString(start, ShowType.MONTH_DAY);
            }
        }


    }


    public String showTime(Date date) {
        return showTime(date, null);
    }


    public String showTime(Date start, Date end) {
        if (start == null) {
            return "";
        }
        if (end == null) {
            end = new Date();
        }

        long difTime = (end.getTime() - start.getTime()) / 1000;

        //时间大于当前时间 直接显示yyyy-mm-dd
        if (difTime < 0) {
            return getString(start, ShowType.YEAR_DAY);
        }

        if (difTime < 60) {
            return STR_NOW;
        }

        if (60 <= difTime && difTime < 3600) {
            return difTime / 60 + STR_MIN + STR_AGO;
        }
        Calendar c1 = Calendar.getInstance();
        c1.setTime(start);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(end);
        //两个时间的年份及以前 直接显示yyyy-mm-dd
        if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR)) {
            return getString(start, ShowType.YEAR_DAY);
        }

        //两个日期的时间差（注意已经排除跨年）
        int difDay = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
        if (difDay > 5) {
            return getString(start, ShowType.MONTH_DAY);
        }
        if (difDay == 0) {//今天
            return difTime / 3600 + STR_HOUR + STR_AGO;
        }
        if (difDay == 1) {//昨天
            return STR_YESTERDAY;
        }
        if (difDay == 2) {//昨天
            return SIR_TWODAYS;
        }
        return difDay + STR_DAY + STR_AGO;


    }


    public int getMinutes(Date startDate, Date endDate) {
        return (int) Math.abs((endDate.getTime() - startDate.getTime())) / (1000 * 60);
    }

    /**
     * end_date - begin_date
     * 如果  begin_date>  end_date则返回负数   如果  end_date>  begin_date则返回正数
     *
     * @param begin_date
     * @param end_date
     * @return
     */
    public Long getTwoTime(Date begin_date, Date end_date) {
        return end_date.getTime() - begin_date.getTime();

    }


    public int getTwoDay(Date begin_date, Date end_date) {
        return (int) ((end_date.getTime() - begin_date.getTime()) / 86400000l);

    }

    public Date changeDay(Date date, int type, int val) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(type, val);
        return c.getTime();
    }

    /**
     * @Description: 获取date 的月份的第一天
     * @Param: [date]
     * @return: java.util.Date
     * @Date: 2018/11/5
     */
    public Date getMonthFristDate(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //将小时至0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        calendar.set(Calendar.MINUTE, 0);
        //将秒至0
        calendar.set(Calendar.SECOND, 0);
        //将毫秒至0
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * @Description:
     * @Param: [startDate, endDate]
     * @return: int
     * @Date: 2018/11/9
     */
    public int getDistDate(Date startDate, Date endDate) {

        try {

            Calendar c1 = Calendar.getInstance();
            c1.setTime(startDate);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(endDate);


            if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) {      //今天 今天 hh:mm
                return c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
            }

            return -1;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return -1;
        }

    }

    public long getDistDates(long time) {
        long nowtime = System.currentTimeMillis();
        if (time <= 0) {
            return nowtime;
        }
        return nowtime - time;

    }

    public boolean isSameDay(Date a, Date b) {
        if (a == null || b == null) {
            return false;
        }
        return this.getString(a, ShowType.YEAR_DAY).equals(this.getString(b, ShowType.YEAR_DAY));
    }

    public String secToStr(Long time) {
        if (time == null || time <= 0) {
            return 0+STR_MIN;
        }

        long hour = time / 3600;
        if (hour > 0) {
            return hour + STR_HOUR;
        }

        long min = (time % 3600) / 60;
        if (min > 0) {
            return min + STR_MIN;
        }
        return 0+ STR_MIN;
    }


    public String secToTime(int time) {

        String timeStr ;
        int hour ;
        int min ;
        int second ;
        if (time <= 0)
            return "00:00";
        else {
            min = time / 60;
            if (min < 60) {
                second = time % 60;
                timeStr = unitFormat(min) + ":" + unitFormat(second);
            } else {
                hour = min / 60;
                if (hour > 99)
                    return "99:59:59";
                min = min % 60;
                second = time - hour * 3600 - min * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(min) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    private String unitFormat(int i) {
        if (i >= 0 && i < 10) {
            return "0" + Integer.toString(i);
        }
        return  "" + i;
    }


    public Long getTime(Date date) {
        if (date == null) {
            return 0l;
        }

        return date.getTime();
    }

    public String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

}
