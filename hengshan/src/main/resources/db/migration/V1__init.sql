set names utf8mb4;
set foreign_key_checks = 0;

-- 用户表
create table if not exists user (
 `id` bigint not null auto_increment comment 'ID',
 `username` varchar(255) comment '用户名',
 `password` varchar(128) comment '密码',
 `avatar` varchar(255) comment '头像',
 `email` varchar(100) comment '邮箱',
 `sex` char(1) comment '性别：0女 1男',
 `phone` varchar(11) comment '手机号码',
 `type` char(1) default '0' comment '用户类型：0普通用户 1管理员',
 `status` char(1) default '1' comment '帐号状态：0停用 1正常',
 `create_time` datetime comment '创建时间',
 `update_time` datetime comment '更新时间',
 primary key (id)
) engine=innodb auto_increment=1 comment = '用户表';

-- 角色表
create table if not exists role (
 `id` int not null auto_increment comment 'ID',
 `name` varchar(255) comment '角色名称',
 `description` varchar(255) comment '描述',
 `create_time` datetime comment '创建时间',
 `update_time` datetime comment '更新时间',
 primary key (id)
) engine=innodb auto_increment=1 comment = '角色表';

-- 权限表
create table if not exists menu (
 `id` int comment 'ID',
 `name` varchar(255) not null comment '权限名',
 `parent_id` int default '0' comment '父级ID',
 `url` varchar(255) default '#' comment '访问路径或接口',
 `order` int comment '排序',
 `type` char(1) default '1' comment '权限类型：1菜单 2按钮',
 primary key (id)
) engine=innodb comment = '权限表';

-- 用户角色表
create table if not exists user_role (
 `user_id` int not null comment '用户ID',
 `role_id` int not null comment '角色ID'
) engine=innodb comment = '用户角色表';

-- 角色权限表
create table if not exists role_menu (
 `role_id` int not null comment '角色ID',
 `menu_id` int not null comment '权限ID'
) engine=innodb comment = '角色权限表';

-- 初始化 用户-角色-权限 数据
insert into user values(1, 'admin', '123','https://img2.woyaogexing.com/2023/12/04/252170494265e17853195349efbf99a9.jpg','12345678@qq.com', 1,'15888888888', 1,1, sysdate(), null);
insert into user values(2, 'test', '123', 'https://img2.woyaogexing.com/2022/11/07/81617bf06d5295a0!400x400.jpg','12345678@qq.com', 0, '15666666666', 0, 1,sysdate(), null);
insert into user values(3, 'guest', '123', 'https://img2.woyaogexing.com/2022/01/27/181386851e34466498c787838acc17d1!400x400.jpeg','12345678@qq.com',1, '15699999999', 0, 1,sysdate(), null);
insert into role values(1, '超级管理员', '全部权限', sysdate(), null);
insert into role values(2, '普通用户', '部分权限', sysdate(), null);
insert into user_role values(1, 1);
insert into user_role values(2, 2);
insert into menu values(1, '系统管理', 0, '#', 1, '1');
insert into menu values(2, '系统监控', 0, '#', 2, '1');
insert into menu values(3, '系统工具', 0, '#', 3, '1');
insert into menu values(100, '用户管理', 1, '#', 1, '1');
insert into menu values(101, '角色管理', 1, '#', 2, '1');
insert into menu values(102, '菜单管理', 1, '#', 3, '1');
insert into menu values(200, '服务监控', 2, '#', 1, '1');
insert into menu values(201, '系统监控', 2, '#', 2, '1');
insert into role_menu values(1, 1);
insert into role_menu values(1, 2);
insert into role_menu values(1, 3);
insert into role_menu values(1, 100);
insert into role_menu values(1, 101);
insert into role_menu values(1, 102);
insert into role_menu values(1, 200);
insert into role_menu values(1, 201);
insert into role_menu values(2, 1);
insert into role_menu values(2, 2);
insert into role_menu values(2, 100);
insert into role_menu values(2, 101);
insert into role_menu values(2, 102);

-- 文章表
create table if not exists article (
 `id` int not null auto_increment comment 'ID',
 `title` varchar(255) comment '标题',
 `content` longtext comment '内容',
 `summary` varchar(1024) comment '摘要',
 `category_id` int comment '所属分类id',
 `thumbnail` varchar(255) comment '缩略图',
 `view_count` int default '0' comment '访问量',
 `comment_count` int default '0' comment '评论数',
 `is_top` char(1) default '0' comment '是否置顶：0否 1是',
 `status` char(1) default '1' comment '状态：0草稿 1已发布',
 `is_comment` char(1) default '1' comment '是否允许评论：0否 1是',
 `del_flag` char(1) default '0' comment '删除标志：0正常 1删除',
 `user_id` bigint comment '文章作者',
 `create_time` datetime comment '创建时间',
 `update_time` datetime comment '更新时间',
 primary key (id)
) engine=innodb auto_increment=1 comment = '文章表';
insert into article values(1,'测试文章1','Selenium 是什么。\r\nSelenium 是一个Web自动化工具，能够模拟用户与浏览器交互，实现浏览器操作自动化。','Selenium是什么？',1,'https://img0.baidu.com/it/u=3120410141,1466872751&fm=253&fmt=auto&app=138&f=JPEG?w=801&h=500',100,10,1,1,1,0,1,sysdate(),sysdate());
insert into article values(2,'测试文章2','测试文章2内容','测试文章2摘要',2,'https://img0.baidu.com/it/u=3120410141,1466872751&fm=253&fmt=auto&app=138&f=JPEG?w=801&h=500',100,10,1,1,1,0,1,sysdate(),sysdate());

-- 文章分类表
create table if not exists category(
 `id` int not null auto_increment comment 'ID',
 `name` varchar(128) comment '分类名称',
 `description` varchar(512) comment '描述',
 `create_time` datetime comment '创建时间',
 `update_time` datetime comment '更新时间',
 primary key (id)
) engine = innodb auto_increment=1 comment = '文章分类表';
insert into category values(1,'java','后端语言',sysdate(),sysdate());
insert into category values(2,'javascript','前端编程语言',sysdate(),sysdate());
insert into category values(3,'vue','后端框架',sysdate(),sysdate());

-- 友链表
create table if not exists bro_link(
 `id` int not null auto_increment comment 'ID',
 `name` varchar(128) comment '名称',
 `description` varchar(512) comment '描述',
 `url` varchar(128) comment '地址',
 `logo` varchar(512) comment 'logo',
 `create_time` datetime comment '创建时间',
 primary key (id)
) engine = innodb auto_increment=1 comment = '友链表';
insert into bro_link values(1,'百度','百度搜索','https://www.baidu.com','https://www.baidu.com/img/flexible/logo/pc/result.png',sysdate());

create table if not exists comment (
 `id` bigint not null auto_increment comment '评论id',
 `article_id` int comment '文章id',
 `type` char(1) default '1' comment '类型：1文章 2友链',
 `root_id` bigint comment '根评论id',
 `reply_id` bigint comment '被回复的评论id',
 `content` text comment '评论内容',
 `reply_user_id` bigint comment '被回复的用户id',
 `user_id` bigint comment '当前评论用户id',
 `create_time` datetime comment '评论时间',
 `update_time` datetime comment '更新时间',
 primary key (`id`)
) engine = innodb auto_increment=1 comment = '评论表';
insert into comment values(1,1,'1',null,null,'写的不错，作者辛苦了',1,2,sysdate(),sysdate());
insert into comment values(2,1,'1',null,null,'给作者赞一个',1,1,sysdate(),sysdate());
insert into comment values(3,1,'1',1,1,'谢谢支持，up主会继续努力的！',2,1,sysdate(),sysdate());
insert into comment values(4,1,'1',1,3,'就这还写的不错？',2,3,sysdate(),sysdate());
insert into comment values(5,1,'1',1,4,'来来来，你牛逼你来写',3,2,sysdate(),sysdate());
insert into comment values(6,2,'1',null,null,'作者牛逼',1,2,sysdate(),sysdate());

set foreign_key_checks = 1;