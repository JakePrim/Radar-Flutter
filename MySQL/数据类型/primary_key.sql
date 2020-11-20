CREATE TABLE IF  NOT EXISTS test_primary_key(
  id INT UNSIGNED PRIMARY KEY,-- 设置ID为主键
  username VARCHAR(20)
);
INSERT test_primary_key(id,username) VALUES(1,'PRIM');
-- 测试是否可以插入重复的主键
INSERT test_primary_key(id,username) VALUES(1,'PRIM1');-- 报错 Duplicate entry '1' for key 'PRIMARY'
-- 测试是否主键可以为空
INSERT test_primary_key(username) VALUES('KING');-- 报错 Field 'id' doesn't have a default value

-- 设置主键的其他方式
CREATE TABLE IF NOT EXISTS test_pri_key1(
  id INT UNSIGNED KEY,
  name VARCHAR(20)
);
DESC test_pri_key1;

CREATE TABLE IF NOT EXISTS test_pri_key2(
  id INT UNSIGNED,
  name VARCHAR(20),
  PRIMARY KEY(id)
);
DESC test_pri_key2;

-- 测试主键一个表是否只能设置一个 : 一个表中只能有一个主键: 否则会报错Multiple primary key defined
CREATE TABLE IF NOT EXISTS test_pri_key3(
  id INT UNSIGNED PRIMARY KEY,
  cId INT UNSIGNED PRIMARY KEY
);

-- 复合主键 可以使用多个字段来作为主键
CREATE TABLE IF NOT EXISTS test_pri_key4(
  id INT UNSIGNED,
  cId VARCHAR(20),
  name VARCHAR(20),
  PRIMARY KEY(id,cId)
);
-- 注意:在插入值的时候id 和 cId完全相等才会主键重复 如下
INSERT test_pri_key4(id,cId,name) VALUES(1,'a','JAKE');-- 主键就是 1-a
INSERT test_pri_key4(id,cId,name) VALUES(1,'b','JAKE');-- 主键就是 1-b
INSERT test_pri_key4(id,cId,name) VALUES(2,'a','JAKE');-- 主键就是 2-a

INSERT test_pri_key4(id,cId,name) VALUES(1,'a','JAKE1');-- 主键就是 1-a 这样插入才会主键重复

-- 测试自增约束
CREATE TABLE IF NOT EXISTS test_auto_increment(
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(20)
);
INSERT test_auto_increment(name) VALUES('A');
INSERT test_auto_increment(name) VALUES('B');
INSERT test_auto_increment(id,name) VALUES(10,'B');
INSERT test_auto_increment(id,name) VALUES(NULL,'B');
INSERT test_auto_increment(id,name) VALUES(DEFAULT,'B');
INSERT test_auto_increment(id,name) VALUES('','B');

CREATE TABLE IF NOT EXISTS test_not_null(
  a VARCHAR(10),
  b VARCHAR(10) NOT NULL
);
-- 测试插入
INSERT test_not_null(a,b) VALUES('',''); -- 正确

INSERT test_not_null(a,b) VALUES(NULL,NULL);--  Column 'b' cannot be null
INSERT test_not_null(a,b) VALUES(NULL,'abc');-- 正确
INSERT test_not_null(a) VALUES('TEST'); -- Field 'b' doesn't have a default value

-- 测试DEFAULT
CREATE TABLE test_default(
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(20) NOT NULL,
  age TINYINT UNSIGNED DEFAULT 18,
  email VARCHAR(50) NOT NULL DEFAULT '123465@qq.com'
);

INSERT test_default(name) VALUES('A');
--
INSERT test_default(name,age,email) VALUES('B',27,'9283923@qq.com');
INSERT test_default(name,age,email) VALUES('C',NULL,'9283923@qq.com');
INSERT test_default(name,age,email) VALUES('C',NULL,NULL); -- Column 'email' cannot be null
INSERT test_default(name,age,email) VALUES('C',NULL,DEFAULT);

CREATE TABLE test_default1(
  id INT UNSIGNED AUTO_INCREMENT KEY,
  sex ENUM('a','b','c') NOT NULL DEFAULT 'a'
);

-- UNIQUE KEY 唯一索引
CREATE TABLE test_unique(
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(20) NOT NULL UNIQUE KEY,
  email VARCHAR(50) UNIQUE,
  card CHAR(18) UNIQUE
);

INSERT test_unique(name,email,card) VALUES('A','12@qq.com','123123');
INSERT test_unique(name,email,card) VALUES('A','112@qq.com','1231231');
INSERT test_unique(name,email,card) VALUES('B',NULL,NULL);
INSERT test_unique(name,email,card) VALUES('C',NULL,NULL);-- UNIQUE 约束对NULL不算重复
