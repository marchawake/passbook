package org.march.passbook.merchants.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.march.passbook.merchants.service.MerchantsService;
import org.march.passbook.merchants.vo.CreateMerchantsRequest;
import org.march.passbook.merchants.vo.PassTemplate;
import org.march.passbook.merchants.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 定义商户服务控制器
 *
 * @author March
 * @date 2020/6/17
 */
@RestController
@Slf4j
@RequestMapping("/merchants")
public class MerchantsController {

    private final MerchantsService service;

    @Autowired
    public MerchantsController(MerchantsService service) {
        this.service = service;
    }

    @ResponseBody
    @PostMapping("/create")
    public Response createMerchants(@RequestBody CreateMerchantsRequest request) {

        log.info("CreateMerchants:{}", JSON.toJSONString(request));
       return service.createMerchants(request);
    }

    @ResponseBody
    @GetMapping("/{id}")
    public Response buildMerchantsById(@PathVariable Integer id) {

        log.info("BuildMerchantsById:{}", JSON.toJSONString(id));
        return service.buildMerchantsById(id);
    }

    @ResponseBody
    @PostMapping("/drop")
    public Response dropPassTemplate(@RequestBody PassTemplate template) {

        log.info("DropPassTemplate:{}", JSON.toJSONString(template));
        return service.dropPassTemplate(template);
    }
}
