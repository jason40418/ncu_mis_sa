package ncu.im3069.demo.util;

import java.sql.*;
import java.time.LocalDateTime;

import org.json.*;

import ncu.im3069.demo.app.Member;

public class MemberUpdate {
	
	public MemberUpdate() {
		
	}
	
	public JSONObject updateMember(Member m) {
		Connection conn = DBMgr.getConnection();
        PreparedStatement pres = null;
        ResultSet rs = null;
        
        JSONArray jsa = new JSONArray();
        String exexcute_sql = "";
        long start_time = System.nanoTime();
        int row = 0;
        
        try {
        	String sql = "Update `missa`.`members` SET `name` = ? ,`password` = ? , `modified` = ? WHERE `email` = ?";
        	String name = m.getName();
            String email = m.getEmail();
            String password = m.getPassword();
        	
        	pres = conn.prepareStatement(sql);
        	pres.setString(1, name);
        	pres.setString(2, password);
        	pres.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
        	pres.setString(4, email);
        	row = pres.executeUpdate();

            // 紀錄真實執行的SQL指令
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

        } catch (SQLException e) {
            // JDBC ERROR
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBMgr.close(rs, pres, conn);
        }
        
        m.addLoginCounter();
        
        long end_time = System.nanoTime();
        long duration = (end_time - start_time);

        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }
	
	public int updateLoginTimes(Member m) {
		int new_times = m.getLoginTimes() + 1;
		Connection conn = DBMgr.getConnection();
        PreparedStatement pres = null;
        ResultSet rs = null;
        
        String exexcute_sql;
		
		try {
        	String sql = "Update `missa`.`members` SET `login_times` = ? WHERE `id` = ?";
        	int id = m.getID();
        	
        	pres = conn.prepareStatement(sql);
        	pres.setInt(1, new_times);
        	pres.setInt(2, id);
        	pres.executeUpdate();

            // 紀錄真實執行的SQL指令
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

        } catch (SQLException e) {
            // JDBC ERROR
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBMgr.close(rs, pres, conn);
        }
		
		return new_times;
	}
	
	public int updateStatus(Member m, String status) {
		int new_times = m.getLoginTimes() + 1;
		Connection conn = DBMgr.getConnection();
        PreparedStatement pres = null;
        ResultSet rs = null;
        
        String exexcute_sql;
		
		try {
        	String sql = "Update `missa`.`members` SET `status` = ? WHERE `id` = ?";
        	int id = m.getID();
        	
        	pres = conn.prepareStatement(sql);
        	pres.setString(1, status);
        	pres.setInt(2, id);
        	pres.executeUpdate();

            // 紀錄真實執行的SQL指令
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

        } catch (SQLException e) {
            // JDBC ERROR
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBMgr.close(rs, pres, conn);
        }
		
		return new_times;
	}

}
