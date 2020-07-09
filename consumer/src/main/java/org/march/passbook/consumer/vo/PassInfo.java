package org.march.passbook.consumer.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.march.passbook.consumer.entity.Merchants;

/**
 * 用户领取的优惠券信息
 *
 * @author March
 * @date 2020/6/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassInfo {

    /** 优惠券 */
    private Pass pass;

    /** 优惠券模板 */
    private PassTemplate passTemplate;

    /** 优惠券对应的商户 */
    private Merchants merchants;
}
