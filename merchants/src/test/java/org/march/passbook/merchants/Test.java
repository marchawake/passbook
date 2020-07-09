package org.march.passbook.merchants;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.march.passbook.merchants.vo.PassTemplate;

import java.util.Calendar;
import java.util.Date;

/**
 * dsafsd
 *
 * @author March
 * @date 2020/6/28
 */
public class Test {

    @org.junit.jupiter.api.Test
    public void hello() {
        Date date = new Date();
        Date truncate = DateUtils.ceiling(date,Calendar.DATE);
        Date round = DateUtils.round(date, Calendar.DATE);
        System.out.println(DateFormatUtils.format(truncate.getTime() -1, "yyyy-MM-dd HH:mm:ss"));
        System.out.println(round);
    }
}
