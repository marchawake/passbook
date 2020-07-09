package org.march.passbook.consumer.service.impl;

import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.march.passbook.consumer.constant.Constants;
import org.march.passbook.consumer.service.IUserService;
import org.march.passbook.consumer.vo.Response;
import org.march.passbook.consumer.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


/**
 * 创建用户服务实现
 *
 * @author March
 * @date 2020/6/19
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    /** HBase 客户端 */
    private final HbaseTemplate hbaseTemplate;

    /** Redis 客户端 */
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public UserServiceImpl(HbaseTemplate hbaseTemplate, StringRedisTemplate redisTemplate) {
        this.hbaseTemplate = hbaseTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Response createUser(User user) throws Exception{

        /** 列名常量转换为字节数组 */
        byte[] FAMILY_B = Constants.UserTable.FAMILY_B.getBytes();
        byte[] NAME = Constants.UserTable.NAME.getBytes();
        byte[] AGE = Constants.UserTable.AEG.getBytes();
        byte[] SEX = Constants.UserTable.SEX.getBytes();
        byte[] FAMILY_O = Constants.UserTable.FAMILY_O.getBytes();
        byte[] PHONE = Constants.UserTable.PHONE.getBytes();
        byte[] ADDRESS = Constants.UserTable.ADDRESS.getBytes();

        /** 获取当前 redis 缓存的用户总数 */
        Long prefix = redisTemplate.opsForValue().increment(Constants.USER_COUNT_REDIS_KEY, 1);
        Long userId = generateUserId(prefix);

        Put put = new Put(Bytes.toBytes(userId));

        /** 把数据存入 byte 字节数组 */
        put.addColumn(FAMILY_B, NAME, Bytes.toBytes(user.getBaseInfo().getName()));
        put.addColumn(FAMILY_B, AGE, Bytes.toBytes(user.getBaseInfo().getAge()));
        put.addColumn(FAMILY_B, SEX, Bytes.toBytes(user.getBaseInfo().getSex()));
        put.addColumn(FAMILY_O, PHONE, Bytes.toBytes(user.getOtherInfo().getPhone()));
        put.addColumn(FAMILY_O, ADDRESS, Bytes.toBytes(user.getOtherInfo().getAddress()));

        /** 存放到 HBase */
        hbaseTemplate.saveOrUpdate(Constants.UserTable.TABLE_NAME, put);

        user.setId(userId);

        return new Response(user);
    }

    /**
     * 生成用户 id
     * @param prefix 当前平台用户数
     * @return {@link Long}
     */
    private Long generateUserId(Long prefix) {

        String suffix = RandomStringUtils.randomNumeric(5);

        return Long.valueOf(prefix + suffix);
    }
}
