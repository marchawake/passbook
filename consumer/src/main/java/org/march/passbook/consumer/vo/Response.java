package org.march.passbook.consumer.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Controller 统一响应对象
 *
 * @author March
 * @date 2020/6/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    /** 错误代码 正确为零 */
    private Integer code = 0;

    /** 错误描述信息 正确状态为空字符串 "" */
    private String message = "";

    /** 返回值对象 */
    private Object data;

    /** 正确状态的构造函数 */
    public Response(Object data) {
        this.data = data;
    }

    /**
     * 空响应
     * @return {@link Response}
     */
    public static Response success() {
        return new Response();
    }

    /**
     * 错误响应
     * @param errorMsg 错误描述信息
     * @return org.march.passbook.consumer.vo.Response
     */
    public static Response failure(String errorMsg) {
        return new Response(-1,errorMsg,null);
    }
}
