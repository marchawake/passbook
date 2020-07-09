package org.march.passbook.consumer.service;

import org.junit.Before;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 服务测试抽象基类
 *
 * @author March
 * @date 2020/6/26
 */
@SpringBootTest
public class AbstractServiceTest {

    Long userId;

    @Before
    public void init() {
        userId = 162791L;
    }
}
