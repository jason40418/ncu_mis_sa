package ncu.im3069.demo.util;

import java.sql.*;
import java.time.LocalDateTime;

import org.json.JSONObject;

import ncu.im3069.demo.app.Member;

public class MemberInsert {
	
	public MemberInsert() {
		
	}
	
	public boolean checkDuplicate(Member m){
		Connection conn = DBMgr.getConnection();
        PreparedStatement pres = null;
        ResultSet rs = null;
        
        int row = -1;
        
        try {
        	String sql = "SELECT count(*) FROM `missa`.`members` WHERE `email` = ?";
        	pres = conn.prepareStatement(sql);
        	
        	String email = m.getEmail();
        	pres.setString(1, email);
            rs = pres.executeQuery();

            // 讓指標移往最後一列
            rs.next();
            row = rs.getInt("count(*)");

        } catch (SQLException e) {
            // JDBC ERROR
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBMgr.close(rs, pres, conn);
        }
        
        return (row == 0) ? false : true;
    }
	
	public JSONObject create(Member m) {
		Connection conn = DBMgr.getConnection();
        PreparedStatement pres = null;
        ResultSet rs = null;
        
        String exexcute_sql = "";
        int row = 0;
        long start_time = System.nanoTime();
        
        try {
        	String sql = "INSERT INTO `missa`.`members`(`name`, `email`, `password`, `modified`, `created`) VALUES(?, ?, ?, ?, ?)";
        	pres = conn.prepareStatement(sql);
        	String name = m.getName();
            String email = m.getEmail();
            String password = m.getPassword();
            pres.setString(1, name);
            pres.setString(2, email);
            pres.setString(3, password);
            pres.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            pres.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
        	row = pres.executeUpdate();
        	
        	// 紀錄真實執行的SQL指令
            exexcute_sql = pres.toString();

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

        // 將SQL指令、花費時間與影響行數，組成JSON物件回傳
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("time", duration);
        response.put("row", row);

        return response;
    }

}
