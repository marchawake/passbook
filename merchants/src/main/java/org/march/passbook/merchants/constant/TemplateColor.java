package org.march.passbook.merchants.constant;

/**
 * 优惠券背景颜色常量枚举
 *
 * @author March
 * @date 2020/6/17
 */
public enum TemplateColor {

    /** 红色 */
    RED(1, "red"),

    /** 绿色 */
    GREEN(2, "green"),

    /** 蓝色 */
    BLUE(3, "blue");


    /** 颜色代码 */
    private Integer code;

    /** 颜色 */
    private String color;

    TemplateColor(Integer code, String color) {
        this.code = code;
        this.color = color;
    }

    /** 获取优惠券背景颜色代码 */
    public Integer getCode() {
        return code;
    }

    /** 获取优惠券背景颜色值 */
    public String getColor() {
        return color;
    }
}
