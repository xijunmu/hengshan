-- 用户信息表
create table if not exists user (
  user_id        int(11)       not null    auto_increment   comment '用户ID',
  user_name      varchar(255)  not null                     comment '用户账号',
  password       varchar(100)  default ''                   comment '密码',
  user_type      varchar(2)    default '00'                 comment '用户类型（00系统用户）',
  role_id        int(11)       default '0'                  comment '角色ID',
  email          varchar(100)  default ''                   comment '用户邮箱',
  phone_number   varchar(11)   default ''                   comment '手机号码',
  status         char(1)       default '0'                  comment '帐号状态（0正常 1停用）',
  login_ip       varchar(128)  default ''                   comment '最后登录IP',
  login_date     datetime                                   comment '最后登录时间',
  create_by      varchar(64)   default ''                   comment '创建者',
  create_time    datetime                                   comment '创建时间',
  update_time    datetime                                   comment '更新时间',
  remark         varchar(500)  default null                 comment '备注',
  primary key (user_id)
) engine=innodb auto_increment=1 comment = '用户信息表';

-- 初始化-用户信息表数据
insert into user values(1, 'admin','123456', '00', '0', '12345678@qq.com', '15888888888', '0', '127.0.0.1', null, 'admin', sysdate(), null, '管理员');
insert into user values(2, 'ry',   '123456', '00', '0', '12345678@qq.com', '15666666666', '0', '127.0.0.1', null, 'admin', sysdate(), null, '测试员');
