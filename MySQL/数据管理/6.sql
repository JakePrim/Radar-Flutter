

CREATE TABLE IF NOT EXISTS `class`(
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(20) NOT NULL UNIQUE,
	descrip VARCHAR(20) DEFAULT '普通班'
)ENGINE=INNODB CHARSET=UTF8;

INSERT class(name,descrip) VALUES('一年级一班','重点班'),
('一年级二班','重点班'),
('二年级一班','重点班'),
('二年级二班','普通班');

CREATE TABLE IF NOT EXISTS student(
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(10) NOT NULL UNIQUE,
    gender ENUM('男','女') NOT NULL,
    c_id INT UNSIGNED NOT NULL,
    CONSTRAINT student_cid_fk FOREIGN KEY(c_id) REFERENCES class(id)
)ENGINE = InnoDB CHARSET=UTF8;

INSERT student(id,name,gender,c_id) VALUES(1001,'赵晓明',1,1);

INSERT student(name,gender,c_id) VALUES('王晓红',2,1),
('张晓晓',2,1),
('孙琪琪',2,3),
('李米米',2,4),
('赵晓刚',1,3),
('张大宝',1,2),
('张兰',2,4),
('孙好',1,1);

CREATE TABLE IF NOT EXISTS course(
	id TINYINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(10) NOT NULL UNIQUE
);

INSERT course(name) VALUES('数学'),('语文'),('英语');


CREATE TABLE IF NOT EXISTS score(
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	s_id INT UNSIGNED NOT NULL,
	course_id TINYINT UNSIGNED NOT NULL,
	mark TINYINT UNSIGNED NOT NULL DEFAULT 0,
	CONSTRAINT score_sid_fk FOREIGN KEY(s_id) REFERENCES student(id),
	CONSTRAINT score_courseid_fk FOREIGN KEY(course_id) REFERENCES course(id)
)ENGINE=InnoDB CHARSET=UTF8;

INSERT score(s_id,course_id,mark) VALUES(1001,1,98),
(1001,2,90),
(1001,3,97),
(1002,1,96),
(1003,1,96),
(1003,3,86),
(1004,2,82),
(1004,3,83),
(1005,1,75),
(1005,2,86),
(1005,3,77),
(1006,1,81),
(1006,2,77),
(1006,3,60),
(1007,1,89),
(1007,2,56),
(1007,3,60),
(1008,1,87),
(1008,2,55),
(1008,3,66),
(1009,1,78),
(1009,2,60),
(1009,3,52);


-- 查询每个班级中每一科的平均成绩，显示数据包括班级名称，
-- 课程以及平均分数，并按照班ID升序排列
-- 
SELECT c.name,co.name,AVG(sc.mark) AS avg_mark FROM class AS c
JOIN student AS s ON c.id = s.c_id
JOIN score AS sc ON sc.s_id = s.id
JOIN course AS co ON co.id = sc.course_id
GROUP BY c.name,co.name
ORDER BY c.id ASC;

-- 查询所有同学的学生ID，姓名，性别以及总分，并按照成绩从高到低排序
SELECT s.id,s.name,s.gender,SUM(sc.mark) AS allMark FROM student AS s
JOIN score AS sc ON sc.s_id = s.id
GROUP BY s.id
ORDER BY allMark DESC;

-- 查询课程成绩小于75分的学生ID，姓名，班级，课程以及分数
SELECT s.id,s.name,c.name,co.name,sc.mark FROM student AS s
JOIN class AS c ON s.c_id = c.id
JOIN score AS sc ON sc.s_id = s.id
JOIN course AS co ON co.id = sc.course_id
WHERE sc.mark < 75;

-- 将李米米的数学成绩修改为88分
UPDATE score SET mark = 88 WHERE s_id = (SELECT id FROM student WHERE name='李米米')
AND course_id=(SELECT id FROM course WHERE name='数学');

-- 算重点班中每一科的平均成绩，
-- 显示数据包括：重点班级ID，班级名称，课程，平均分数，按照降序排列
SELECT c.id,c.name,co.name,AVG(sc.mark) AS avg_mark FROM class AS c
JOIN student AS s ON c.id = s.c_id
JOIN score AS sc ON sc.s_id = s.id
JOIN course AS co ON co.id=sc.course_id
WHERE c.descrip = '重点班'
GROUP BY c.name,co.name
ORDER BY avg_mark DESC;




