package com.zzhujing.news.management.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zzhujing.news.management.controller.req.LoginReq;
import com.zzhujing.news.management.core.exception.SystemSimpleException;
import com.zzhujing.news.management.core.result.CommonResult;
import com.zzhujing.news.management.domain.User;
import com.zzhujing.news.management.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.zzhujing.news.management.core.cons.SysConstant.COOKIE_NAME;

/**
 * @auther hujing
 * @date Create in 2020/6/2
 **/
@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginController {

    private final UserService userService;
    private final HttpSession session;
    private final HttpServletResponse response;

    @PostMapping("/login")
    public CommonResult<User> login(@RequestBody @Valid LoginReq req) {
        User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, req.getUsername()));
        if (user == null || !StrUtil.equals(SecureUtil.md5(req.getPassword()), user.getPassword())) {
            throw new SystemSimpleException("用户不存在或密码错误");
        }
        //存入session
        user.setPassword(null);
        session.setAttribute("user", user);
        session.setMaxInactiveInterval(7 * 24 * 60 * 3600);
        Cookie cookie = new Cookie(COOKIE_NAME, session.getId());
        response.addCookie(cookie);
        return CommonResult.success(user);
    }


    @DeleteMapping("/logout")
    public CommonResult<Void> logout() {
        session.invalidate();
        return CommonResult.success();
    }
}
