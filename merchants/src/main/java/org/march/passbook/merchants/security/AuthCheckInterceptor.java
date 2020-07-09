package org.march.passbook.merchants.security;

import com.alibaba.druid.util.StringUtils;
import org.march.passbook.merchants.constant.Constant;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 商户身份认证检查拦截器
 *
 * @author March
 * @date 2020/6/17
 */
public class AuthCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /** 获取请求头携带的令牌 */
        String token = request.getHeader(Constant.TOKEN_STRING);

        /** 令牌校验 */
        if (StringUtils.isEmpty(token)) {
            throw new Exception("Header 中缺少 " + Constant.TOKEN_STRING + "!");
        }
        if (!token.equals(Constant.TOKEN)) {
            throw new Exception("Header 中的 " + Constant.TOKEN_STRING + "错误!");
        }
        /** 存放当前线程令牌到ThreadLocal */
        AccessContext.setToken(token);

        /** 放行 */
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        /** 清除当前线程令牌 */
        AccessContext.clearAccessKey();
    }
}
