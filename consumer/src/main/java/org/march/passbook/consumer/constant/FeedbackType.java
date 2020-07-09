package org.march.passbook.consumer.constant;

/**
 * 客户评论枚举定义
 *
 * @author March
 * @date 2020/6/18
 */
public enum  FeedbackType {
    /** 针对优惠券的评论 */
    PASS("pass", "针对优惠券的评论"),

    /** 针对卡包 APP 的评论 */
    APP("app", "针对卡包 APP 的评论");

    /** 评论类型代码 */
    private String code;

    /** 评论类型描述 */
    private  String desc;

    /**
     * 枚举构造函数
     * @param code {@link Integer}
     * @param desc {@link String}
     */
    FeedbackType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 获取评论类型代码
     * @return {@link Integer}
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取评论类型描述
     * @return {@link String}
     */
    public String getDesc() {
        return desc;
    }
}
