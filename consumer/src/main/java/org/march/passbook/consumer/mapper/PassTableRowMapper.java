package org.march.passbook.consumer.mapper;

import com.spring4all.spring.boot.starter.hbase.api.RowMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.march.passbook.consumer.constant.Constants;
import org.march.passbook.consumer.utils.CommonDateFormatUtil;
import org.march.passbook.consumer.vo.Pass;



/**
 * HBase PassTable Row To Pass Object
 * HBase Pass表行数据转换为Pass对象(用户优惠券)
 * @author March
 * @date 2020/6/19
 */
@Slf4j
public class PassTableRowMapper implements RowMapper<Pass> {

    /** Pass表字段常量转换为字节数组 */
    private static byte[] FAMILY_I = Constants.PassTable.FAMILY_I.getBytes();

    private static byte[] USER_ID = Constants.PassTable.USER_ID.getBytes();

    private static byte[] PASS_TEMPLATE_ID = Constants.PassTable.PASS_TEMPLATE_ID.getBytes();

    private static byte[] PASS_TOKEN = Constants.PassTable.PASS_TOKEN.getBytes();

    private static byte[] ASSIGN_DATE = Constants.PassTable.ASSIGN_DATE.getBytes();

    private static byte[] CONSUMER_DATE = Constants.PassTable.CONSUMER_DATE.getBytes();


    @Override
    public Pass mapRow(Result result, int i) throws Exception {

        /** 创建Pass实例 */
        Pass pass = new Pass();

        /** 将数据封装到pass实例 */

        pass.setUserId(Bytes.toLong(result.getValue(FAMILY_I, USER_ID)));
        pass.setPassToken(Bytes.toString(result.getValue(FAMILY_I, PASS_TOKEN)));
        pass.setPassTemplateId(Bytes.toString(result.getValue(FAMILY_I, PASS_TEMPLATE_ID)));
        pass.setAssignedDate(CommonDateFormatUtil.getFormatDate(Bytes.toString(result.getValue(FAMILY_I, ASSIGN_DATE))));


        String consumerDateStr = Bytes.toString(result.getValue(FAMILY_I, CONSUMER_DATE));

        if (consumerDateStr.equals("-1")) {
            pass.setConsumerDate(null);
        }else {
            pass.setConsumerDate(CommonDateFormatUtil.getFormatDate(Bytes.toString(result.getValue(FAMILY_I, CONSUMER_DATE))));
        }

        pass.setId(Bytes.toString(result.getRow()));

        return pass;
    }
}
