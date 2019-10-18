package ncu.im3069.demo.util;

import java.sql.*;

import org.json.*;

public class MemberDelete {
	
	public MemberDelete() {
		
	}
	
	public JSONObject delete(int id) {
		Connection conn = DBMgr.getConnection();
        PreparedStatement pres = null;
        ResultSet rs = null;
		
		JSONArray jsa = new JSONArray();
        String exexcute_sql = "";
        long start_time = System.nanoTime();
        int row = 0;
        
        try {
        	String sql = "DELETE FROM `missa`.`members` WHERE `id` = ? LIMIT 1";
        	
        	pres = conn.prepareStatement(sql);
        	pres.setInt(1, id);
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

        long end_time = System.nanoTime();
        long duration = (end_time - start_time);

        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }
}
