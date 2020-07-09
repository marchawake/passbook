package org.march.passbook.consumer.constant;

/**
 * 优惠券的状态
 *
 * @author March
 * @date 2020/6/18
 */
public enum  PassStatus {
    /** 未被使用 */
    UNUSED(1, "未被使用"),

    /** 已经使用的 */
    USED(2, "已经使用的"),

    /** 全部领取的 */
    ALL(3, "全部领取的");

    /** 状态码 */
    private Integer code;

    /** 状态描述信息 */
    private String desc;

    /**
     * 状态枚举构造函数
     * @param code {@link Integer}
     * @param desc {@link String}
     */
    PassStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 获取状态码
     * @return {@link Integer}
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 获取状态描述信息
     * @return {@link String}
     */
    public String getDesc() {
        return desc;
    }
}

