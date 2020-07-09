package org.march.passbook.consumer.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 通用是日期格式
 *
 * @author March
 * @date 2020/6/28
 */
public class CommonDateFormatUtil {
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 日期格式化为字符串 ISO_8601_EXTENDED_DATETIME_FORMAT
     * @param date {@link Date}
     * @return java.lang.String
     */
    public static String getFormatDate(Date date) {
        return format.format(date);
    }

    public static Date getFormatDate(String dateStr) throws ParseException {
        return format.parse(dateStr);
    }

    public static String getFormatDate(Long timestamp) throws ParseException {
        return format.format(timestamp);
    }


}
