package com.xiwa.test.util;


import java.sql.*;


/**
 * Created by xiaobc on 16/2/1.
 */
public final class DBUtils {

    private static String url = "jdbc:mysql://localhost:3306/nvwa_configs";
    private static String user = "root";
    private static String psw = "";

    private static Connection conn;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private DBUtils() {

    }

    /**
     * 获取数据库的连接
     *
     * @return conn
     */
    public static Connection getConnection() {
        if (null == conn) {
            try {
                conn = DriverManager.getConnection(url, user, psw);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return conn;
    }


    /**
     * 创建数据  删除数据
     */
    public static Object insertAndDeleteData(String sql){

        Connection conn = DBUtils.getConnection();
        PreparedStatement ps = null;
//        String sql = "insert into person(name,birthday,sex) values(?,?,?)";
        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int i = ps.executeUpdate();

            // 检索由于执行此 Statement 对象而创建的所有自动生成的键
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                Long id = rs.getLong(1);
                System.out.println("数据主键：" + id);
                if(id>0){
                    return id;
                }
            }
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBUtils.closeResources(conn,ps,null);
        }
    }

    /**
     * 释放资源
     *
     * @param conn
     * @param pstmt
     * @param rs
     */
    public static void closeResources(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        if (null != rs) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                if (null != pstmt) {
                    try {
                        pstmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    } finally {
                        if (null != conn) {
                            try {
                                conn.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }
        }
    }

}
