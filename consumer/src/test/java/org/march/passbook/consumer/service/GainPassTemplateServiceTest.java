package org.march.passbook.consumer.service;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.march.passbook.consumer.vo.GainPassTemplateRequest;
import org.march.passbook.consumer.vo.PassTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *<h1> 用户领取优惠券服务测试 </h1>
 *
 * @author March
 * @date 2020/6/26
 */
@SpringBootTest
public class GainPassTemplateServiceTest extends AbstractServiceTest {

    @Autowired
    private IGainPassTemplateService gainPassTemplateService;

    @Test
    public void gainPassTemplateTest() throws Exception {
        PassTemplate passTemplate = new PassTemplate();
        passTemplate.setMerchantsId(2);
        passTemplate.setHasToken(true);
        passTemplate.setTitle("现金红包");

        System.out.println(JSON.toJSONString(gainPassTemplateService.gainPassTemplate(
                new GainPassTemplateRequest(162791L,passTemplate)
        )));
    }
}
