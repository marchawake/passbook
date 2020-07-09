package org.march.passbook.consumer.service;

import org.march.passbook.consumer.vo.Pass;
import org.march.passbook.consumer.vo.Response;

/**
 * 用户优惠券相关功能接口定义
 *
 * @author March
 * @date 2020/6/19
 */
public interface IUserPassService {

    /**
     * <h2>获取用户个人优惠券信息，即我的优惠券功能实现</h2>
     * @param userId 用户 id
     * @return {@link Response}
     */
    Response getUserPassInfo(Long userId) throws Exception;

    /**
     * <h2>获取用户个人已使用的优惠券， 即已使用的优惠券功能实现</h2>
     * @param userId 用户 id
     * @return {@link Response}
     */
    Response getUserUsedPassInfo(Long userId) throws Exception;

    /**
     * <h2>获取用户所有优惠券</h2>
     * @param userId 用户 id
     * @return {@link Response}
     */
    Response getUserAllPassInfo(Long userId) throws Exception;

    /**
     * <h2>用户使用优惠券</h2>
     * @param pass {@link Pass}
     * @return {@link Response}
     */
    Response userUsePass(Pass pass);
}
