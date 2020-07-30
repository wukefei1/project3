package Tools;

import java.sql.Date;
import java.sql.Timestamp;

public class DateRandom {
    public static Date randomHireday() {
        int startYear = 2016;                                    //指定随机日期开始年份
        int endYear = 2018;                                    //指定随机日期开始年份(含)
        long start = Timestamp.valueOf(startYear + 1 + "-1-1 0:0:0").getTime();
        long end = Timestamp.valueOf(endYear + "-1-1 0:0:0").getTime();
        long ms = (long) ((end - start) * Math.random() + start);    //获得了符合条件的13位毫秒数
        return new Date(ms);
    }
}
