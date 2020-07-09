package org.march.passbook.merchants.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 定义通用响应对象
 *
 * @author March
 * @date 2020/6/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    /** 错误代码 */
    private Integer errorCode;

    /** 错误信息 */
    private String errorMsg;

    /** 响应数据对象 */
    private Object data;

    /**
     * 正确的响应构造函数
     * 传入响应数据对象
     * @param data {@link Object}
     */
    public Response(Object data) {
        this.data = data;
    }

}
