package org.march.passbook.consumer.service;

import org.march.passbook.consumer.vo.GainPassTemplateRequest;
import org.march.passbook.consumer.vo.Response;

/**
 * 用户领取优惠券功能服务接口
 *
 * @author March
 * @date 2020/6/19
 */
public interface IGainPassTemplateService {

    /**
     * <h2>用户领取优惠券信息</h2>
     * @param request {@link GainPassTemplateRequest}
     * @return {@link Response}
     */
    Response gainPassTemplate(GainPassTemplateRequest request) throws Exception;
}
