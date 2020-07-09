package org.march.passbook.consumer.service.impl;

import com.alibaba.fastjson.JSON;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.march.passbook.consumer.constant.Constants;
import org.march.passbook.consumer.mapper.PassTemplateTableRowMapper;
import org.march.passbook.consumer.service.IGainPassTemplateService;
import org.march.passbook.consumer.utils.CommonDateFormatUtil;
import org.march.passbook.consumer.utils.RowKeyGenerateUtil;
import org.march.passbook.consumer.vo.GainPassTemplateRequest;
import org.march.passbook.consumer.vo.PassTemplate;
import org.march.passbook.consumer.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <h1>用户领取优惠券服务功能实现</h1>
 *
 * @author March
 * @date 2020/6/20
 */
@Slf4j
@Service
public class GainPassTemplateServiceImpl implements IGainPassTemplateService {

    /** HBase 客户端 */
    private final HbaseTemplate hbaseTemplate;

    /** Redis 客户端 */
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public GainPassTemplateServiceImpl(HbaseTemplate hbaseTemplate, StringRedisTemplate redisTemplate) {
        this.hbaseTemplate = hbaseTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Response gainPassTemplate(GainPassTemplateRequest request) throws Exception {

        PassTemplate passTemplate = null;
        String passTemplateId = RowKeyGenerateUtil.generatePassTemplateRowKey(request.getPassTemplate());

        try {
            passTemplate = hbaseTemplate.get(
                    Constants.PassTemplateTable.TABLE_NAME,
                    passTemplateId,
                    new PassTemplateTableRowMapper()
            );
        }catch (Exception e) {
            log.error("Gain PassTemplate Error :{}", JSON.toJSONString(request.getPassTemplate()));
            return Response.failure("Gain PassTemplate Error ");
        }

        /** 优惠券数量校验 */
        if (passTemplate.getLimit() <= 1 && passTemplate.getLimit() != -1) {

            log.error("PassTemplate Limit Max: {}",
                    JSON.toJSONString(request.getPassTemplate()));

            return Response.failure("PassTemplate Limit Max!");
        }


        /** 优惠券时间校验 */
        if (!(System.currentTimeMillis() >= passTemplate.getStart().getTime()
                && System.currentTimeMillis() < passTemplate.getEnd().getTime())) {

            log.error("PassTemplate ValidTime Error: {}",
                    JSON.toJSONString(request.getPassTemplate()));

            return Response.failure("PassTemplate ValidTime Error!");
        }

        /** 改变当前商家优惠券库存(优惠券数量有上限的) */
        if (passTemplate.getLimit() != -1) {

            List<Mutation> datas = new ArrayList<>();

            byte[] FAMILY_B = Bytes.toBytes(Constants.PassTemplateTable.FAMILY_B);
            byte[] LIMIT = Bytes.toBytes(Constants.PassTemplateTable.LIMIT);

            Put put = new Put(Bytes.toBytes(passTemplateId));
            put.addColumn(FAMILY_B, LIMIT, Bytes.toBytes(passTemplate.getLimit() - 1));
            datas.add(put);

            hbaseTemplate.saveOrUpdates(Constants.PassTemplateTable.TABLE_NAME, datas);
        }

        /** 保存优惠券到用户 */
        if (!addPassToUser(request,passTemplate.getMerchantsId(),passTemplateId)) {
            return Response.failure("Gain PassTemplate Failure!");
        }
        return Response.success();
    }

    /**
     * <h2>给用户添加优惠券</h2>
     * @param request {@link GainPassTemplateRequest}
     * @param merchantsId 商户 id
     * @param passTemplateId 优惠券模板 id
     * @return boolean
     */
    private boolean addPassToUser(GainPassTemplateRequest request,
                                  Integer merchantsId, String passTemplateId) throws IOException {
        /** 创建接收数据的字节数组 */
        byte[] FAMILY_I = Bytes.toBytes(Constants.PassTable.FAMILY_I);
        byte[] USER_ID = Bytes.toBytes(Constants.PassTable.USER_ID);
        byte[] PASS_TEMPLATE_ID = Bytes.toBytes(Constants.PassTable.PASS_TEMPLATE_ID);
        byte[] PASS_TOKEN = Bytes.toBytes(Constants.PassTable.PASS_TOKEN);
        byte[] ASSIGN_DATE = Bytes.toBytes(Constants.PassTable.ASSIGN_DATE);
        byte[] CONSUMER_DATE = Bytes.toBytes(Constants.PassTable.CONSUMER_DATE);

        Put put = new Put(Bytes.toBytes(RowKeyGenerateUtil.generatePassRowKey(request)));

        put.addColumn(FAMILY_I,USER_ID,Bytes.toBytes(request.getUserId()));
        put.addColumn(FAMILY_I,PASS_TEMPLATE_ID,Bytes.toBytes(passTemplateId));

        if (request.getPassTemplate().getHasToken()) {

            /** 从 redis 中获取当前优惠券的 消费token */
            String passToken = redisTemplate.opsForSet().pop(passTemplateId);

            if (null == passToken) {
                log.error("PassTemplateToken is not Exist:{}",passTemplateId);
                return false;
            }


            recordTokenToFile(merchantsId,passTemplateId,passToken);

            put.addColumn(FAMILY_I, PASS_TOKEN, Bytes.toBytes(passToken));

        }else {
            put.addColumn(FAMILY_I, PASS_TOKEN, Bytes.toBytes("-1"));
        }

        put.addColumn(
                FAMILY_I,
                ASSIGN_DATE,
                Bytes.toBytes(CommonDateFormatUtil.getFormatDate(new Date()))
        );

        put.addColumn(FAMILY_I,CONSUMER_DATE, Bytes.toBytes("-1"));

        hbaseTemplate.saveOrUpdate(Constants.PassTable.TABLE_NAME, put);

        return true;
    }

    /**
     * <h2>将消费优惠券的 token 记录到当前优惠券模板文件中</h2>
     * @param merchantsId 商户 id
     * @param passTemplateId 优惠券模板 id
     * @param token 分配的优惠券 token
     * @return void
     */
    private void recordTokenToFile(Integer merchantsId,
                                   String passTemplateId,
                                   String token) throws IOException {
        Files.write(
                Paths.get(Constants.TOKEN_DIR,
                        String.valueOf(merchantsId),
                        passTemplateId + Constants.USED_TOKEN_SUFFIX),
                (token + "\n").getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );

    }
}
