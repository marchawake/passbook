package org.march.passbook.consumer.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * <h1>获取库存服务测试</h1>
 *
 * @author March
 * @date 2020/6/26
 */
@SpringBootTest
public class InventoryServiceTest extends AbstractServiceTest {

    @Autowired
    private IInventoryService inventoryService;

    @Test
    public void testGetInventoryInfo() throws Exception {

        System.out.println(JSON.toJSONString(
                inventoryService.getInventoryInfo(288157L),
                SerializerFeature.DisableCircularReferenceDetect
        ));

    }
}
