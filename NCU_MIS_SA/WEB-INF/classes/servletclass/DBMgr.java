package servletclass;

import java.time.*;
import java.sql.*;
import org.json.*;

public class DBMgr {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/missa?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8";
    static final String USER = "root";
    static final String PASS = "123456";

    private Connection conn;
    private PreparedStatement pre_stmt;

    public DBMgr() {
        this.conn = null;
        this.pre_stmt = null;
    }

    public JSONObject updateMember(Member m) {
        JSONArray jsa = new JSONArray();
        String exexcute_sql = "";
        long start_time = System.nanoTime();
        int row = 0;
        try {
            Class.forName(JDBC_DRIVER);
            this.conn = DriverManager.getConnection(DBMgr.DB_URL, DBMgr.USER, DBMgr.PASS);

            String sql = "Update `missa`.`members` SET `name` = ? ,`password` = ? , `modified` = ? WHERE `email` = ?";
            this.pre_stmt = conn.prepareStatement(sql);
            String name = m.getName();
            String email = m.getEmail();
            String password = m.getPassword();
            this.pre_stmt.setString(1, name);
            this.pre_stmt.setString(2, password);
            this.pre_stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            this.pre_stmt.setString(4, email);

            row = this.pre_stmt.executeUpdate();

            // 紀錄真實執行的SQL指令
            exexcute_sql = this.pre_stmt.toString();
            System.out.println(exexcute_sql);
        } catch (SQLException e) {
            // JDBC ERROR
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            // Class.forName ERROR
            e.printStackTrace();
        }
        long end_time = System.nanoTime();
        long duration = (end_time - start_time);

        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }

    public JSONObject deleteMember(int id) {
        JSONArray jsa = new JSONArray();
        String exexcute_sql = "";
        long start_time = System.nanoTime();
        int row = 0;
        try {
            Class.forName(JDBC_DRIVER);
            this.conn = DriverManager.getConnection(DBMgr.DB_URL, DBMgr.USER, DBMgr.PASS);

            String sql = "DELETE FROM `missa`.`members` WHERE `id` = ? LIMIT 1";
            this.pre_stmt = conn.prepareStatement(sql);
            this.pre_stmt.setInt(1, id);

            row = this.pre_stmt.executeUpdate();

            // 紀錄真實執行的SQL指令
            exexcute_sql = this.pre_stmt.toString();
            System.out.println(exexcute_sql);
        } catch (SQLException e) {
            // JDBC ERROR
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            // Class.forName ERROR
            e.printStackTrace();
        }
        long end_time = System.nanoTime();
        long duration = (end_time - start_time);

        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }
    
    public JSONObject getMember(String id) {
        JSONArray jsa = new JSONArray();
        String exexcute_sql = "";
        long start_time = System.nanoTime();
        int row = 0;
        try {
            Class.forName(JDBC_DRIVER);
            this.conn = DriverManager.getConnection(DBMgr.DB_URL, DBMgr.USER, DBMgr.PASS);

            String sql = "SELECT `name`, `email`, `password` FROM `missa`.`members` WHERE `id` = ? LIMIT 1";
            this.pre_stmt = conn.prepareStatement(sql);
            this.pre_stmt.setString(1, id);

            ResultSet rs = this.pre_stmt.executeQuery();

            // 紀錄真實執行的SQL指令
            exexcute_sql = this.pre_stmt.toString();

            while(rs.next()) {
                row += 1;
                JSONObject jso = new JSONObject();
                String name  = rs.getString("name");
                jso.put("name", name);

                String email = rs.getString("email");
                jso.put("email", email);

                String password = rs.getString("password");
                jso.put("password", password);

                jsa.put(jso);
            }
        } catch (SQLException e) {
            // JDBC ERROR
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            // Class.forName ERROR
            e.printStackTrace();
        }
        long end_time = System.nanoTime();
        long duration = (end_time - start_time);

        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }
    
    public JSONObject getAllMembers() {
        JSONArray jsa = new JSONArray();
        String exexcute_sql = "";
        long start_time = System.nanoTime();
        int row = 0;
        try {
            Class.forName(JDBC_DRIVER);
            this.conn = DriverManager.getConnection(DBMgr.DB_URL, DBMgr.USER, DBMgr.PASS);

            String sql = "SELECT `id`, `name`, `email` FROM `missa`.`members`";
            this.pre_stmt = conn.prepareStatement(sql);

            ResultSet rs = this.pre_stmt.executeQuery();

            // 紀錄真實執行的SQL指令
            exexcute_sql = this.pre_stmt.toString();

            while(rs.next()) {
                row += 1;
                JSONObject jso = new JSONObject();
                String id  = rs.getString("id");
                jso.put("id", id);

                String name = rs.getString("name");
                jso.put("name", name);

                String email = rs.getString("email");
                jso.put("email", email);

                jsa.put(jso);
            }
        } catch (SQLException e) {
            // JDBC ERROR
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            // Class.forName ERROR
            e.printStackTrace();
        }
        long end_time = System.nanoTime();
        long duration = (end_time - start_time);

        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }

    public boolean checkDuplicateMember(Member m){
        int row = -1;
        try {
            Class.forName(JDBC_DRIVER);
            this.conn = DriverManager.getConnection(DBMgr.DB_URL, DBMgr.USER, DBMgr.PASS);

            String sql = "SELECT count(*) FROM `missa`.`members` WHERE `email` = ?";
            this.pre_stmt = conn.prepareStatement(sql);

            String email = m.getEmail();
            pre_stmt.setString(1, email);

            ResultSet rs = this.pre_stmt.executeQuery();

            // 讓指標移往最後一列
            rs.next();
            row = rs.getInt("count(*)");
        } catch (SQLException e) {
            // JDBC ERROR
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            // Class.forName ERROR
            e.printStackTrace();
        }

        if (row == 0) {
            return false;
        }
        else {
            return true;
        }
    }

    public JSONObject createMember(Member m) {
        String exexcute_sql = "";
        int row = 0;
        long start_time = System.nanoTime();
        try {
            Class.forName(JDBC_DRIVER);
            // open connect
            this.conn = DriverManager.getConnection(DBMgr.DB_URL, DBMgr.USER, DBMgr.PASS);

            // 使用Prepare Statement
            String sql = "INSERT INTO `missa`.`members`(`name`, `email`, `password`, `modified`, `created`) VALUES(?, ?, ?, ?, ?)";
            this.pre_stmt = conn.prepareStatement(sql);

            String name = m.getName();
            String email = m.getEmail();
            String password = m.getPassword();
            this.pre_stmt.setString(1, name);
            this.pre_stmt.setString(2, email);
            this.pre_stmt.setString(3, password);
            this.pre_stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            this.pre_stmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
        
            // 執行並記錄影響的列數
            row = this.pre_stmt.executeUpdate();

            // 紀錄真實執行的SQL指令
            exexcute_sql = this.pre_stmt.toString();

        } catch (SQLException e) {
            // JDBC ERROR
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            // Class.forName ERROR
            e.printStackTrace();
        }

        long end_time = System.nanoTime();
        long duration = (end_time - start_time);

        // 將SQL指令、花費時間與影響行數，組成JSON物件回傳
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("time", duration);
        response.put("row", row);

        return response;
    }

}