package hy.plugin.common;

import hy.plugin.common.factory.ToolFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.junit.Assert.*;

public class TimeToolTest {


    TimeTool timeTool = ToolFactory.create(TimeTool.class);

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void getDay() {
        Date date = timeTool.getDay(System.currentTimeMillis());
        logger.info("getDay："+date.toString());
    }

    @Test
    public void getDay1() {
        Date date = timeTool.getDay("2012.01.12",ShowType.D_YEAR_DAY);
        logger.info("getDay1："+date);
    }

    @Test
    public void getString() {
        logger.info("getString："+timeTool.getString(timeTool.getDay("2012.01.12",ShowType.D_YEAR_DAY),ShowType.SYS_DF));
    }

    @Test
    public void getString1() {
        logger.info("getString1："+timeTool.getString(timeTool.getDay("2012.01.12",ShowType.D_YEAR_DAY),"YYYY-MM-DD"));
    }

    @Test
    public void showDay() {
        logger.info("showDay："+timeTool.showDay(timeTool.getDay("2018.10.31",ShowType.D_YEAR_DAY)));
    }

    @Test
    public void showTime() {
        logger.info("showTime："+timeTool.showTime(timeTool.getDay("2018-11-3 10:12:00",ShowType.SYS_DF)));
    }
}