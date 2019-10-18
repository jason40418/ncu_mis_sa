package ncu.im3069.demo.util;

import java.sql.*;
import org.json.*;

import ncu.im3069.demo.app.Member;

public class MemberGet {

	public MemberGet() {
		
	}
	
	public JSONObject getAll() {
		Connection conn = DBMgr.getConnection();
        PreparedStatement pres = null;
        ResultSet rs = null;
        Member m = null;
        
        JSONArray jsa = new JSONArray();
        String exexcute_sql = "";
        long start_time = System.nanoTime();
        int row = 0;
        
        try {
        	String sql = "SELECT * FROM `missa`.`members`";
        	pres = conn.prepareStatement(sql);
        	rs = pres.executeQuery();

        	// 紀錄真實執行的SQL指令
            exexcute_sql = pres.toString();

            while(rs.next()) {
                row += 1;
            	int member_id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                int login_times = rs.getInt("login_times");
                String status = rs.getString("status");
                m = new Member(member_id, email, password, name, login_times, status);
                jsa.put(m.getData());
            }

        } catch (SQLException e) {
            // JDBC ERROR
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBMgr.close(rs, pres, conn);
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
	
	public JSONObject getOne(String id) {
		Connection conn = DBMgr.getConnection();
        PreparedStatement pres = null;
        Member m = null;
        ResultSet rs = null;
        JSONArray jsa = new JSONArray();
        
        String exexcute_sql = "";
        int row = 0;
        
        long start_time = System.nanoTime();
        
        try {
        	String sql = "SELECT * FROM `missa`.`members` WHERE `id` = ? LIMIT 1";
        	pres = conn.prepareStatement(sql);
        	pres.setString(1, id);
        	rs = pres.executeQuery();

        	// 紀錄真實執行的SQL指令
            exexcute_sql = pres.toString();
            
            while(rs.next()) {
            	row += 1;
            	int member_id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                int login_times = rs.getInt("login_times");
                String status = rs.getString("status");
                m = new Member(member_id, email, password, name, login_times, status);
                jsa.put(m.getData());
            }
            
        } catch (SQLException e) {
            // JDBC ERROR
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBMgr.close(rs, pres, conn);
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
	
	public JSONObject getLoginTimesStatus(Member m) {
		Connection conn = DBMgr.getConnection();
        PreparedStatement pres = null;
        ResultSet rs = null;
        JSONObject jso = new JSONObject();

        try {
        	String sql = "SELECT * FROM `missa`.`members` WHERE `id` = ? LIMIT 1";
        	pres = conn.prepareStatement(sql);
        	pres.setInt(1, m.getID());
        	rs = pres.executeQuery();
            
            while(rs.next()) {
                int login_times = rs.getInt("login_times");
                String status = rs.getString("status");
                jso.put("login_times", login_times);
                jso.put("status", status);
            }
            
        } catch (SQLException e) {
            // JDBC ERROR
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBMgr.close(rs, pres, conn);
        }

        return jso;
	}
}