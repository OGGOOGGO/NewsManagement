create database  if not exists `news-management`;
create table tb_news
(
    id           int auto_increment
        primary key,
    title        varchar(200)                                  not null comment '标题',
    publisher_id int                                           not null comment '发布者id',
    content      text                                          null comment '新闻内容',
    create_time  timestamp           default CURRENT_TIMESTAMP not null,
    update_time  timestamp           default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    state        enum ('UP', 'DOWN') default 'DOWN'            not null comment '新闻发布/不发布'
)
    comment '新闻表';


create table tb_user
(
    id          int auto_increment
        primary key,
    username    varchar(50)                                                   not null comment '用户名',
    password    varchar(50)                                                   not null comment '密码',
    role        enum ('USER', 'PUBLISHER', 'ADMIN') default 'USER'            not null comment '角色，默认普通用户',
    create_time timestamp                           default CURRENT_TIMESTAMP not null comment '创建时间

',
    update_time timestamp                           default CURRENT_TIMESTAMP not null comment '修改时间'
)
    comment '用户表';