package org.march.passbook.consumer.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.march.passbook.consumer.vo.Feedback;
import org.march.passbook.consumer.vo.GainPassTemplateRequest;
import org.march.passbook.consumer.vo.PassTemplate;

/**
 * RowKey 生成器工具类
 *
 * @author March
 * @date 2020/6/19
 */
@Slf4j
public class RowKeyGenerateUtil {

    /**
     * 根据提供的 PassTemplate 对象，生成对应的 RowKey
     * @param passTemplate {@link PassTemplate}
     * @return {@link String}
     */
    public static String generatePassTemplateRowKey(PassTemplate passTemplate) {

        String passInfo = String.valueOf(passTemplate.getMerchantsId() + "_" + passTemplate.getTitle());
        String rowKey = DigestUtils.md5Hex(passInfo);

        log.info("GeneratePassTemplateRowKey: {}", passInfo, rowKey);
        return rowKey;
    }

    /**
     * <h2>根据提供的优惠券领取请求生成 RowKey， 只可以在领取优惠券的时候使用</h2>
     * Pass RowKey = reversed(userId) + inverse(timestamp) + PassTemplate RowKey
     * @param request {@link GainPassTemplateRequest}
     * @return {@link String}
     */
    public static String generatePassRowKey(GainPassTemplateRequest request) {
        return new StringBuilder(String.valueOf(request.getUserId())).reverse().toString()
                + (Long.MAX_VALUE - System.currentTimeMillis())
                + generatePassTemplateRowKey(request.getPassTemplate());
    }
    
    /**
     * 根据提供的 Feedback 对象，生成对应的 RowKey
     * @param feedback {@link Feedback}
     * @return java.lang.String
     */
    public static String generateFeedbackRowKey(Feedback feedback) {
        return new StringBuilder(String.valueOf(feedback.getUserId())).reverse().toString() +
                (Long.MAX_VALUE - System.currentTimeMillis());
    }
}
