package org.march.passbook.consumer.service;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.march.passbook.consumer.vo.Pass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <h1>用户优惠券相关服务测试</h1>
 *
 * @author March
 * @date 2020/6/27
 */
@SpringBootTest
public class UserPassServiceTest extends AbstractServiceTest{

    @Autowired
    private IUserPassService userPassService;

    /**
     * <h2>获取用户的所有优惠券信息</h2>
     * @return void
     */
    @Test
    public void getUserAllPassInfoTest() throws Exception {
        System.out.println(JSON.toJSONString(
                userPassService.getUserAllPassInfo(116299L)
        ));
    }

    /**
     * <h2>获取用户有效优惠券信息</h2>
     * @return void
     */
    @Test
    public void getUserPassInfoTest() throws Exception {
        System.out.println(JSON.toJSONString(
                userPassService.getUserPassInfo(151800L)
        ));
    }

    /**
     * <h2>获取用户已使用优惠券信息</h2>
     * @return void
     */
    @Test
    public void getUserUsedPassInfoTest() throws Exception {
        System.out.println(JSON.toJSONString(
                userPassService.getUserUsedPassInfo(151800L)
        ));
    }

    /**
     * <h2>用户使用优惠券信息</h2>
     * @return void
     */
    @Test
    public void userUsePassTest() throws Exception {
        Pass pass = new Pass();
        pass.setPassTemplateId("6e1b9c2b41532b323cb1bc8e4c4a130c");
        pass.setUserId(151800L);
        System.out.println(JSON.toJSONString(
                userPassService.userUsePass(pass)
        ));
    }

}
