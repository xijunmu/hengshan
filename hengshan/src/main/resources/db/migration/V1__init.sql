SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 用户表
create table if not exists user (
 `id` int not null auto_increment comment 'ID',
 `name` varchar(255) comment '用户名称',
 `password` varchar(100) comment '密码',
 `email` varchar(100) comment '邮箱',
 `phone` varchar(11) comment '手机号码',
 `status` char(1) default '1' comment '帐号状态：0停用 1正常',
 `create_time` datetime comment '创建时间',
 `update_time` datetime comment '更新时间',
 primary key (id)
) engine=innodb auto_increment=1 comment = '用户表';

-- 角色表
create table if not exists role (
 `id` int not null auto_increment comment 'ID',
 `name` varchar(255) comment '角色名称',
 `desc` varchar(255) comment '备注信息',
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
insert into user values(1, 'admin', '1E95272D3B47AB50BC5108C9A7200AB1', '12345678@qq.com', '15888888888', '0', sysdate(), null);
insert into user values(2, 'test', '1E95272D3B47AB50BC5108C9A7200AB1', '12345678@qq.com', '15666666666', '0', sysdate(), null);
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
 `create_by` int comment '文章创建者',
 `update_by` int comment '文章更新者',
 `create_time` datetime comment '创建时间',
 `update_time` datetime comment '更新时间',
 primary key (id)
) engine=innodb auto_increment=1 comment = '文章表';

insert into article values(1,"10分钟认识一下Selenium自动化工具","Selenium 是什么。\r\nSelenium 是一个Web自动化工具，能够模拟用户与浏览器交互，实现浏览器操作自动化。","Selenium是什么？",1,"https://img0.baidu.com/it/u=3120410141,1466872751&fm=253&fmt=auto&app=138&f=JPEG?w=801&h=500",100,10,1,1,1,0,1,1,sysdate(),sysdate());

SET FOREIGN_KEY_CHECKS = 1;