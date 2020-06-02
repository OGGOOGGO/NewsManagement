package com.zzhujing.news.management.controller;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zzhujing.news.management.core.auth.Auth;
import com.zzhujing.news.management.core.auth.Role;
import com.zzhujing.news.management.core.exception.SystemSimpleException;
import com.zzhujing.news.management.core.result.CommonResult;
import com.zzhujing.news.management.core.result.MyPage;
import com.zzhujing.news.management.domain.User;
import com.zzhujing.news.management.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author hujing
 * @since 2020-06-02
 */
@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Auth
public class UserController {

    private final UserService userService;

    /**
     * 不需要传递角色，默认为新闻发布者
     */
    @PostMapping("/saveOrUpdate")
    public CommonResult<Void> saveOrUpdateUser(@RequestBody @Valid User user) {
        int count = userService.count(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()));
        if (count > 1) throw new SystemSimpleException("用户名已存在");
        user.setPassword(SecureUtil.md5(user.getPassword()));
        user.setRole(Role.PUBLISHER.name());
        userService.saveOrUpdate(user);
        return CommonResult.success();
    }


    @DeleteMapping("/del/{id}")
    public CommonResult<Void> delUser(@PathVariable Integer id) {
        userService.removeById(id);
        return CommonResult.success();
    }

    @GetMapping("/list/{index}/{size}")
    public CommonResult<MyPage<User>> findPublisherList(@PathVariable long index, @PathVariable long size) {
        MyPage<User> pageResult = MyPage.<User>of(index, size);
        return CommonResult.success(pageResult.buildResultIdentity(userService.page(pageResult.getQueryPage(), Wrappers.<User>lambdaQuery().eq(User::getRole, Role.PUBLISHER.name()))));
    }
}
