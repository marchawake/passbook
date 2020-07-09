package org.march.passbook.consumer.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 日志对象定义
 *
 * @author March
 * @date 2020/6/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogObject {

    /** 日志动作类型 */
    private String action;

    /** 用户 id */
    private Long userId;

    /** 当前时间戳 */
    private Long timestamp;

    /** 客户端 IP 地址 */
    private String remoteIp;

    /** 日志信息 */
    private Object logInfo = null;

}
