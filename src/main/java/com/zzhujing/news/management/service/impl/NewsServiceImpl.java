package com.zzhujing.news.management.service.impl;

import com.zzhujing.news.management.domain.News;
import com.zzhujing.news.management.mapper.NewsMapper;
import com.zzhujing.news.management.service.NewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 新闻表 服务实现类
 * </p>
 *
 * @author hujing
 * @since 2020-06-02
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

}
