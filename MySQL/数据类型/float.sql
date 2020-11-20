-- 测试浮点型
CREATE TABLE IF NOT EXISTS test_float(
  a FLOAT(6,2),-- 表示小数为六位 小数点后面是两位
  b DOUBLE(6,2),
  c DECIMAL(6,2)-- DECIMAL精度高是指它能够表示的小数位比float和double更多，在进行小数运算时可以更好的防止小数丢失。
);

INSERT test_float(a,b,c) VALUES(4.143,4.146,4.149);
-- 4.14 4.15 4.15

CREATE TABLE IF NOT EXISTS test_float2(
  a FLOAT,-- 表示小数为六位 小数点后面是两位
  b DOUBLE,
  c DECIMAL-- DECIMAL精度高是指它能够表示的小数位比float和double更多，在进行小数运算时可以更好的防止小数丢失。
);
INSERT test_float2(a,b,c) VALUES(4.143,4.146,4.149);
-- 4.143 4.146 4
INSERT test_float2(a,b,c) VALUES(4.143,4.146,4.549);
-- -- 4.143 4.146 5
