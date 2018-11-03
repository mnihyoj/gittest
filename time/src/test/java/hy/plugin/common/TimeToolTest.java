package hy.plugin.common;

import hy.plugin.factory.ToolFactory;
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
        logger.info("测试："+date.toString());
    }

    @Test
    public void getDay1() {
        Date date = timeTool.getDay("2012.01.12",ShowType.D_YEAR_DAY);
        logger.info("测试："+date);
    }

    @Test
    public void getString() {
        logger.info(timeTool.getString(timeTool.getDay("2012.01.12",ShowType.D_YEAR_DAY),ShowType.SYS_DF));
    }

    @Test
    public void getString1() {
        logger.info(timeTool.getString(timeTool.getDay("2012.01.12",ShowType.D_YEAR_DAY),"YYYY-MM-DD"));
    }

    @Test
    public void showDay() {
    }

    @Test
    public void show() {
    }
}