package org.march.passbook.consumer.service;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.march.passbook.consumer.constant.FeedbackType;
import org.march.passbook.consumer.vo.Feedback;
import org.march.passbook.consumer.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 用户评论服务测试
 *
 * @author March
 * @date 2020/6/27
 */
@SpringBootTest
public class FeedbackServiceTest {

    @Autowired
    private IFeedbackService feedbackService;

    @Test
    public void createFeedback() {
        Feedback feedback = new Feedback();
        feedback.setUserId(156805L);
        feedback.setComment("哼很好用");
        feedback.setType(FeedbackType.APP.getCode());
        Response response = feedbackService.createFeedback(feedback);

        System.out.println(JSON.toJSONString(response));

        feedback.setPassTemplateId("f8860f6d9b3d0c467da24f7f68b7037a");
        feedback.setComment("多发点优惠券");
        feedback.setType(FeedbackType.PASS.getCode());

        Response response2 = feedbackService.createFeedback(feedback);
        System.out.println(JSON.toJSONString(response2));
    }

    @Test
    public void getFeedback() {
        Response response = feedbackService.getFeedback(156805L);

        System.out.println(JSON.toJSONString(response));
    }
}
