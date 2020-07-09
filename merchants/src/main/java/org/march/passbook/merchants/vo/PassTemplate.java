package org.march.passbook.merchants.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.march.passbook.merchants.constant.ErrorCode;
import org.march.passbook.merchants.dao.MerchantsDao;
import java.util.Date;

/**
 * 定义优惠券模板对象
 *
 * @author March
 * @date 2020/6/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassTemplate {

    /** 所属商户id */
    private Integer merchantsId;

    /** 优惠券标题 */
    private String title;

    /** 优惠券摘要 */
    private String summary;

    /** 优惠券详细信息 */
    private String desc;

    /** 优惠券投放数量 */
    private Long limit;

    /** 优惠券是否携带 token, 用于商户核销 */
    /** token 存储在 Redis set 中，每次领取从 Redis 获取 */
    private Boolean hasToken;

    /** 优惠券背景颜色，默认为红色 */
    private Integer background = 1;

    /** 优惠券开始时间 */
    private Date start;

    /** 优惠券结束时间 */
    private Date end;

    /**
     * 校验商户信息的有效性
     * @param merchantsDao {@link MerchantsDao}
     * @return {@link ErrorCode}
     */
    public ErrorCode validate(MerchantsDao merchantsDao) {
        return merchantsDao.existsById(this.merchantsId) ? ErrorCode.SUCCESS : ErrorCode.MERCHANTS_NOT_EXISTS;
    }
}
