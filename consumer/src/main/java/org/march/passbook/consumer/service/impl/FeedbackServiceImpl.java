package org.march.passbook.consumer.service.impl;

import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.march.passbook.consumer.constant.Constants;
import org.march.passbook.consumer.mapper.FeedbackTableRowMapper;
import org.march.passbook.consumer.service.IFeedbackService;
import org.march.passbook.consumer.utils.RowKeyGenerateUtil;
import org.march.passbook.consumer.vo.Feedback;
import org.march.passbook.consumer.vo.Response;
import org.mortbay.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论功能服务实现
 *
 * @author March
 * @date 2020/6/19
 */
@Slf4j
@Service
public class FeedbackServiceImpl implements IFeedbackService {

    private final HbaseTemplate hbaseTemplate;

    @Autowired
    public FeedbackServiceImpl(HbaseTemplate hbaseTemplate) {
        this.hbaseTemplate = hbaseTemplate;
    }

    @Override
    public Response createFeedback(Feedback feedback) {

        /** 校验评论是否有效 */
        if (!feedback.validate()) {
            log.error("Feedback Error :{}", JSON.toString(feedback));
            return Response.failure("Feedback Error");
        }

        /** 创建 Put 实例 */
        Put put = new Put(Bytes.toBytes(RowKeyGenerateUtil.generateFeedbackRowKey(feedback)));

        /** */
        put.addColumn(Bytes.toBytes(Constants.FeedbackTable.FAMILY_I),
                Bytes.toBytes(Constants.FeedbackTable.USER_ID),
                Bytes.toBytes(feedback.getUserId()));

        put.addColumn(Bytes.toBytes(Constants.FeedbackTable.FAMILY_I),
                Bytes.toBytes(Constants.FeedbackTable.PASS_TEMPLATE_ID),
                Bytes.toBytes(feedback.getPassTemplateId()));

        put.addColumn(Bytes.toBytes(Constants.FeedbackTable.FAMILY_I),
                Bytes.toBytes(Constants.FeedbackTable.TYPE),
                Bytes.toBytes(feedback.getType()));

        put.addColumn(Bytes.toBytes(Constants.FeedbackTable.FAMILY_I),
                Bytes.toBytes(Constants.FeedbackTable.COMMENT),
                Bytes.toBytes(feedback.getComment()));

        hbaseTemplate.saveOrUpdate(Constants.FeedbackTable.TABLE_NAME, put);

        return Response.success();
    }

    @Override
    public Response getFeedback(Long userId) {
        /** 翻转用户 id */
        byte[] reverseUserId = new StringBuilder(String.valueOf(userId)).reverse().toString().getBytes();

        Scan scan = new Scan();
        scan.setFilter(new PrefixFilter(reverseUserId));

        List<Feedback> feedbacks = hbaseTemplate.find(Constants.FeedbackTable.TABLE_NAME, scan, new FeedbackTableRowMapper());
        return new Response(feedbacks);
    }
}
