-- 2020-11-01
-- 新增资料核查表
CREATE TABLE demo (
  id BIGINT(20) NOT NULL auto_increment COMMENT '主键ID',
  name varchar(30) default NULL COMMENT '姓名',
  sex varchar(2) default NULL,
  age int(11) default NULL COMMENT '年龄',
  birthday date default NULL COMMENT '生日',
  email varchar(50) default NULL COMMENT '邮箱',
  content varchar(1000) default NULL COMMENT '个人简介',
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 2020-11-25
-- 申请开票表添加字段
ALTER TABLE patfu_invoice ADD apply_person_name VARCHAR(50) COMMENT '申请人名字';