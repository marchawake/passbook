package org.march.passbook.merchants.service;

import org.march.passbook.merchants.vo.CreateMerchantsRequest;
import org.march.passbook.merchants.vo.PassTemplate;
import org.march.passbook.merchants.vo.Response;

/**
 * 定义商户服务接口
 *
 * @author March
 * @date 2020/6/17
 */
public interface MerchantsService {

    /**
     * 创建商户服务
     * @param request {@link CreateMerchantsRequest}
     * @return {@link Response}
     */
    Response createMerchants(CreateMerchantsRequest request);

    /**
     * 根据商户 id 构建商户信息服务
     * @param id {@link Integer}
     * @return {@link Response}
     */
    Response buildMerchantsById(Integer id);

    /**
     * 商户投放优惠券服务
     * @param passTemplate
     * @return org.march.passbook.merchants.vo.Response
     */
    Response dropPassTemplate(PassTemplate passTemplate);
}
