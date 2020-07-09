package org.march.passbook.merchants;

import org.march.passbook.merchants.security.AuthCheckInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MerchantsApplication implements WebMvcConfigurer {


    public static void main(String[] args) {
        SpringApplication.run(MerchantsApplication.class, args);
    }

    /** 配置拦截的请求路径 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthCheckInterceptor())
                .addPathPatterns("/merchants/**");
    }
}
