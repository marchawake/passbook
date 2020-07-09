package org.march.passbook.merchants.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.march.passbook.merchants.constant.Constant;
import org.march.passbook.merchants.constant.ErrorCode;
import org.march.passbook.merchants.dao.MerchantsDao;
import org.march.passbook.merchants.entity.Merchants;
import org.march.passbook.merchants.service.MerchantsService;
import org.march.passbook.merchants.vo.CreateMerchantsRequest;
import org.march.passbook.merchants.vo.CreateMerchantsResponse;
import org.march.passbook.merchants.vo.PassTemplate;
import org.march.passbook.merchants.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

/**
 * 定义商户服务接口实现类
 *
 * @author March
 * @date 2020/6/17
 */
@Service
@Slf4j
public class MerchantsServiceImpl implements MerchantsService {

    /** 商户数据访问接口引用 */
    private final MerchantsDao merchantsDao;

    /** kafka 客户端 */
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public MerchantsServiceImpl(MerchantsDao merchantsDao, KafkaTemplate<String, String> kafkaTemplate) {
        this.merchantsDao = merchantsDao;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @Transactional
    public Response createMerchants(CreateMerchantsRequest request) {

        /** 创建通用响应对象 */
        Response response = new Response();

        /** 创建 创建商户响应对象 */
        CreateMerchantsResponse createMerchantsResponse = new CreateMerchantsResponse();

        /** 校验请求对象有效性 */
        ErrorCode errorCode = request.validate(merchantsDao);
        response.setErrorCode(errorCode.getErrorCode());
        response.setErrorMsg(errorCode.getErrorDesc());

        if (errorCode == ErrorCode.SUCCESS) {

            createMerchantsResponse.setId(merchantsDao.save(request.toMerchants()).getId());
            response.setData(createMerchantsResponse);
        }
        return response;
    }

    @Override
    public Response buildMerchantsById(Integer id) {

        /** 创建通用响应对象 */
        Response response = new Response();

        /** 通过商户 id 获取 Optional<Merchants> 对象*/
        Optional<Merchants> optional = merchantsDao.findById(id);

        /** 校验 id 有效性*/
        if (!optional.isPresent()) {

            /** 设置响应对象的值 */
            response.setErrorCode(ErrorCode.MERCHANTS_NOT_EXISTS.getErrorCode());
            response.setErrorMsg(ErrorCode.MERCHANTS_NOT_EXISTS.getErrorDesc());
        }else {

            /** 设置响应对象的值 */
            response.setErrorCode(ErrorCode.SUCCESS.getErrorCode());
            response.setErrorMsg(ErrorCode.SUCCESS.getErrorDesc());
            response.setData(optional.get());
        }
        return response;
    }

    @Override
    public Response dropPassTemplate(PassTemplate template) {

        /** 时间截止处理 */
        Date start = DateUtils.truncate(template.getStart(), Calendar.DATE);
        Date end = DateUtils.ceiling(template.getEnd(), Calendar.DATE);
        template.setStart(start);
        template.setEnd(new Date(end.getTime() -1));

        /** 创建通用响应对象 */
        Response response = new Response();

        /** 校验优惠券的有效性 */
        ErrorCode errorCode = template.validate(merchantsDao);

        /** 设置响应对象的值 */
        response.setErrorCode(errorCode.getErrorCode());
        response.setErrorMsg(errorCode.getErrorDesc());

        if (errorCode == ErrorCode.SUCCESS) {

            /** 序列化成JSON字符串 */
            String passTemplate = JSON.toJSONString(template);

            /** 使用 Kafka 发送信息 */
            kafkaTemplate.send(
                    Constant.TEMPLATE_TOPIC,
                    Constant.TEMPLATE_TOPIC,
                    passTemplate
            );

            log.info("DropPassTemplate: {}", passTemplate);
        }
        return response;
    }
}
