package org.march.passbook.merchants.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.march.passbook.merchants.service.MerchantsService;
import org.march.passbook.merchants.vo.CreateMerchantsRequest;
import org.march.passbook.merchants.vo.PassTemplate;
import org.march.passbook.merchants.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MerchantsServiceImplTest {
    @Autowired
    private MerchantsService merchantsService;

    @Test
    @Transactional
    void createMerchants() {
        CreateMerchantsRequest createMerchantsRequest = new CreateMerchantsRequest();
        createMerchantsRequest.setName("hello12389");
        createMerchantsRequest.setLogoUrl("http://marchawake.cn");
        createMerchantsRequest.setBusinessLicenseUrl("http://marchawake.cn");
        createMerchantsRequest.setPhone("13692580376");
        createMerchantsRequest.setAddress("广州白云区同和榕树头101号");
        Response response = merchantsService.createMerchants(createMerchantsRequest);
        String s = JSON.toJSONString(response);
        System.out.println(s);
    }

    @Test
    void buildMerchantsById() {
        Response response = merchantsService.buildMerchantsById(100);
        String s = JSON.toJSONString(response);
        System.out.println(s);
    }

    @Test
    void dropPassTemplate() {
        PassTemplate passTemplate = new PassTemplate();
        passTemplate.setMerchantsId(1);
        passTemplate.setTitle("你好nihao");
        passTemplate.setSummary("HelloWorld");
        passTemplate.setDesc("无所dsgsgs不能");
        passTemplate.setHasToken(true);
        passTemplate.setLimit(10000L);
        passTemplate.setBackground(2);
        passTemplate.setStart(new Date());
        passTemplate.setEnd(DateUtils.addDays(new Date(),5));
        Response response = merchantsService.dropPassTemplate(passTemplate);
        String s = JSON.toJSONString(response);
        System.out.println(s);
    }
}