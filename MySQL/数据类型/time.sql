CREATE TABLE IF NOT EXISTS test_time(
  time TIME
);
-- TIME HH:MM:SS[D HH:MM:SS] D 表示天数 0~34 存储为3个字节
-- 插入TIME类型有几种不同的方式如下
INSERT test_time(time) VALUES('12:23:45');
-- 还可以前天带上天数
INSERT test_time(time) VALUES('2 12:23:45');
-- 相当于HH:MM:00
INSERT test_time(time) VALUES('22:22');
-- 相当于00:00:22
INSERT test_time(time) VALUES('22');
-- 注意如果带上天数就相当于小时了
INSERT test_time(time) VALUES('2 22');
-- 还可以按照HHMMSS的格式
INSERT test_time(time) VALUES('121212');
-- 如果存储超出了格式则会报错 Incorrect time value: '787878' for column 'time' at row 1
INSERT test_time(time) VALUES('787878');
-- 在看一下插入0会保存为什么格式呢?
INSERT test_time(time) VALUES(0);
INSERT test_time(time) VALUES('0');
-- 插入当前时间
INSERT test_time(time) VALUES(NOW());
INSERT test_time(time) VALUES(CURRENT_TIME);
INSERT test_time(time) VALUES(CURTIME());
-- 时间可以使用整型保存时间戳的形式

-- 测试DATE类型YYYY-MM-DD YYYYMMDD
CREATE TABLE IF NOT EXISTS test_date(
  d DATE
);

-- 插入时间的几种类型
INSERT test_date(d) VALUES('2019-01-02');
INSERT test_date(d) VALUES('2019-2-3');
INSERT test_date(d) VALUES('7008-11-13');
INSERT test_date(d) VALUES('70081213');
-- 也可以使用其他分隔符
INSERT test_date(d) VALUES('2018@10@11');
INSERT test_date(d) VALUES('2018.10.13');
INSERT test_date(d) VALUES('2018#10#14');

-- YY-MM-DD YYMMDD 的格式 注意:YY 的转换格式 70~99 = 1979~1999;00~69 = 2000~2069
INSERT test_date(d) VALUES('780123');
INSERT test_date(d) VALUES('78-11-24');
INSERT test_date(d) VALUES('650908');
INSERT test_date(d) VALUES('881018');

-- 插入当前时间
INSERT test_date(d) VALUES(NOW());
INSERT test_date(d) VALUES(CURRENT_DATE);


-- 测试DATETIME
CREATE TABLE test_datetime(
  d DATETIME
);
INSERT test_datetime(d) VALUES('2019-01-01 12:13:14');
INSERT test_datetime(d) VALUES('671113121212');
-- 插入当前时间
INSERT test_datetime(d) VALUES(NOW());

CREATE TABLE test_timestamp(
  t TIMESTAMP
);
INSERT test_timestamp(t) VALUES('1978-10-11 10:11:12');
-- 插入当前时间
INSERT test_timestamp(t) VALUES(NOW());
INSERT test_timestamp(t) VALUES(CURRENT_TIMESTAMP);
INSERT test_timestamp(t) VALUES(NULL);
INSERT test_timestamp(t) VALUES();-- 在MYSQL 8 以上的版本发生错误

-- YEAR 范围 1901 -2155 只占用了一个字节
CREATE TABLE test_year(
  a YEAR
);
INSERT test_year(a) VALUES(1901);
INSERT test_year(a) VALUES(1900);-- 报错 超出范围
INSERT test_year(a) VALUES(2155);
INSERT test_year(a) VALUES(2156);-- 报错 超出范围
