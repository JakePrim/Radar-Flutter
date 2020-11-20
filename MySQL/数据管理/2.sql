-- 多表查询
-- 分为以下三种形式
-- 笛卡尔积
-- 内链接
-- 外链接

--员工表
CREATE DATABASE IF NOT EXISTS test2 DEFAULT CHARACTER SET 'UTF8';
USE test2;
CREATE TABLE IF NOT EXISTS emp(
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(20) NOT NULL UNIQUE COMMENT "用户名",
  age TINYINT NOT NULL DEFAULT 18 COMMENT '年龄',
  sex ENUM('男','女','保密') NOT NULL DEFAULT '保密' COMMENT '性别',
  addr VARCHAR(50) NOT NULL DEFAULT '北京',
  depId TINYINT UNSIGNED NOT NULL COMMENT '部门对应的编号'
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;
INSERT emp(username,age,depId) VALUES('king',25,1),
('queen',26,2),
('imooc',27,1),
('lili',28,2),
('rose',29,1),
('john',30,3),
('prim',31,3);


--部门表
CREATE TABLE IF NOT EXISTS dep(
  id TINYINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  depName VARCHAR(10) NOT NULL UNIQUE,
  depDesc VARCHAR(50) NOT NULL DEFAULT ''
)ENGINE = InnoDB CHARSET=UTF8;

INSERT dep(depName,depDesc)
VALUES('Java技术部','JAVA'),
('Android技术部','Android'),
('IOS技术部','IOS'),
('WEB前端技术部','WEB前端');


-- 查询员工的名称 年龄 部门名称

--笛卡尔积
SELECT emp.id,emp.username,emp.age,dep.depName FROM emp,dep; -- 这样的查询肯定是不正确的

-- 内链接
-- SELECT 字段名称,... FROM tbl_name1
-- [INNER] JOIN tbl_name2 ON 连接条件
--(连接的表最好不要超过三张表)
SELECT e.id,e.username,e.age,d.depName
FROM emp AS e INNER JOIN dep AS d
ON e.depId = d.id;

INSERT emp(username,age,depId) VALUES('测试用户',28,6);

--外链接
--左外链接 SELECT .... FROM tbl_name1 LEFT [OUTER] JOIN tbl_name2 ON 条件
--先显示左表的全部记录,然后去右表中查询符合条件的记录 不符合条件的以NULL代替

SELECT e.id,e.username,e.age,d.depName,d.depDesc FROM emp AS e
LEFT OUTER JOIN dep AS d
ON e.depId = d.id;

-- 右外连接 以右边为主,先显示右表的全部记录,再去左表中查询符合条件的记录

SELECT e.id,e.username,e.age,d.depName,d.depDesc FROM emp AS e
RIGHT JOIN dep AS d
ON e.depId = d.id;

CREATE TABLE IF NOT EXISTS user(
  id TINYINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '编号',
  username VARCHAR(20) NOT NULL UNIQUE COMMENT '用户名',
  email VARCHAR(50) NOT NULL DEFAULT '1@qq.com' COMMENT '邮箱',
  proId TINYINT UNSIGNED NOT NULL COMMENT '用户所在省份编号'
)ENGINE=INNODB CHARSET=UTF8;

INSERT user(username,proId) VALUES('a','1'),
('b','1'),
('c','1'),
('d','2'),
('e','3'),
('f','1'),
('g','1');

CREATE TABLE IF NOT EXISTS pro(
  id TINYINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '省份编号',
  proName VARCHAR(10) NOT NULL UNIQUE COMMENT '省份名称'
)ENGINE = INNODB CHARSET = UTF8;

INSERT pro(proName) VALUES('北京'),('上海'),('深圳');

SELECT u.id,u.username,p.proName FROM user AS u
JOIN pro AS p
ON u.proId = p.id;

-- 如果我们想要将所有的用户表中的北京改为首都,如果使用的一个表,需要操作这个表的所有用户进行修改,如果有一百万个
-- 用户 在性能上可想而知了,使用多个表后,我们只需要该pro表中的一条记录就可以了,相比该几百万条记录
-- 简单了好多
UPDATE pro SET proName = '首都' WHERE id=1;

-- 管理员表
CREATE TABLE IF NOT EXISTS admin(
  id TINYINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '编号',
  username VARCHAR(20) NOT NULL UNIQUE COMMENT '用户名',
  email VARCHAR(50) NOT NULL DEFAULT '1@qq.com' COMMENT '邮箱',
  proId TINYINT UNSIGNED NOT NULL COMMENT '用户所在省份编号'
)ENGINE=INNODB CHARSET=UTF8;

INSERT admin(username,proId) VALUES('king',1),
('queen',2);

-- 商品分类表
CREATE TABLE IF NOT EXISTS cate(
  id TINYINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  cateName VARCHAR(50) NOT NULL UNIQUE,
  cateDesc VARCHAR(100) NOT NULL DEFAULT ''
);

INSERT cate(cateName) VALUES('母婴'),('服装'),('电子');

--商品表
CREATE TABLE IF NOT EXISTS product(
  id INT UNSIGNED AUTO_INCREMENT KEY,
  productName VARCHAR(50) NOT NULL UNIQUE,
  price FLOAT(8,2) NOT NULL DEFAULT 0,
  cateId TINYINT UNSIGNED NOT NULL,
  adminId TINYINT UNSIGNED NOT NULL
);
INSERT product(productName,price,cateId,adminId) VALUES('iphone9',9888,3,1),
('adidas',388,2,2),
('nike',888,2,1),
('奶瓶',288,1,1);

-- 多表查询
SELECT p.id,p.productName,p.price,c.cateName,a.username,a.email
FROM product AS p
JOIN cate AS c
ON p.cateId = c.id
JOIN admin AS a
ON p.adminId = a.id;

SELECT p.id,p.productName,p.price,c.cateName,a.username,a.email
FROM product AS p
JOIN cate AS c
ON p.cateId = c.id
JOIN admin AS a
ON p.adminId = a.id
WHERE p.price <= 1000
ORDER BY p.price DESC
LIMIT 0,2;

SELECT p.id,p.productName,p.price,c.cateName,a.username,a.email,pro.proName
FROM product AS p
JOIN cate AS c
ON p.cateId = c.id
JOIN admin AS a
ON p.adminId = a.id
JOIN pro
ON a.proId = pro.id
WHERE p.price <= 1000
ORDER BY p.price DESC
LIMIT 0,2;
