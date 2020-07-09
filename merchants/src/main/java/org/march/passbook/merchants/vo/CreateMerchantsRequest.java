package org.march.passbook.merchants.vo;

import com.alibaba.druid.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.march.passbook.merchants.constant.ErrorCode;
import org.march.passbook.merchants.dao.MerchantsDao;
import org.march.passbook.merchants.entity.Merchants;

/**
 * 定义创建商户请求对象
 *
 * @author March
 * @date 2020/6/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMerchantsRequest {

    /** 商户名称*/
    private String name;

    /** 商户名称*/
    private String logoUrl;

    /** 商户名称*/
    private String businessLicenseUrl;

    /** 商户名称*/
    private String phone;

    /** 商户名称*/
    private String address;

    /**
     * 校验请求的有效性
     * @param merchantsDao {@link MerchantsDao}
     * @return {@link ErrorCode}
     */
    public ErrorCode validate(MerchantsDao merchantsDao) {
        if (null != merchantsDao.findByName(this.name)) {
            return ErrorCode.DUPLICATE_NAME;
        }

        if (StringUtils.isEmpty(this.name)) {
            return ErrorCode.EMPTY_LOGO;
        }

        if (StringUtils.isEmpty(this.businessLicenseUrl)) {
            return ErrorCode.EMPTY_BUSINESS_LICENSE;
        }

        if (StringUtils.isEmpty(this.phone)) {
            return ErrorCode.EMPTY_PHONE;
        }

        if (StringUtils.isEmpty(this.address)) {
            return ErrorCode.EMPTY_ADDRESS;
        }

        return ErrorCode.SUCCESS;
    }

    /**
     * 把创建商户请求对象转换为商户对象
     * @return {@link Merchants}
     */
    public Merchants toMerchants() {
        Merchants merchants = new Merchants();
        merchants.setName(this.name);
        merchants.setLogoUrl(this.logoUrl);
        merchants.setBusinessLicenseUrl(this.businessLicenseUrl);
        merchants.setPhone(this.phone);
        merchants.setAddress(this.address);

        return merchants == null ? null : merchants;
    }

}
