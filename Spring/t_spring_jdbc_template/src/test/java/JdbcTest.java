import com.prim.bean.Student;
import com.prim.dao.StudentDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class JdbcTest {
    private JdbcTemplate jdbcTemplate;

    {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        //拿到jdbcTemplate
        jdbcTemplate = (JdbcTemplate) applicationContext.getBean("jdbcTemplate");
    }

    @Autowired
    private StudentDao studentDao;

    @Test
    public void testSpring() {
        List<Student> all = studentDao.findAll();
        System.out.println("JdbcTest.testSpring:" + all);
    }

    @Test
    public void testExecute() {
        //简化了jdbc的操作
        //execute
        jdbcTemplate.execute("create table user1(id int,name varchar(20))");
        //实现对数据对增删改操作
        //update 对数据增删改操作
        //插入一条数据
        int update = jdbcTemplate.update("insert into student(name, sex) values (?,?)", new Object[]{"张三", "男"});
        System.out.println("JdbcTest.testExecute" + update);

        //更新一条数据
        jdbcTemplate.update("update student set sex = ? where id=?", "女", 1);

        //batchUpdate 批量增删改操作
        jdbcTemplate.batchUpdate(new String[]{
                "insert into student(name, sex) values ('关羽','女')",
                "insert into student(name, sex) values ('刘备','男')",
                "update  student set sex='女' where id=3",
        });
        //另一种方式
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[]{1, 1001});
        list.add(new Object[]{1, 1003});
        int[] ints = jdbcTemplate.batchUpdate("insert into elective(sid, cid) VALUES (?,?)",
                list);
    }

    @Test
    public void testQuery() {
        //查询一个
        Integer integer = jdbcTemplate.queryForObject("select count(*) from student", Integer.class);
        System.out.println("JdbcTest.testQuery:" + integer);

        //查询多个
        List<String> stringList = jdbcTemplate.queryForList("select name from student where sex=?", String.class, "女");
        System.out.println("JdbcTest.testQuery:" + stringList);

        //查询复杂对象 Map
        //获取一个
        Map<String, Object> map = jdbcTemplate.queryForMap("select * from student where id=?", 2);
        System.out.println("JdbcTest.testQuery:" + map);

        //获取多个
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from student");
        System.out.println("JdbcTest.testQuery:" + maps);

        //复杂对象 封装为实体对象
        //RowMapper接口
        Student student = jdbcTemplate.queryForObject("select * from student where id=?", new StudentRowMapper(), 1);
        System.out.println("JdbcTest.testQuery:" + student);

        //查询多个对象 query
        List<Student> students = jdbcTemplate.query("select * from student", new StudentRowMapper());
        System.out.println("JdbcTest.testQuery:" + students);
    }

    private static class StudentRowMapper implements RowMapper<Student> {

        @Override
        public Student mapRow(ResultSet resultSet, int i) throws SQLException {
            Student student1 = new Student();
            student1.setId(resultSet.getInt("id"));
            student1.setName(resultSet.getString("name"));
            student1.setSex(resultSet.getString("sex"));
            student1.setBorn(resultSet.getDate("birthday"));
            return student1;
        }
    }
}
