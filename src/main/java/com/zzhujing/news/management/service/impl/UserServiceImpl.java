package com.zzhujing.news.management.service.impl;

import com.zzhujing.news.management.domain.User;
import com.zzhujing.news.management.mapper.UserMapper;
import com.zzhujing.news.management.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author hujing
 * @since 2020-06-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
