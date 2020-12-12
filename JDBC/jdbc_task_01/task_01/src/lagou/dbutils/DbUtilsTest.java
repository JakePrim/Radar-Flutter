package lagou.dbutils;

import lagou.demo04.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;

public class DbUtilsTest {
    public static void main(String[] args) {
        //1 手动模式
        QueryRunner qr = new QueryRunner();
        //2 自动模式 传入数据库连接池对象 提供数据库连接池对象 DbUtils会自动的维护连接
        QueryRunner qr2 = new QueryRunner(DruidUtils.getDataSource());
    }
}
