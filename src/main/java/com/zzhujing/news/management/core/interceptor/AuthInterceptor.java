package com.zzhujing.news.management.core.interceptor;

import cn.hutool.core.util.ArrayUtil;
import com.zzhujing.news.management.core.auth.Auth;
import com.zzhujing.news.management.core.auth.Role;
import com.zzhujing.news.management.core.exception.SystemSimpleException;
import com.zzhujing.news.management.domain.User;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @auther hujing
 * @date Create in 2020/6/2
 * Copyright 本内容仅限于杭州大希地有限公司内部传阅，禁止外泄以及用于其他的商业目的
 **/
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String role = Optional.ofNullable(request.getSession())
                .map(s -> ((User) s.getAttribute("user")))
                .map(User::getRole)
                .orElseThrow(() -> new SystemSimpleException("session获取用户角色失败！"));
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            Class<?> clazz = hm.getBeanType();
            //先从方法上获取
            Role[] roles;
            Auth auth = hm.getMethod().getAnnotation(Auth.class);
            if (auth != null) {
                roles = auth.value();
            } else {
                Auth authWithClass = clazz.getAnnotation(Auth.class);
                if (authWithClass != null) {
                    roles = authWithClass.value();
                } else {
                    return true;
                }
            }
            if (!ArrayUtil.contains(roles, Role.valueOf(role))) {
                throw new SystemSimpleException("无权限操作");
            }
        }
        return true;
    }
}
