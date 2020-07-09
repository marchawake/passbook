package org.march.passbook.merchants.security;

/**
 * 使用ThreadLocal存储每个线程携带的令牌信息
 *
 * @author March
 * @date 2020/6/17
 */
public class AccessContext {

    /** 初始化ThreadLocal实例 */
    private static final ThreadLocal<String> token = new ThreadLocal<String>();

    /**
     * 获取当前线程的令牌
     * @return {@link String}
     */
    public static ThreadLocal<String> getToken() {
        return token;
    }

    /**
     * 设置当前线程的令牌
     * @param tokenStr
     * @return void
     */
    public static void setToken(String tokenStr) {
        token.set(tokenStr);
    }

    /**
     * 清除当前线程的令牌
     * @return void
     */
    public static void clearAccessKey() {
        token.remove();
    }
}
