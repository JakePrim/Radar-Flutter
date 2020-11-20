-- 特殊形式查询 子查询:一个查询语句嵌套在另一个查询语句中
-- SELECT col_name... FROM tbl_name WHERE col_name = (SELECT col_name FROM tbl_name2);
-- 内层语句查询的结果可以作为外层语句查询的条件
-- example:
-- 使用 emp 和  dep 两个表

-- 由IN引发的子查询
--
SELECT id FROM dep;
SELECT * FROM emp WHERE depId IN(1,2,3,4);
-- 查询员工中的depId在部门dep的编号中 将上述两个语句进行合并
SELECT * FROM emp WHERE depId IN(SELECT id FROM dep);
-- 查询员工中的depId不在部分dep中的信息
SELECT * FROM emp WHERE depId NOT IN(SELECT id FROM dep);

-- 由比较运算符 引出的子查询

CREATE TABLE IF NOT EXISTS stu(
  id TINYINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(20) NOT NULL UNIQUE,
  score TINYINT NOT NULL
);
INSERT stu(username,score) VALUES
('king',95),
('queen',75),
('zhangsan',69),
('lisi',78),
('wangwu',87),
('tianqi',98),
('ceshi',99),
('tiancai',50);

CREATE TABLE IF NOT EXISTS `level`(
  id TINYINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  score TINYINT UNSIGNED
);

INSERT `level`(score) VALUES(90),(80),(70);

-- 要求:查询出成绩优秀的学员
SELECT score FROM level WHERE id = 1;
SELECT * FROM stu WHERE score >= (SELECT score FROM level WHERE id = 1);
-- 分数没有达到最低级的
SELECT * FROM stu WHERE score <= (SELECT score FROM level WHERE id = 3);

--由 EXISTS 引发的子查询
SELECT depName FROM dep WHERE id = 10;

-- 查找是否存在部门编号为10的员工 如果存在查询有所员工
SELECT * FROM emp WHERE EXISTS (SELECT depName FROM dep WHERE id = 10);

SELECT * FROM emp WHERE EXISTS (SELECT depName FROM dep WHERE id = 1);



-- ANY SOME ALL 关键字的子查询
-- > >= : ANY 代表是最小值 | SOME 代表最小值 | ALL 代表最大值
-- < <= : ANY 最大值 | SOME 最大值 | ALL 最小值
-- = : ANY 任意值 | SOME 任意值
-- <> != : ALL 任意值

-- 查询有哪些学员可以获得评级
-- > >= ANY|SOME(一些值中的最小值)
SELECT * FROM stu WHERE score >= ANY(SELECT score FROM level);
SELECT * FROM stu WHERE score >= SOME(SELECT score FROM level);
-- > >= ALL(一些值中的最大值)
SELECT * FROM stu WHERE score >= ALL(SELECT score FROM level);

-- < <= ANY|SOME(一些值中的最大值)
SELECT * FROM stu WHERE score <= ANY(SELECT score FROM level);
-- < <= ALL(一些值中的最小值) 不能获得评级的学员
SELECT * FROM stu WHERE score <= ALL(SELECT score FROM level);

--
SELECT * FROM stu WHERE score = ANY(SELECT score FROM level WHERE id = 1);

-- 相当于 score = 90
SELECT * FROM stu WHERE score = ALL(SELECT score FROM level WHERE id =1);

-- NOT IN 与 != ALL 或 <>ALL 等效

-- 联合查询的使用
--UNION(去掉重复的数据) | UNION ALL(只是简单的合并 重复数据存在)
-- SELECT .... FROM tbl_name UNION SELECT ... FROM tbl_name2;
-- SELECT .... FROM tbl_name UNION ALL SELECT ... FROM tbl_name2;

-- 将user1和user2的数据合并到一起
SELECT * FROM user1 UNION SELECT * FROM user2;

SELECT * FROM user1 UNION ALL SELECT * FROM user2;

-- 创建相同的表结构的语句
-- CREATE TABLE tbl_name1 LIKE tbl_name2;
CREATE TABLE user3 LIKE user;



-- INSERT ... SELECT
-- 创建一个user1表,把员工表中的用户导入到新表中
CREATE TABLE IF NOT EXISTS user2(
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(20) NOT NULL UNIQUE
)SELECT id,username FROM emp;

-- 导入其他表中的数据
INSERT user2(username) SELECT username FROM user;

INSERT user1(id,username) SELECT id,username FROM user2;

INSERT user2 SET username = (SELECT username FROM stu WHERE id = 8);

-- 去掉重复值查询DISTINCT()
SELECT DISTINCT(username) FROM user2;


-- 自查询 无限极分类数据表的设计 自身连接查询
-- 类似于淘宝和京东的商品分类
CREATE TABLE IF NOT EXISTS pro_cate(
  id SMALLINT UNSIGNED AUTO_INCREMENT KEY,
  cateName VARCHAR(20) NOT NULL UNIQUE,
  pId SMALLINT UNSIGNED NOT NULL DEFAULT 0
);

-- 举个例子 可以这样理解 服装是顶级分类 长袖属于服装所以pId 就是1 8分袖属于长袖 pId = 2
-- id    Name   pId
-- 1      服装    0
-- 2     长袖     1
-- 3     8分袖    2
-- 4     裙子     1
-- 插入顶级分类
INSERT pro_cate(cateName) VALUES('服装')
,('数码'),('玩具');

-- 插入服装的子分类
INSERT pro_cate(cateName,pId) VALUES('男装',1),
('女装',1),('内衣',1);

-- 插入数码的子分类
INSERT pro_cate(cateName,pId) VALUES('电视',2),
('冰箱',2),('洗衣机',2);

-- 插入玩具的子分类
INSERT pro_cate(cateName,pId) VALUES('爱马仕',3),
('LV',3),('GUCCI',3);

-- 插入男装的子分类
INSERT pro_cate(cateName,pId) VALUES('夹克',4),
('衬衫',4),('裤子',4);

-- 插入电视的子分类
INSERT pro_cate(cateName,pId) VALUES('液晶电视',7),
('等离子电视',7),('背投电视',7);

-- 查询所有的分类信息 并且得到父分类信息
SELECT s.id,s.cateName,p.cateName AS '父类' FROM pro_cate AS s
LEFT JOIN pro_cate AS p
ON s.pId = p.id;

-- 统计子分类的数目
SELECT p.id,p.cateName AS '父类',COUNT(s.cateName) FROM pro_cate AS s
RIGHT JOIN pro_cate AS p
ON s.pId = p.id
GROUP BY p.cateName
ORDER BY p.id ASC;
