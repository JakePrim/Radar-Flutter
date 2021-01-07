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

-- 表中插入一条数据 INSERT [INFO] tab_name(id,...) VALUES(1,...);
INSERT user(id,username,password,email,age,card,tel,salary,married,addr,sex) VALUES(
  1,'PRIM','prim','1760473022@qq.com',27,'111111111111111111','12345678901',88888.68,0,'北京','男'
);

INSERT user(id,username,password,email,age,card,tel,salary,married,addr,sex) VALUES(
  -1,'PRIM','prim1','1760473022@qq.com',110,'111111111111111111','12345678901',28888.68,0,'北京','女'
);
-- 查询表中的所有记录
SELECT * FROM user;

-- 测试整型
CREATE TABLE IF NOT EXISTS test_int(
  a TINYINT,b SMALLINT,c MEDIUMINT,d INT,e BIGINT
);

-- 插入数据
INSERT test_int(a) VALUES(-128);
-- Out of range value for column 'a' at row 1 超过了最大范围
INSERT test_int(a) VALUES(-129);
INSERT test_int(a) VALUES(128);

-- 测试无符号 unsigned
CREATE TABLE IF NOT EXISTS test_unsigned(
  a TINYINT,
  b TINYINT UNSIGNED
);
--错误超出范围 Out of range value for column 'b' at row
INSERT test_unsigned(a,b) VALUES(-12,-12);
INSERT test_unsigned(a,b) VALUES(0,256);
-- 正确
INSERT test_unsigned(a,b) VALUES(0,0);
INSERT test_unsigned(a,b) VALUES(0,255);

--测试零填充 zerofill
CREATE TABLE IF NOT EXISTS test_int1(
  a TINYINT ZEROFILL,b SMALLINT ZEROFILL,c MEDIUMINT ZEROFILL,d INT ZEROFILL,e BIGINT ZEROFILL
);

-- 测试设置长度
INSERT test_int1(a,b,c,d,e) VALUES(1,1,1,1,1);

CREATE TABLE IF NOT EXISTS test_int2(
  a TINYINT(2),b SMALLINT(2)
);

INSERT test_int2(a,b) VALUES(123,3456);

SELECT * FROM test_int2;
