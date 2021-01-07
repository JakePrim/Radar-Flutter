
-- 新闻分类表
CREATE TABLE IF NOT EXISTS news_cate(
  id TINYINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  cateName VARCHAR(50) NOT NULL UNIQUE,
  cateDesc VARCHAR(100) NOT NULL DEFAULT ''
)ENGINE=InnoDB;

INSERT news_cate(cateName) VALUES('国内新闻'),
('国际新闻'),('娱乐新闻'),('体育新闻');

-- 新闻表
CREATE TABLE IF NOT EXISTS news(
  id INT UNSIGNED AUTO_INCREMENT KEY,
  title VARCHAR(100) NOT NULL UNIQUE,
  content LONGTEXT NOT NULL,
  cateId TINYINT UNSIGNED NOT NULL,
  FOREIGN KEY(cateId) REFERENCES news_cate(id)
)ENGINE=InnoDB;

-- 指定外键
-- [CONSTRAINT 外键名称] FOREIGN KEY(字段名) REFERENCES 主表(字段名称)
-- 注意
-- 子表的外键字段 和主表的主键字段类型要相似;如果是数值型要求一致,并且无符号也要一致
-- 如果是字符类型,要求类型一致,长度可以不行
-- 如果外键字段没有添加索引,MySQL会自动帮我们添加索引
-- 子表的外键关联的必须是父表的主键
-- 外键约束只有InnoDB存储引擎支持外键

INSERT news(title,content,cateId) VALUES('a1','aaa1',1),
('a2','aaa2',1),
('a3','aaa3',4),
('a4','aaa4',2),
('a5','aaa5',3);

SELECT n.id,n.title,n.content,c.cateName
FROM news AS n
JOIN news_cate AS c
ON n.cateId = c.id;
-- 测试非法插入
INSERT news(title,content,cateId) VALUES('a6','aa6',6);
--  Cannot add or update a child row

-- 测试删除父表中的记录,查看子表中是否还有记录
DELETE FROM news_cate WHERE id = 1;-- 删除国内新闻类型
-- 无法删除了,因为子表中使用id=1 删除会影响子表的 Cannot delete or update a parent row

-- 测试更新父表中的主键
UPDATE news_cate SET id = 10 WHERE id = 1;
-- 同样是无法更新的,因为在子表中news中还有记录用到id=1 Cannot delete or update a parent row
-- 可以更改处了主键之外的字段的值 如下这样是可以成功的.
UPDATE news_cate SET cateName = '国内' WHERE id = 1;

DROP TABLE news_cate;-- 同样如果子表中有记录,那么父表无法删除,必须先删除子表
DROP TABLE news;

-- 向父表中插入子表没有的id
INSERT news_cate(cateName) VALUES('教育新闻');

-- 在更新/删除父表中的教育新闻,只要不影响子表都能成功
UPDATE news_cate SET id =10 WHERE id = 5;
DELETE FROM news_cate WHERE id = 10;

-- 动态删除外键
-- ALTER TABLE tbl_name DROP FOREING KEY fk_name(必须是外键名称)
-- 如果没有设置外键名称那么先查询外键名称
SHOW CREATE TABLE news;
ALTER TABLE news DROP FOREIGN KEY news_ibfk_1;
-- 也可在建表时设置外键名称
CREATE TABLE IF NOT EXISTS news(
  id INT UNSIGNED AUTO_INCREMENT KEY,
  title VARCHAR(100) NOT NULL UNIQUE,
  content LONGTEXT NOT NULL,
  cateId TINYINT UNSIGNED NOT NULL,
  CONSTRAINT news_cateId_fk FOREIGN KEY(cateId) REFERENCES news_cate(id)
)ENGINE=InnoDB;
ALTER TABLE news DROP FOREIGN KEY news_cateId_fk;


-- 动态添加外键
-- 动态添加外键之前,表中的值记录一定要是合法记录,也就是说外键的值必须在父表中存在,否则添加不成功
-- ALTER TABLE tbl_name ADD [CONSTRAINT 外键名称] FOREIGN KEY(外键字段) REFERENCES 父表(主键);
ALTER TABLE news
ADD CONSTRAINT  news_cateId_fk
FOREIGN KEY(cateId) REFERENCES news_cate(id);

-- 外键约束的参照操作
-- CASCADE - 从父表中删除或更新,子表也跟着删除或更新,级联的操作
-- SET NULL - 从父表中删除或更新,并设置子表的外键列为NULL
-- NO ACTION | RESTRICT 拒绝对父表做更新和删除操作 (默认的)

-- 测试操作
ALTER TABLE news DROP FOREIGN KEY news_cateId_fk;

-- 测试 DELETE CASCADE/ UPDATE CASCADE
ALTER TABLE news
ADD FOREIGN KEY(cateId) REFERENCES news_cate(id)
ON DELETE CASCADE ON UPDATE CASCADE;

-- 将id=1 更新id为11,子表中是存在id=1的这个记录的但是我们指定了 ON UPDATE CASCADE 子表也会更新
UPDATE news_cate SET id = 10 WHERE id = 1;

DELETE FROM news_cate WHERE id = 10;

-- 这时候我们去删除
