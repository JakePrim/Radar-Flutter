-- CEIL(X) 该函数返回的最小整数值,但不能小于X 对数值向上取整 一般12.12 CEIL(12.12) = 13
-- ROUND(X) 函数为四舍五入
-- FLOOR(X) 对数值向下取整 也就是只取小数的整数位
-- TRUNCATE(X,I) 截取X的I的小数位 如果I为0 只取整数位 ; 1 取小数点的后一位
-- MOD : 取余 MOD(10,3); 10除以3取余 或者 10 % 3

-- CHAR_LENGTH(); 得到字符串的字符数
-- LENGTH(); 得到字符串的长度
-- CONCAT();合并字符串为一个字符串 如果字符串中包含null 返回的拼接结果就是null
--CONCAT_WS();以指定分隔符拼接字符串
-- 转换大小写的函数 UPPER UCASE LOWER LCASE
SELECT UPPER('hello king'),UCASE('hello imooc'),LOWER('HELLO ADMIN'),
LCASE('HELLO EVERYBODY');
-- 字符串的反转 REVERSE();
SELECT REVERSE('abc');

-- LEFT() RIGHT(); 返回字符串的前几个字符或者后几个字符
SELECT LEFT('hello',2),RIGHT('hello',2);

-- LPAD()|RPAD(); 用字符串填充到指定长度
SELECT LPAD('abc',10,'?');
SELECT RPAD('abc',10,'?');

-- 去掉字符串两端的空格 TRIM|LTRIM|RTRIM
SELECT CONCAT('*',TRIM(' abc '),'*'),CONCAT('*',LTRIM(' abc '),'*'),CONCAT('*',RTRIM(' abc '),'*');

-- REPEAT 重复指定的次数
SELECT REPEAT('hello',3);

-- REPLACE 替换字符串
SELECT REPLACE('hello king','king','queen');

-- SUBSTRING 截取字符串
SELECT SUBSTRING('hello',2,2);

--STRCMP 比较字符串
SELECT STRCMP('a','b'),STRCMP('z','a'),STRCMP('a','a');

-- 返回当前日期
SELECT CURDATE(),CURRENT_DATE();

-- 返回当前时间
SELECT CURTIME(),CURRENT_TIME();

-- 返回当前的日期时间
SELECT NOW(),CURRENT_TIMESTAMP(),SYSDATE();

-- 返回日期中的月份和月份名称
SELECT MONTH(CURDATE()),MONTHNAME(CURDATE());

-- 返回星期几
SELECT DAY(CURDATE()),DAYNAME(CURDATE());

-- 返回一周内的第几天
SELECT DAYOFWEEK(NOW());
SELECT WEEK(NOW());

SELECT YEAR(NOW()),MONTH(NOW()),DAY(NOW()),HOUR(NOW()),MINUTE(NOW()),SECOND(NOW());

-- 两个日期相差的天数 DATEDIFF
SELECT DATEDIFF('2020-2-22','2020-1-10');

-- DATE_FORMAT 改变日期的格式
SELECT DATE_FORMAT('2020-2-22','%Y/%m/%d');

-- 获取mysql当前的版本 和连接数 返回当前连接的连接ID，每个连接都有一个在数据库中唯一的ID
SELECT VERSION(),CONNECTION_ID();



-- 获取当前的数据库名
SELECT DATABASE(),SCHEMA();

-- 得到当前登录的用户 MySQL用户名和主机名

SELECT USER(),CURRENT_USER(),SYSTEM_USER(),SESSION_USER();

-- 得到上一步操作插入的AUTO_INCREMENT 产生的值
-- 自动返回上一步 INSERT或 UPDATE 操作，产生的AUTO_INCREMENT列的值
SELECT LAST_INSERT_ID();

-- MD5() 加密
SELECT MD5('king');

-- PASSWORD() 密码加密算法
SELECT PASSWORD('root');










