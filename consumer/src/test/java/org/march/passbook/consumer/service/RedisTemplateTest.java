package org.march.passbook.consumer.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Redis 客户端测试
 *
 * @author March
 * @date 2020/6/20
 */

@SpringBootTest
public class RedisTemplateTest {

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Test
    public void testRedisTemplate() {
        // redis flashAll(慎用)
        redisTemplate.execute((RedisCallback<Object>) connection ->
        {
            connection.flushAll();
            return null;
        });
        String march = redisTemplate.opsForValue().get("march");
        System.out.println(march);
        redisTemplate.opsForValue().set("march", "helloRedis");

        march = redisTemplate.opsForValue().get("march");
        System.out.println(march);
    }
}
