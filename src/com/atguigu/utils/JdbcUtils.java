package com.atguigu.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {

    //创建数据库连接池
    // 注意static关键字，dataSource属于JdbcUtils类，后面的几个方法都是静态方法，包括静态初始化块。静态方法可以直接用类名调用
    private static DruidDataSource dataSource;
    private static ThreadLocal<Connection> conns = new ThreadLocal<>();

    static {
        try {
            Properties properties = new Properties();
            //读取jdbc.properties属性配置文件
            //getClassLoader()：获取当前根目录下的某个文件的URL路径，得到文件的输入流
            InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            //从流中加载数据
            properties.load(inputStream);
            //创建数据库连接池
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);

//            System.out.println(dataSource.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取数据库连接池中的链接
     *
     * @return 如果返回null，说明返回失败，有值就是成功
     */
    public static Connection getConnection() {

        Connection conn = conns.get();
        if (conn == null){
            try {
                conn = dataSource.getConnection();//数据库获取连接
                conns.set(conn); // 保存到Threadlocal对象中，供后面jdbc操作使用
                conn.setAutoCommit(false); // 设置为手动管理事务
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return conn;
    }

    /**
     * 提交事务，并关闭释放连接
     */
    public static void commitAndClose(){
        Connection connection = conns.get();
        if (connection != null){//不等于null  说明之前使用过连接，操作过数据库
            try {
                connection.commit(); // 提交事务
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    connection.close(); // 关闭连接。资源释放
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        //一定要执行remove操作，否则会出错（因为Tomcat服务器底层使用了线程技术)
        conns.remove();
    }

    /**
     * 回滚事务，并关闭释放连接
     */
    public static void rollbackAndClose(){
        Connection connection = conns.get();
        if (connection != null){//不等于null  说明之前使用过连接，操作过数据库
            try {
                connection.rollback(); // 回滚事务
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    connection.close(); // 关闭连接。资源释放
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        //一定要执行remove操作，否则会出错（因为Tomcat服务器底层使用了线程技术)
        conns.remove();
    }

    /**
     * 关闭连接，放回数据库连接池
     *
     * @param conn
     */
    /*public static void close(Connection conn) {
        if(conn != null ){
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }*/
}
