package com.cetc28.DbUtils;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.Date;

/**
 * @author Licaodong
 * @project exam
 * @date 2023/8/1 22:29
 **/
public class DbUtil {
    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;
    public static final String POOLCLASS;

    static{
        ResourceBundle bundle=ResourceBundle.getBundle("db");
        DRIVER=bundle.getString("jdbc.driver");
        URL=bundle.getString("jdbc.url");
        USERNAME=bundle.getString("jdbc.username");
        PASSWORD=bundle.getString("jdbc.password");
        POOLCLASS = bundle.getString("hikari.pool.class");
    }

    /**
     * 获取UUID
     * @return String
     */
    public static String getId(){
        return UUID.randomUUID().toString()
                .replace("-","");
    }

    /**
     * 获取java.sql.Date类型的当前时间
     * @return java.sql.Date
     */
    public static java.sql.Date getDate(){
//        LocalDate date=LocalDate.now();
//        String d=date.format(DateTimeFormatter.ofPattern("dd-M月 -yy"));
//        return d;
        Date date = new Date();
        long timeInMilliSeconds = date.getTime();
        java.sql.Date date1 = new java.sql.Date(timeInMilliSeconds);
        System.out.println(date1);
        return date1;
    }

    /**
     * 获得数据库驱动连接
     * @return 一个连接对象
     */
    public static Connection getConnection(){
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得连接池数据源
     * @return
     */
    public static DataSource getPoolDataSource(){
        try {
            Class<?> aClass = Class.forName(POOLCLASS);
            Object o = aClass.getConstructor().newInstance();
            Method setDriverClassName = aClass.getMethod("setDriverClassName", String.class);
            Method setUsername = aClass.getMethod("setUsername", String.class);
            Method setPassword = aClass.getMethod("setPassword", String.class);
            setDriverClassName.invoke(o,DRIVER);
            setUsername.invoke(o,USERNAME);
            setPassword.invoke(o,PASSWORD);

            Method setUrl = null;
            if(aClass.getName().equals(DruidDataSource.class.getName())){
                setUrl = aClass.getMethod("setUrl", String.class);
            }else{
                setUrl = aClass.getMethod("setJdbcUrl",String.class);
            }
            setUrl.invoke(o,URL);
            return (DataSource) o;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getPoolConnection(){
        DataSource dataSource = getPoolDataSource();
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param connection
     * @return Statement对象
     */
    public static Statement openStatement(Connection connection){
        try {
            Statement statement = connection.createStatement();
            return statement;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param statement
     * @param sql
     * @return
     */
    public static int executeUpdate(Statement statement, String sql){
        try {
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     *
     * @param sql
     * @return
     */
    public static int executeUpdate(Connection connection,String sql){
        Statement statement = openStatement(connection);
        int i = executeUpdate(statement,sql);
        close(statement,connection);
        return i;
    }

    /**
     *
     * @param sql
     * @return
     */
    public static DbObject search(Connection connection,String sql){
        Statement statement = openStatement(connection);
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new DbObject(connection,statement,resultSet);
    }

    /**
     * 针对prepareStatement
     * @param sql
     * @param args
     * @return
     */
    public static int executeSql(Connection connection,String sql,Object...args){
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            int index = 1;
            for (Object arg : args) {
                stmt.setObject(index++, arg);
            }
            int i = stmt.executeUpdate();
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(stmt,connection);
        }
        return -1;
    }

    /**
     *
     * @param connection
     * @param sql
     * @param args
     * @return
     */
    public static DbObject executeQuery(Connection connection,String sql,Object...args){

        PreparedStatement stmt=null;
        try {
            stmt= connection.prepareStatement(sql);
            int index=1;
            for (Object arg : args) {
                stmt.setObject(index++,arg);
            }
            ResultSet resultSet=stmt.executeQuery();
            return new DbObject(connection,stmt,resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close(ResultSet resultSet){
        if (resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void close(Statement statement){
        if (statement!=null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void close(Connection connection){
        if (connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void close(Statement statement,Connection connection){
        close(statement);
        close(connection);
    }
    public static void close(ResultSet resultSet, Statement statement, Connection connection){
        close(resultSet);
        close(statement);
        close(connection);
    }
}
