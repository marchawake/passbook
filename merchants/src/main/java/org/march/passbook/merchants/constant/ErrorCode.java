package org.march.passbook.merchants.constant;

/**
 * 错误码枚举定义
 *
 * @author March
 * @date 2020/6/17
 */
public enum  ErrorCode {

    /** 成功状态 状态码为0 错误描述为空 */
    SUCCESS(0, ""),

    /** 商户名重复 */
    DUPLICATE_NAME(1, "商户名重复"),

    /** 商户logo地址为空 */
    EMPTY_LOGO(2, "商户logo地址为空"),

    /** 商户营业执照为空 */
    EMPTY_BUSINESS_LICENSE(3, "商户营业执照为空"),

    /** 商户电话为空 */
    EMPTY_PHONE(4, "商户电话为空"),

    /** 商户地址为空 */
    EMPTY_ADDRESS(5, "商户地址为空"),

    /** 商户不存在 */
    MERCHANTS_NOT_EXISTS(6, "商户不存在");

    /** 错误代码 */
    private Integer errorCode;

    /** 错误代码对应的错误描述 */
    private String errorDesc;

    ErrorCode(Integer errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    /** 获取错误代码 */
    public Integer getErrorCode() {
        return errorCode;
    }

    /** 获取错误代码对应的描述信息 */
    public String getErrorDesc() {
        return errorDesc;
    }
}
