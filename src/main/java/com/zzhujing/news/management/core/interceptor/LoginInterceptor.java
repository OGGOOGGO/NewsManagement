package com.zzhujing.news.management.core.interceptor;

import cn.hutool.core.util.ArrayUtil;
import com.zzhujing.news.management.core.exception.SystemSimpleException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Stream;

import static com.zzhujing.news.management.core.cons.SysConstant.COOKIE_NAME;

/**
 * @auther hujing
 * @date Create in 2020/6/2
 **/
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (ArrayUtil.isEmpty(cookies)) {
            throw new SystemSimpleException("请登录！");
        }
        Stream.of(cookies)
                .filter(c -> c.getName().equals(COOKIE_NAME))
                .filter(c -> c.getValue().equals(request.getSession().getId()))
                .findAny().orElseThrow(() -> new SystemSimpleException("请登录！"));
        return true;
    }
}
