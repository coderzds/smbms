package com.pink.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

//操作数据库的公共类
public class BaseDao {
    private static final String driver;
    private static final String url;
    private static final String username;
    private static final String password;

    //静态代码块，类加载的时候就初始化
    static {
        Properties properties = new Properties();
        //通过类加载器读取资源
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");

        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");

    }

    //获取数据库的连接
    public static Connection getConnection(){
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    //编写查询公共方法
    public static ResultSet execute(Connection conn,String sql,Object[] params,ResultSet resultSet,PreparedStatement preparedStatement) throws SQLException {
        //预编译的sql，在后面直接执行就可以了
        preparedStatement = conn.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            //setObject，占位符，从1开始但是我们的数组是从0开始
            preparedStatement.setObject(i+1,i);
        }
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    //编写增删改工具方法
    public static int execute(Connection conn,String sql,Object[] params,PreparedStatement preparedStatement) throws SQLException {
        preparedStatement = conn.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            //setObject，占位符，从1开始但是我们的数组是从0开始
            preparedStatement.setObject(i+1,i);
        }

        return preparedStatement.executeUpdate();
    }

    //释放资源
    public static boolean closeResource(Connection conn,PreparedStatement preparedStatement,ResultSet resultSet){
        boolean flag = true;

        if(resultSet!=null){
            try {
                resultSet.close();
                //GC回收
                resultSet = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
                //GC回收
                preparedStatement = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        if(conn!=null){
            try {
                conn.close();
                //GC回收
                conn = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }
}
