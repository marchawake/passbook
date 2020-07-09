package org.march.passbook.consumer.log;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志生成器
 *
 * @author March
 * @date 2020/6/18
 */
@Slf4j
public class LogGenerator {

    /**
     * 生成日志
     * @param request 浏览器请求对象 {@link HttpServletRequest}
     * @param userId 用户 id {@link Long}
     * @param action 日志类型 {@link String}
     * @param info 日志信息 {@link Object}
     * @return void
     */
    public static void generateLog(HttpServletRequest request, Long userId, String action, Object info) {
        log.info(
                JSON.toJSONString(
                        new LogObject(action, userId, System.currentTimeMillis(), request.getRemoteAddr(), info)
                )
        );
    }
}
