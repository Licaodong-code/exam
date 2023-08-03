import com.cetc28.DbUtils.DbUtil;

import java.sql.Connection;
import java.util.Scanner;

/**
 * @author Licaodong
 * @project exam
 * @date 2023/8/1 22:25
 **/
public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入登录账号和密码：");

        String sql = "insert into users values(?,?,?,?)";
        Connection poolConnection = DbUtil.getPoolConnection();
        int i = DbUtil.executeSql(poolConnection,sql, DbUtil.getId(), scanner.next(), scanner.next(), DbUtil.getDate());
        System.out.println(i > 0 ? "插入数据成功" : "插入数据失败");

        /*
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入登录账号和密码：");
        String sql = "select * from users where name = ? and pwd = ?";

        com.cetc28.DbUtils.DbObject dbObject = com.cetc28.DbUtils.DbUtils.executeQuery(sql, scanner.next(), scanner.next());
        ResultSet resultSet = dbObject.getResultSet();
        try {
            System.out.println(resultSet.next() ? "登录成功" : "登录失败");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            dbObject.close();
        }*/


    }
}
