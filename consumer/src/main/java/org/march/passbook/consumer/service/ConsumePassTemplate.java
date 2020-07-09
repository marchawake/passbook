package org.march.passbook.consumer.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.march.passbook.consumer.constant.Constants;
import org.march.passbook.consumer.vo.PassTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * 消费Kafka 中的 PassTemplate
 *
 * @author March
 * @date 2020/6/19
 */
@Slf4j
@Component
public class ConsumePassTemplate {

    /** Pass 相关的 HBase 服务 */
    private final IHBasePassService passService;

    @Autowired
    public ConsumePassTemplate(IHBasePassService passService) {
        this.passService = passService;
    }

    /**
     * 接收
     * @param passTemplate {@link String}
     * @param key {@link String}
     * @param partition int
     * @param topic {@link String}
     * @return void
     */
    @KafkaListener(topics = {Constants.TEMPLATE_TOPIC})
    public void receive(@Payload String passTemplate,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Consumer Receive PassTemplate: {}",passTemplate);

        PassTemplate pt = null;
        try {
            pt = JSON.parseObject(passTemplate, PassTemplate.class);
        } catch(Exception e){
            log.error("Parse PassTemplate Error:{}",e.getMessage());
        }

        log.info("DropPassTemplateToHBase:{}",passService.dropPassTemplateToHBase(pt));
    }
}
