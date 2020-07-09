package org.march.passbook.consumer.mapper;

import com.spring4all.spring.boot.starter.hbase.api.RowMapper;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.march.passbook.consumer.constant.Constants;
import org.march.passbook.consumer.vo.Feedback;

/**
 * HBase FeedbackTable Row To Feedback Object
 * HBase反馈表行转换为用户反馈反馈对象
 *
 * @author March
 * @date 2020/6/19
 */
public class FeedbackTableRowMapper implements RowMapper<Feedback> {

    /** Feedback 表列常量转为字节数组 */

    private static byte[] FAMILY_I = Constants.FeedbackTable.FAMILY_I.getBytes();

    private static byte[] USER_ID = Constants.FeedbackTable.USER_ID.getBytes();

    private static byte[] TYPE = Constants.FeedbackTable.TYPE.getBytes();

    private static byte[] PASS_TEMPLATE_ID = Constants.FeedbackTable.PASS_TEMPLATE_ID.getBytes();

    private static byte[] COMMENT = Constants.FeedbackTable.COMMENT.getBytes();

    @Override
    public Feedback mapRow(Result result, int i) throws Exception {
        /** 创建 Feedback 实例*/
        Feedback feedback = new Feedback();

        /** 将结果数据封装到实例 */
        feedback.setUserId(Bytes.toLong(result.getValue(FAMILY_I, USER_ID)));
        feedback.setType(Bytes.toString(result.getValue(FAMILY_I, TYPE)));
        feedback.setPassTemplateId(Bytes.toString(result.getValue(FAMILY_I, PASS_TEMPLATE_ID)));
        feedback.setComment(Bytes.toString(result.getValue(FAMILY_I, COMMENT)));
        feedback.setId(Bytes.toString(result.getRow()));

        return feedback;
    }
}
