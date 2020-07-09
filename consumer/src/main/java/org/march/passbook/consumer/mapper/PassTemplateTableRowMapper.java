package org.march.passbook.consumer.mapper;


import com.spring4all.spring.boot.starter.hbase.api.RowMapper;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.march.passbook.consumer.constant.Constants;
import org.march.passbook.consumer.utils.CommonDateFormatUtil;
import org.march.passbook.consumer.vo.PassTemplate;

/**
 * HBase PassTemplate Row To PassTemplate Object
 * HBase PassTemplate行转换为PassTemplate对象
 *
 * @author March
 * @date 2020/6/19
 */
public class PassTemplateTableRowMapper implements RowMapper<PassTemplate> {

    /** 准备接收数据的缓存字节数组 */
    private static byte[] FAMILY_B = Constants.PassTemplateTable.FAMILY_B.getBytes();

    private static byte[] MERCHANTS_ID = Constants.PassTemplateTable.MERCHANTS_ID.getBytes();

    private static byte[] TITLE = Constants.PassTemplateTable.TITLE.getBytes();

    private static byte[] SUMMARY = Constants.PassTemplateTable.SUMMARY.getBytes();

    private static byte[] DESC = Constants.PassTemplateTable.DESC.getBytes();

    private static byte[] HAS_TOKEN = Constants.PassTemplateTable.HAS_TOKEN.getBytes();

    private static byte[] BACKGROUND = Constants.PassTemplateTable.BACKGROUND.getBytes();

    private static byte[] FAMILY_C = Constants.PassTemplateTable.FAMILY_C.getBytes();

    private static byte[] LIMIT = Constants.PassTemplateTable.LIMIT.getBytes();

    private static byte[] START = Constants.PassTemplateTable.START.getBytes();

    private static byte[] END = Constants.PassTemplateTable.END.getBytes();

    @Override
    public PassTemplate mapRow(Result result, int i) throws Exception {

        /** 创建优惠券实例 */
        PassTemplate passTemplate = new PassTemplate();

        /** 将数据设置给优惠券实例 */
        passTemplate.setMerchantsId(Bytes.toInt(result.getValue(FAMILY_B, MERCHANTS_ID)));
        passTemplate.setTitle(Bytes.toString(result.getValue(FAMILY_B, TITLE)));
        passTemplate.setSummary(Bytes.toString(result.getValue(FAMILY_B, SUMMARY)));
        passTemplate.setDesc(Bytes.toString(result.getValue(FAMILY_B, DESC)));
        passTemplate.setHasToken(Bytes.toBoolean(result.getValue(FAMILY_B, HAS_TOKEN)));
        passTemplate.setBackground(Bytes.toInt(result.getValue(FAMILY_B, BACKGROUND)));

        /** 将数据设置给优惠券实例 */
        passTemplate.setLimit(Bytes.toLong(result.getValue(FAMILY_C, LIMIT)));
        passTemplate.setStart(CommonDateFormatUtil.getFormatDate(Bytes.toString(result.getValue(FAMILY_C,START))));
        passTemplate.setEnd(CommonDateFormatUtil.getFormatDate(Bytes.toString(result.getValue(FAMILY_C,END))));

        passTemplate.setId(Bytes.toString(result.getRow()));

        return passTemplate;
    }
}
