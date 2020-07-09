package org.march.passbook.consumer.service;

import org.march.passbook.consumer.vo.Response;

/**
 * 获取库存信息，只返回客户未领取的优惠券，即优惠券库存功能实现接口定义
 *
 * @author March
 * @date 2020/6/19
 */
public interface IInventoryService {

    /**
     * <h2>获取优惠券库存信息</h2>
     * @param userId 用户 id
     * @return {@link Response}
     */
    Response getInventoryInfo(Long userId) throws Exception;

}
