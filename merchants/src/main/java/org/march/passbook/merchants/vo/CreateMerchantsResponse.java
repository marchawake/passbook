package org.march.passbook.merchants.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 定义创建商户响应对象
 *
 * @author March
 * @date 2020/6/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMerchantsResponse {
    /** 商户id 不存在返回-1 */
    private Integer id = -1;
}
