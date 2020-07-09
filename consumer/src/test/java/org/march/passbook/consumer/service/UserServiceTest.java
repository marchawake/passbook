package org.march.passbook.consumer.service;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.march.passbook.consumer.vo.Response;
import org.march.passbook.consumer.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceTest {

    @Autowired
    IUserService userService;

    @Test
    void createUser() throws Exception {
        User user = new User();

        user.setBaseInfo(new User.BaseInfo("肖锦光", 22,"男"));
        user.setOtherInfo(new User.OtherInfo("13692580376", "广东茂名"));

        Response user1 = userService.createUser(user);

        /** {"data":{
         * "baseInfo":{"age":22,"name":"march","sex":"男"},
         * "id":148794,
         * "otherInfo":{"address":"广州白云区同和榕树头101号","phone":"13692580376"}}}*/
        System.out.println(JSON.toJSONString(user1));

    }
}