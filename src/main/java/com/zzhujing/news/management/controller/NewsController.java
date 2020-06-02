package com.zzhujing.news.management.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.zzhujing.news.management.core.auth.Auth;
import com.zzhujing.news.management.core.auth.Role;
import com.zzhujing.news.management.core.exception.SystemSimpleException;
import com.zzhujing.news.management.core.result.CommonResult;
import com.zzhujing.news.management.core.result.MyPage;
import com.zzhujing.news.management.domain.News;
import com.zzhujing.news.management.domain.User;
import com.zzhujing.news.management.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

import static com.zzhujing.news.management.core.auth.Role.*;

/**
 * <p>
 * 新闻表 前端控制器
 * </p>
 *
 * @author hujing
 * @since 2020-06-02
 */
@RestController
@RequestMapping("/news")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Auth(Role.PUBLISHER)
public class NewsController {

    private final NewsService newsService;
    private final HttpSession session;

    /**
     * 添加/修改新闻
     * /发布/下架 -> 传递 id+状态字符串即可 发布 -> 'UP' 下架 -> 'DOWN'
     */
    @PostMapping("/saveOrUpdate")
    public CommonResult<Void> saveOrUpdate(@RequestBody @Valid News news) {
        if (news.getId() == null) {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                throw new SystemSimpleException("session不存在！");
            }
            news.setPublisherId(((User) session.getAttribute("user")).getId());
        }
        newsService.saveOrUpdate(news);
        return CommonResult.success();
    }


    /**
     * 通过不同的登录角色来查询新闻list列表
     *
     * @param index current page
     * @param size  page size
     * @param type  'UP' 上架 'DOWN' 下架
     * @return
     */
    @GetMapping("/list/{index}/{size}/{type}")
    public CommonResult<MyPage<News>> findListByRole(@PathVariable long index, @PathVariable long size, @PathVariable String type) {
        MyPage<News> pageResult = MyPage.of(index, size);
        User user = (User) session.getAttribute("user");
        LambdaQueryWrapper<News> queryWrapper = Wrappers.<News>lambdaQuery().eq(News::getState, type);
        if (user != null && StrUtil.equals(user.getRole(), PUBLISHER.name())) {
            queryWrapper.eq(News::getPublisherId, user.getId());
        }
        return CommonResult.success(pageResult.buildResultIdentity(newsService.page(new Page<>(index, size), queryWrapper)));
    }

    @DeleteMapping("/del/{id}")
    @Auth({ADMIN, PUBLISHER})
    public CommonResult<Void> delNews(@PathVariable Integer id) {
        newsService.removeById(id);
        return CommonResult.success();
    }
}
