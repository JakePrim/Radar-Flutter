-- 添加记录
-- INSERT [INTO] table_name[(id,...)] {VALUE|VALUES}(VALUES...);
CREATE DATABASE IF NOT EXISTS king DEFAULT CHARACTER SET 'UTF8';
USE king;
CREATE TABLE IF NOT EXISTS user(
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '编号',
  username VARCHAR(20) NOT NULL UNIQUE COMMENT '用户名',
  age TINYINT UNSIGNED DEFAULT 18 COMMENT '年龄',
  email VARCHAR(50) NOT NULL UNIQUE DEFAULT 'user@qq.com' COMMENT '邮箱'
)ENGINE=INNODB CHARSET=UTF8;
-- 不指定字段名称 的插入方式
INSERT user VALUE(1,'king',24,'12@qq.com');

INSERT user VALUES(NULL,'queen',25,'queen@qq.com');
INSERT user VALUES(NULL,'lili',25,'lili@qq.com');

-- 列出指定字段
INSERT user(username,email) VALUES('rose','rose@qq.com');
INSERT user(username) VALUES('rose1');

-- 一次插入多条记录
-- INSERT tbl_name[(...)] VALUES(...),(...),(...);
INSERT user VALUES(NULL,'a',DEFAULT,DEFAULT),
(NULL,'b',56,'b@qq.com'),
(NULL,'c',15,'c@qq.com');

-- INSERT ... SET 插入形式
-- INSERT tbl_name SET 字段名=值;
INSERT user SET username = 'd',age = 45 ,email='d@qq.com';

-- INSERT .. SELECT
-- INSERT tbl_name[(...)] SELECT 字段名称 ... FROM 表名 [WHERE 条件]
CREATE TABLE test(
  a VARCHAR(20)
);
INSERT test VALUES('AA'),('BB'),('CC');

INSERT user(username) SELECT a FROM test;

-- 修改记录
-- UPDATE tbl_name SET 字段名称=值..(WHERE 条件) 如果不添加条件整个表中的记录都会被更新
UPDATE user SET age = 29 WHERE id = 1;-- 一条记录受影响
UPDATE user SET username='huahua',age = 28,email="huahua@qq.com" WHERE id = 3;
UPDATE user SET age = age + 10;
UPDATE user SET age = age - 20,email=DEFAULT WHERE id<=5;
-- 删除记录
-- DELETE FROM tbl_name (WHERE 条件)
-- 如果不添加条件表中所有的记录被删除
DELETE FROM user WHERE username='king';
DELETE FROM user WHERE age = 8;
DELETE FROM user;-- 删除表中所有记录 之后在插入数据 还是会从之前的自增长编号开始
-- 如何重置编号呢? 通过修改AUTO_INCREMENT属性的值来重置为1
ALTER TABLE user AUTO_INCREMENT = 1;
--彻底清空数据表 也会重置AUTO_INCREMENT
--TRUNCATE[TABLE] tbl_name;
TRUNCATE user;
-- 查询记录
-- SELECT select_expr,... FROM tbl_name
--[WHERE 条件] [GROUP BY {col_name|position} HAVING 二次筛选]
--[ORDER BY {col_name|position|exper} [ASC|DESC]]
--[LITMIT 限制结果集的显示条数]

-- SELECT * FROM tbl_name; 查询所有记录 * 代表所有字段
-- SELECT 字段名称,... FROM tbl_name; 查询所有记录中的某些字段

CREATE TABLE IF NOT EXISTS user1(
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(20) NOT NULL UNIQUE COMMENT "用户名",
  age TINYINT NOT NULL DEFAULT 18 COMMENT '年龄',
  sex ENUM('男','女','保密') NOT NULL DEFAULT '保密' COMMENT '性别',
  addr VARCHAR(50) NOT NULL DEFAULT '北京',
  married TINYINT(1) NOT NULL DEFAULT 0 COMMENT '0未婚,1已婚',
  salary FLOAT(8,2) NOT NULL DEFAULT 0 COMMENT '薪水'
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;
INSERT user1 VALUES(1,'king',23,'男','北京',1,500000);
INSERT user1(username,age,sex,addr,married,salary) VALUES('queen',27,2,'上海',0,25000);
INSERT user1 SET username='imooc',age = 31,sex=2,addr=DEFAULT,salary=10000;
INSERT user1 VALUES(NULL,'张三',38,1,'上海',0,15000),
(NULL,'张三1',38,1,'上海',0,15000),
(NULL,'张三2',39,2,DEFAULT,1,85000),
(NULL,'张三3',42,1,'深圳',0,95000),
(NULL,'张三4',58,1,'广州',1,115000),
(NULL,'张三5',28,1,'北京',0,115000);

INSERT user1 VALUES(NULL,'张三3',42,1,'深圳',0,95000),
(NULL,'张三4',58,1,'广州',1,115000),
(NULL,'张三5',28,1,'北京',0,115000);

SELECT username,addr,age FROM user1;
-- 字段取别名
-- SELECT 字段名称 [AS] 别名名称 FROM user1;
SELECT username AS '用户名',addr AS '地址',age '年龄' FROM user1;
--给数据表取别名 主要用于多表联查
--SELECT 字段名称,... FROM tbl_name [AS] 别名;
SELECT id,username FROM user1 AS u;

-- 表名.字段名 主要用于多表
-- SELECT tbl_name.字段名称,... FROM tbl_name;
SELECT user1.id,user1.username FROM user1;
SELECT u.id,u.username FROM user1 AS u;

-- 查询king数据库下的user1表中的所有记录 在没有在打开数据库的情况下可以使用库名.数据表名 进行查询
SELECT * FROM king.user1;

--- WHERE 条件筛选
-- 筛选复合条件的数据
-- 作为条件:比较运算符 > >= < <= != = <> <=>
SELECT id,username,age FROM user1 WHERE id = 5;

SELECT id,username,age FROM user1 WHERE id = 50;--没有符合条件的返回空的集合

-- 添加desc字段
ALTER TABLE user1
ADD userDesc VARCHAR(100);

UPDATE user1 SET userDesc = NULL WHERE id = 9;

-- 检测NULL 值的有:<=> NULL  和 IS [NOT] NULL
-- 查询userDesc 为null的用户
SELECT id,username,age,userDesc FROM user1 WHERE userDesc = NULL;
-- 不能用=检测NULL 需要使用<=>NULL检测
SELECT id,username,age,userDesc FROM user1 WHERE userDesc <=> NULL;
-- 还可以使用 IS [NOT] NULL
SELECT id,username,age,userDesc FROM user1 WHERE userDesc IS NULL;

SELECT id,username,age,userDesc FROM user1 WHERE userDesc IS NOT NULL;

-- 指定范围 [NOT] BETWEEN ... AND ...
-- 查询年龄在18 - 30 之间的用户
SELECT id,username,age,sex FROM user1
WHERE age BETWEEN 18 AND 30;

SELECT id,username,age,salary FROM user1
WHERE salary BETWEEN 10000 AND 50000;

-- 指定集:[NOT] IN (值,...)
--- 查询编号 1,3,5,7,9
SELECT id,username,age,sex FROM user1
WHERE id IN (1,3,5,7,9);

SELECT id,username,age,sex FROM user1
WHERE id NOT IN (1,3,5,7,9);

SELECT id,username,age,sex FROM user1
WHERE username IN ('king','queen','lili');

-- 逻辑运算符:AND OR
SELECT id,username,age,sex FROM user1
WHERE sex = 1 AND age >=20;

-- 练习 逻辑运算符
SELECT id,username,age,sex FROM user1
WHERE id >=5 AND age <= 30;

SELECT id,username,age,sex FROM user1
WHERE id >= 5 AND age <= 30 AND sex = 1;

SELECT id,username,age,sex,addr FROM user1
WHERE sex = 2 AND addr = '北京';

--
SELECT id,username,age,sex,salary,addr FROM user1
WHERE salary BETWEEN 60000 AND 100000 AND sex = 1 AND addr = '北京';

SELECT id,username,age FROM user1
WHERE id = 1 or username='queen';

-- 匹配字符 [NOT] LIKE 简单的模糊查询 配合通配符 % 任意长度的字符串; _ 任意一个字符
SELECT id,username,age,sex FROM user1
WHERE username LIKE 'king';--全量匹配

SELECT id,username,age,sex FROM user1
WHERE username LIKE '%三';--三前面可以有任意字符

SELECT id,username,age,sex FROM user1
WHERE username LIKE '%三%';--三前面和后面可以有任意字符

SELECT id,username,age,sex FROM user1
WHERE username LIKE '%in%';

SELECT id,username,age,sex FROM user1
WHERE username LIKE '张%';

-- 用户名长度为三位的用户
SELECT id,username,age,sex FROM user1
WHERE username LIKE '___';

SELECT id,username,age,sex FROM user1
WHERE username LIKE '张_';

SELECT id,username,age,sex FROM user1
WHERE username LIKE '张_%';

SELECT id,username,age,sex FROM user1
WHERE username LIKE '%K%'; -- 默认忽略大小写



-- GROUP BY 把记录的值相同的放到一个组中,最终查询的结果只会显示组中的一条记录
-- 按照性别来分组 注意一下写法在MySQL 8.0中会发生错误
SELECT id,username,age,sex FROM user1
GROUP BY sex;

SELECT id,username,age,sex,addr FROM user1
GROUP BY addr;

-- 分组配合GROUP_CONCAT() 查看组中某个字段的详细信息
SELECT GROUP_CONCAT(username) FROM user1
GROUP BY sex;

SELECT GROUP_CONCAT(username),GROUP_CONCAT(addr) FROM user1
GROUP BY sex;

-- 配合聚合函数 COUNT() SUM() MAX() MIN() AVG()
SELECT COUNT(*) as total_users FROM user1;-- 9条 COUNT(*) 会统计NULL值
SELECT COUNT(id) as total_users FROM user1;
SELECT COUNT(userDesc) as total_users FROM user1; -- 8条 如果字段值为空的话就不会记录统计

SELECT COUNT(*),sex FROM user1
GROUP BY sex;

SELECT GROUP_CONCAT(username),sex,COUNT(*) as total_users FROM user1
GROUP BY sex;
-- 根据地区分组 统计年龄
SELECT addr,GROUP_CONCAT(username),
COUNT(*) as total_users,
SUM(age) as sum_ages,
MAX(age) as max_age,
MIN(age) as min_age,
AVG(age) as avg_age FROM user1
GROUP BY addr;

-- 根据性别区分,统计薪水
SELECT sex,GROUP_CONCAT(username),
COUNT(*) as total_users,
SUM(salary) as sum_salary,
MAX(salary) as max_salary,
MIN(salary) as min_salary,
AVG(salary) as avg_salary FROM user1
GROUP BY sex;

-- 配合WITH ROLLUP 会在记录末尾添加一条记录 是上面所有记录的总和
SELECT GROUP_CONCAT(username), COUNT(*) as count FROM user1
GROUP BY sex WITH ROLLUP;

-- 按照位置进行分组 从1开始对应着字段的位置
SELECT GROUP_CONCAT(id),sex,GROUP_CONCAT(username),
COUNT(*) as total_users,
SUM(salary) as sum_salary,
MAX(salary) as max_salary,
MIN(salary) as min_salary,
AVG(salary) as avg_salary FROM user1
GROUP BY 2;

-- GROUP BY 配合 WHERE 一起使用
SELECT GROUP_CONCAT(username) as userDetail FROM user1
WHERE age >= 30
GROUP BY sex;

-- HANING 子句对分组结果的二次筛选
-- HAVING 后面可以加通过聚合函数操作的列(SUM..) 或者SELECT 查询的列

-- 例如根据地址分组
SELECT addr,GROUP_CONCAT(username) as userDetail,
COUNT(*) as total_users FROM user1
GROUP BY addr;

-- 在分组的基础上进行筛选总人数 >= 3的分组
SELECT addr,GROUP_CONCAT(username) as userDetail,
COUNT(*) as total_users FROM user1
GROUP BY addr
HAVING COUNT(*) >= 3;

-- 可以使用别名
SELECT addr,GROUP_CONCAT(username) as userDetail,
COUNT(*) as total_users FROM user1
GROUP BY addr
HAVING total_users >= 3;

-- addr 分组 得到用户详情 总人数 和薪水的总和 薪水的最大值和最小值 平均值
SELECT GROUP_CONCAT(username) as userDetail,
COUNT(*) as total_users,
SUM(salary) as sum_salary,
MAX(salary) as max_salary,
MIN(salary) as min_salary,
AVG(salary) as avg_salary FROM user1
GROUP BY addr;

-- 在上述分组基础上筛选 薪水的平均值大于40000的分组
SELECT GROUP_CONCAT(username) as userDetail,
COUNT(*) as total_users,
SUM(salary) as sum_salary,
MAX(salary) as max_salary,
MIN(salary) as min_salary,
AVG(salary) as avg_salary FROM user1
GROUP BY addr
HAVING avg_salary >= 40000;

-- 注意用HAVING进行分组条件时,一定要保证分组条件要么为聚合函数,要么条件的字段必须出现在当前的SELECT语句中

-- SELECT grade FROM student GROUP BY grade HAVING id>3; 这个语句就不正确

-- ORDER BY 排序
-- ORDER BY 要排序的字段名 ASC(默认升序)|DESC(降序)
SELECT id,username,age FROM user1
ORDER BY id DESC;-- 按照id降序排序

SELECT id,username,age FROM user1
ORDER BY age ASC;-- 按照age升序排序


SELECT id,username,age FROM user1
ORDER BY age;-- 按照age升序排序 默认为升序 可以不写ASC

-- 按照多个字段来排序 如下按age 升序排序 如果age相同按id升序排序
SELECT id,username,age FROM user1
ORDER BY age ASC,id ASC;


SELECT id,username,age FROM user1
WHERE age >= 30
ORDER BY age DESC;
-- 实现随机记录 打乱排序
-- SELECT RAND();
SELECT id,username,age FROM user1
ORDER BY RAND();

-- LIMIT 限制结果集显示条数 分页效果
-- LIMIT 值
-- LIMIT offset,值 (offset 表示从哪里开始,显示几条记录)
SELECT * FROM user1 LIMIT 5;
SELECT id,username,age,sex FROM user1 LIMIT 0,5;
-- 表示从第5条开始 再显示5条
SELECT * FROM user1 LIMIT 5,5;

UPDATE user1 SET age = age + 5 LIMIT 3;

UPDATE user1 SET age = age - 10
ORDER BY id DESC
LIMIT 3;
-- 删除前三条记录
DELETE FROM user1 LIMIT 3;

DELETE FROM user1
ORDER BY id DESC
LIMIT 3;


SELECT addr,GROUP_CONCAT(username) AS userDetail,
COUNT(*) AS totalUsers,
MAX(age) AS max_age,
MIN(age) AS min_age,
AVG(age) AS avg_age,
SUM(age) AS sum_age
FROM user1
GROUP BY addr HAVING totalUsers >= 2
ORDER BY totalUsers ASC
LIMIT 2,2;
