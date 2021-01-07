-- 创建数据库
CREATE DATABASE IF NOT EXISTS db_user DEFAULT CHARACTER SET 'UTF8';

-- 打开数据库
USE db_user;

-- 创建用户表
CREATE TABLE IF NOT EXISTS user(
  id INT,
  username VARCHAR(20),
  password CHAR(30),
  email VARCHAR(50),
  age TINYINT,
  card CHAR(18),
  tel CHAR(11),
  salary float(8,2),
  married TINYINT(1),
  addr VARCHAR(100),
  sex ENUM('男','女','保密')
)ENGINE=INNODB CHARSET=UTF8;

-- 查看当前数据库的数据表
SHOW FULL TABLES FROM db_user;

-- 查看表的详细信息
SHOW CREATE TABLE user;

-- 三种方式查看指定数据表结构信息
DESC user;

DESCRIBE user;

SHOW COLUMNS FROM user;

-- 删除数据表
DROP TABLE IF EXISTS user;


CREATE TABLE IF NOT EXISTS `imooc_user`(
  `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
  `username` VARCHAR(20) NOT NULL UNIQUE KEY COMMENT '用户名',
  `password` CHAR(32) NOT NULL COMMENT '用户密码',
  `email` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户邮箱',
  `age` TINYINT NOT NULL DEFAULT 18 COMMENT '年龄',
  `tel` CHAR(11) NOT NULL UNIQUE COMMENT '手机号码',
  `card` CHAR(18) NOT NULL UNIQUE COMMENT '身份证号',
  `salary` FLOAT(8,2) NOT NULL COMMENT '薪资',
  `married` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '0未婚,1已婚',
  `addr` VARCHAR(50) NOT NULL DEFAULT '北京' COMMENT '地址',
  `sex` ENUM('男','女','保密') NOT NULL DEFAULT '保密' COMMENT '性别'
)ENGINE = INNODB DEFAULT CHARSET=UTF8;


-- 测试添加和删除字段
CREATE TABLE IF NOT EXISTS user1(
  id INT UNSIGNED AUTO_INCREMENT KEY
);
-- ALTER TABLE table_name ADD 字段名 字段属性 [完整性约束] [FIRST(新的字段添加到首位)|AFTER 字段名(新的字段在这个字段之后)]
-- ALTER TABLE table_name DROP [COLUMN] 字段名
-- ADD 和 DROP 可以复合使用

ALTER TABLE user1 ADD username VARCHAR(20);
ALTER TABLE user1 ADD password CHAR(32) NOT NULL;
ALTER TABLE user1 ADD email VARCHAR(50) NOT NULL UNIQUE AFTER username; -- 添加到username 之后
ALTER TABLE user1 ADD test TINYINT(1) NOT NULL DEFAULT 0 FIRST; -- 添加到首位

-- 删除字段
ALTER TABLE user1 DROP test;

-- 添加age addr 字段,删除email字段
ALTER TABLE user1 ADD age TINYINT UNSIGNED NOT NULL DEFAULT 18,
ADD addr VARCHAR(100) NOT NULL DEFAULT '北京',
DROP email;

-- 练习
CREATE TABLE IF NOT EXISTS user2(
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  proName VARCHAR(20) NOT NULL
);

ALTER TABLE user2
ADD price FLOAT(8,2) UNSIGNED NOT NULL DEFAULT 0,
ADD num INT UNSIGNED NOT NULL DEFAULT 100,
ADD test VARCHAR(50) NOT NULL FIRST,
ADD test1 CHAR(23) NOT NULL AFTER price;

ALTER TABLE user2
DROP test;

ALTER TABLE user2
ADD `desc` TEXT,-- 注意此处需要反引号 否则会报错
ADD isSale TINYINT(1) NOT NULL DEFAULT 0,
DROP test1;

-- 添加默认值
-- ALTER TABLE tbl_name ALTER 字段名 SET DEFAULT 默认值
-- 删除默认值
-- ALTER TABLE tbl_name ALTER 字段名 DROP DEFAULT
CREATE TABLE IF NOT EXISTS user3(
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  usename VARCHAR(20),
  age TINYINT UNSIGNED NOT NULL DEFAULT 18,
  email VARCHAR(50) NOT NULL
);
DESC user3;
-- 给emali添加默认值
ALTER TABLE user3
ALTER email SET DEFAULT '111@qq.com';
DESC user3;
-- 删除age 的默认值
ALTER  TABLE user3
ALTER age DROP DEFAULT;
DESC user3;

CREATE TABLE IF NOT EXISTS pro(
  count INT NOT NULL DEFAULT 0,
  addr VARCHAR(50) NOT NULL
);
ALTER TABLE pro
ALTER addr SET DEFAULT '北京';

ALTER TABLE pro
ALTER count DROP DEFAULT;

-- 修改字段的类型、字段属性、完整性约束条件 MODIFY
-- ALTER TABLE tbl_name MODIFY 字段名称 字段类型 [字段属性] [FIRST|AFTER 字段名称]
-- 修改字段名称、字段的类型、字段属性、完整性约束条件 CHANGE
-- ALTER TABLE tbl_name CHANGE 原字段名称 新字段名称 字段类型 字段属性 [FIRST|AFTER 字段名称]

-- 测试MODIFY
CREATE TABLE user4(
  id INT UNSIGNED AUTO_INCREMENT  KEY,
  username VARCHAR(5) NOT NULL UNIQUE,
  password CHAR(32) NOT NULL,
  email VARCHAR(10) NOT NULL
);

-- 改变用户名字段属性
ALTER TABLE user4
MODIFY username VARCHAR(50) NOT NULL;

-- 改变密码字段
ALTER TABLE user4
MODIFY password CHAR(40) NOT NULL;

-- email 改为50
ALTER TABLE user4
MODIFY email VARCHAR(50) NOT NULL;

-- 测试CHANGE 修改字段名称和属性
-- username 改为user
ALTER TABLE user4
CHANGE username uer VARCHAR(20) NOT NULL;
-- password 改为 pwd
ALTER TABLE user4
CHANGE password pwd CHAR(40) NOT NULL;

-- email 改为userEmail 100 '12@QQ.COM'
ALTER TABLE user4
CHANGE email userEmail VARCHAR(100) DEFAULT '12@qq.com';

-- 添加主键
-- ALTER TABLE tbl_name ADD PRIMARY KEY(字段名);
-- 删除主键
-- ALTER TABLE tbl_name DROP PRIMARY KEY; -- 一个表只有一个主键
CREATE TABLE user5(
  id INT UNSIGNED,
  username VARCHAR(20)
);
-- 给id添加主键
ALTER TABLE user5
ADD PRIMARY KEY(id);

-- 删除分主键
ALTER TABLE user5
DROP PRIMARY KEY;

-- 如果存在主键自增约束 在动态删除主键那么自增约束就不能成立了
-- 需要先去掉自增的属性AUTO_INCREMENT
CREATE TABLE user6(
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY
);
-- 这时候如果删除主键会出现错误,还记得之前讲的AUTO_INCREMENT 约束必须和索引配合使用
ALTER TABLE user6
DROP PRIMARY KEY;

-- 先去掉自增属性
ALTER TABLE user6
MODIFY id INT UNSIGNED;

-- 添加唯一索引
-- ALTER TABLE tbl_name ADD UNIQUE KEY|INDEX [index_name] (字段名称)
-- index_name 不写默认为字段名
-- 删除唯一索引
-- ALTER TABLE tbl_name DROP INDEX index_name;
-- index_name 为索引名称 一般我们在创建表添加的唯一索引名称就是字段名

CREATE TABLE user7(
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(20) NOT NULL UNIQUE,
  password CHAR(32) NOT NULL,
  email VARCHAR(40) NOT NULL UNIQUE
);

ALTER TABLE user7
DROP INDEX username;

ALTER TABLE user7
DROP INDEX email;

ALTER TABLE user7
ADD UNIQUE KEY(username);
-- 添加索引名称
ALTER TABLE user7
ADD UNIQUE KEY nui_email (email);

--修改表名
--ALTER TABLE tbl_name RENAME [TO|AS] new_tbl_name;
--RENAME TABLE tab_name TO new_tbl_name;

ALTER TABLE user7 RENAME TO user77;
ALTER TABLE user77 RENAME AS user7;

RENAME TABLE user7 TO user77;

-- MyISAM 存储引擎
-- 在Mysql 5.5 之前使用的是MyISAM 存储引擎,5.5之后默认使用的InnoDB存储引擎
-- 测试MyISAM
CREATE TABLE test_myisam(
  a INT UNSIGNED,
  b VARCHAR(10),
  c CHAR(10)
)ENGINE=MyISAM;
-- 会在磁盘中产生三个文件:
-- .frm表结构文件 .MYD 数据文件 .MYI 索引文件
-- 可以在建表的时候指定数据文件和索引文件的存储位置
-- 只有MyISAM表支持.
-- 通过DATA DIRECORY [=] 数据保存的绝对路径
-- 通过INDEX DIRECTORY [=] 索引文件保存的绝对路径

-- MyISAM 单表最大支持的数据量2的64次方条记录
-- 每个表最多可以建立64个索引
-- 如果是复合索引,每个复合索引最多包含16个列,索引值最大长度是100B
-- MyISAM 引擎的存储格式:定长(FIXED 静态) 动态(DYNAMIC) 压缩(COMPRESSED)


-- InnoDB 存储引擎
-- 5.5之后默认使用的InnoDB存储引擎,InnoDB 现在是主流的存储引擎
-- 设计遵循ACID模型:支持事务,具有从服务崩溃中恢复的能力,能够最大限度保护用户的数据能力
-- 原子性(Atomiocity)
-- 一致性(Consistency)
-- 隔离性(Isolation)
-- 持久性(Durability)
-- ACID 模型

-- 支持行级锁,可以提升多用户并发时的读写性能

-- 支持外键,保证数据的一致性和完整性

-- InnoDB 拥有自己独立的缓冲池,常用的数据和索引都在缓存中

-- 对于INSERT、UPDATE、DELETE操作,InnoDB会使用一种change buffering的机制来自动优化,还可以
-- 提供一致性的读,并且还能够缓存变更的数据,减少磁盘I/O,提高性能

-- InnoDB 表会产生哪些文件呢?
-- .frm 和 .ibd 两个文件.
-- .frm 是表结构文件.
-- .ibd 是表空间是数据和索引.

-- 所有的表都需要创建主键,最好配合AUTO_INCREMENT,也可以放到经常查询的列作为主键
