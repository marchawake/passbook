package org.march.passbook.consumer.service;

import org.march.passbook.consumer.vo.Response;
import org.march.passbook.consumer.vo.User;

/**
 * 用户服务：创建用户服务接口
 *
 * @author March
 * @date 2020/6/19
 */
public interface IUserService {

    /**
     * 创建用户业务
     * @param user
     * @return {@link Response}
     */
    Response createUser(User user) throws Exception;
}
