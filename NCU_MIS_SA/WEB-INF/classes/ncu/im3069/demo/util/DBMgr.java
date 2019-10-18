package ncu.im3069.demo.util;

import java.sql.*;
import java.util.Properties;

public class DBMgr {
	
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    //static final String DB_URL = "jdbc:mysql://localhost:3306/missa?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8";
	static final String DB_URL = "jdbc:mysql://localhost:3306/missa";
	static final String USER = "root";
    static final String PASS = "123456";
    
	static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public DBMgr() {
		
    }
	
	public static Connection getConnection() {
        Properties props = new Properties();
        props.setProperty("useSSL", "false");
		props.setProperty("serverTimezone", "UTC");
		props.setProperty("useUnicode", "true");
		props.setProperty("characterEncoding", "utf8");
		props.setProperty("user", DBMgr.USER);
		props.setProperty("password", DBMgr.PASS);

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(DBMgr.DB_URL, props);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
	
	public static void close(ResultSet rs, Statement stm, Connection conn) {

        try {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}