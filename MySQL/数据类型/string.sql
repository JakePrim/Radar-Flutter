--decimal 为什么精度高?? 疑问

-- char 是定长 如果设置定长度是50 那么设置a的长度还是五十 浪费的是空间
-- varchar 是变长 长度是如果设置的是50则 0 - 50,如果设置为a则占用的字节大小就是a的大小+1 避免了空间的不必要的浪费
-- char的查询速度大于varchar char是用空间换取了时间
-- CHAR 效率高于VARCHAR,CHAR 相当于拿空间换取时间,VARCHAR相当于拿时间换取空间
-- CHAR 默认存储数据的时候,后面会用 空格填充到指定的长度,检索的时候会去掉后面的空格
-- VARCHAR 在保存数据时不进行填充,尾部的空格会留下
-- char定义的是固定长度，长度范围为0-255，存储时，如果字符数没有达到定义的位数，会在后面用空格补全存入数据库中，
-- varchar是变长长度，长度范围为0-65535，存储时，如果字符没有达到定义的位数，也不会在后面补空格

-- TEXT 不能有默认值 检索的时候不存在大小写转换

CREATE TABLE IF NOT EXISTS test_str(
  a CHAR(5),
  b VARCHAR(5)
);

INSERT  test_str(a,b) VALUES('','');
INSERT  test_str(a,b) VALUES('a','a');
INSERT  test_str(a,b) VALUES('ab','ab');
INSERT  test_str(a,b) VALUES('abc','abc');
INSERT  test_str(a,b) VALUES('abcd','abcd');
INSERT  test_str(a,b) VALUES('abcde','abcde');
-- ERROR 1406 (22001): Data too long for column 'a' at row 1
INSERT  test_str(a,b) VALUES('abcdef','abcdef');

SELECT * FROM test_str;

-- 测试空字符串char和varchar的区别
INSERT test_str(a,b) VALUES(' 123 ',' 123 ');
SELECT CONCAT('*',a,'*'),CONCAT('*',b,'*') FROM test_str;

-- 测试TEXT不能有默认值
CREATE  TABLE IF NOT  EXISTS test_text(
  context TEXT DEFAULT 'THIS IS '
);

-- 测试ENUM
CREATE TABLE IF NOT EXISTS test_enum(
  sex ENUM('男','女','保密')
);
-- ENUM 是可以为null的
DESC test_enum;

INSERT test_enum(sex) VALUES('男');
INSERT test_enum(sex) VALUES('男1');
INSERT test_enum(sex) VALUES(NULL);
INSERT test_enum(sex) VALUES(1);
INSERT test_enum(sex) VALUES(2);
INSERT test_enum(sex) VALUES(3);
INSERT test_enum(sex) VALUES(5);
INSERT test_enum(sex) VALUES('男     ');
INSERT test_enum(sex) VALUES('    女');

CREATE TABLE IF NOT EXISTS test_enum1(
  sex ENUM('男','女','保密') NOT NULL
);
INSERT test_enum1(sex) VALUES(NULL);

CREATE TABLE IF NOT EXISTS test_enum4(
  sex ENUM('男    ',' 女','保密  ')
);
INSERT test_enum3(sex) VALUES('男');
INSERT test_enum4(sex) VALUES(' 女');
INSERT test_enum3(sex) VALUES('保密');

-- 测试SET类型
CREATE TABLE IF NOT EXISTS test_set(
  a SET('A','B','C','D','E','F')
);
INSERT test_set(a) VALUES('A');
INSERT test_set(a) VALUES(2,1,3);
-- 插入多个值
INSERT test_set(a) VALUES('A,B,C');
INSERT test_set(a) VALUES('A,F,C');
