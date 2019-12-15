package ncu.im3069.Group16.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.json.JSONArray;
import org.json.JSONObject;

import ncu.im3069.demo.util.DBMgr;

public class TeacherHelper {
	/**
   * 實例化（Instantiates）一個新的（new）ParentHelper物件<br>
   * 採用Singleton不需要透過new
   */
  private TeacherHelper() {
      
  }
  
  /** 靜態變數，儲存ParentHelper物件 */
	private static TeacherHelper th;
	
	/** 儲存JDBC資料庫連線 */
  private Connection conn = null;
  
  /** 儲存JDBC預準備之SQL指令 */
  private PreparedStatement pres = null;
  
  /***靜態方法
   *	實作Singleton（單例模式），僅允許建立一個TeacherHelper物件
	 *
	 * @return the helper 回傳TeacherHelper物件
   */
	public static TeacherHelper getHelper(){	//1212 3pm by min
		/** Singleton檢查是否已經有TeacherHelper物件，若無則new一個，若有則直接回傳 */
		if(th == null) th = new TeacherHelper();
		
		return th;
	}
	
	/* 透過會員編號（ID）刪除老師會員
	 * 
	 * @param id 會員編號
	 * @return the JSONObject 回傳SQL執行結果
	 */
	public JSONObject deleteByID(int id) {	//1212 3pm by min
      /** 記錄實際執行之SQL指令 */
      String exexcute_sql = "";
      /** 紀錄程式開始執行時間 */
      long start_time = System.nanoTime();
      /** 紀錄SQL總行數 */
      int row = 0;
      /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
      ResultSet rs = null;
      
      try {
          /** 取得資料庫之連線 */
          conn = DBMgr.getConnection();
          
          /** SQL指令 */ //1212 6pm 這要再想想要改掉嗎！！！
          String sql = "DELETE FROM `sa16`.`teachers` WHERE `id` = ? LIMIT 1";
          
          /** 將參數回填至SQL指令當中 */
          pres = conn.prepareStatement(sql);
          pres.setInt(1, id);	//把id塞進pres中的第一個問號 by min
          /** 執行刪除之SQL指令並記錄影響之行數 */
          row = pres.executeUpdate();

          /** 紀錄真實執行的SQL指令，並印出 **/
          exexcute_sql = pres.toString();
          System.out.println(exexcute_sql);
          
      } catch (SQLException e) {
          /** 印出JDBC SQL指令錯誤 **/
          System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
      } catch (Exception e) {
          /** 若錯誤則印出錯誤訊息 */
          e.printStackTrace();
      } finally {
          /** 關閉連線並釋放所有資料庫相關之資源 **/
          DBMgr.close(rs, pres, conn);
      }

      /** 紀錄程式結束執行時間 */
      long end_time = System.nanoTime();
      /** 紀錄程式執行時間 */
      long duration = (end_time - start_time);
      
      /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
      JSONObject response = new JSONObject();
      response.put("sql", exexcute_sql);
      response.put("row", row);
      response.put("time", duration);

      return response;
  }
	
	/*
	* *取回所有老師會員資料
	*
	* @return the JSONObject 回傳SQL執行結果與自資料庫取回之所有資料
	*/
  public JSONObject getAll() {		//1212 3pm by min
      /** 新建一個 Parent 物件之 p 變數，用於紀錄每一位查詢回之老師會員資料 */
      Teacher t = null;
      /** 用於儲存所有檢索回之老師會員，以JSONArray方式儲存 */
      JSONArray jsa = new JSONArray();
      /** 記錄實際執行之SQL指令 */
      String exexcute_sql = "";
      /** 紀錄程式開始執行時間 */
      long start_time = System.nanoTime();
      /** 紀錄SQL總行數 */
      int row = 0;
      /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
      ResultSet rs = null;
      
      try {
          /** 取得資料庫之連線 */
          conn = DBMgr.getConnection();
          /** SQL指令 */
          String sql = "SELECT * FROM `sa16`.`teachers`";
          
          /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
          pres = conn.prepareStatement(sql);
          /** 執行查詢之SQL指令並記錄其回傳之資料 */
          rs = pres.executeQuery();

          /** 紀錄真實執行的SQL指令，並印出 **/
          exexcute_sql = pres.toString();
          System.out.println(exexcute_sql);
          
          /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
          while(rs.next()) {
              /** 每執行一次迴圈表示有一筆資料 */
              row += 1;
              
              /** 將 ResultSet 之資料取出
               * 裡面參數放DB的欄位名 
               */
              int parent_id = rs.getInt("id");
              String name = rs.getString("name");
              String email = rs.getString("email");
              String password = rs.getString("password");
              String cellphone = rs.getString("cellphone");
              int gender = rs.getInt("gender");
              
              /** 將每一筆老師會員資料產生一名新Parent物件 建構子2 */
              t = new Teacher(parent_id, name, email, password, cellphone, gender);
              /** 取出該名老師會員之資料並封裝至 JSONsonArray 內 */
              jsa.put(t.getData());
          }

      } catch (SQLException e) {
          /** 印出JDBC SQL指令錯誤 **/
          System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
      } catch (Exception e) {
          /** 若錯誤則印出錯誤訊息 */
          e.printStackTrace();
      } finally {
          /** 關閉連線並釋放所有資料庫相關之資源 **/
          DBMgr.close(rs, pres, conn);
      }
      
      /** 紀錄程式結束執行時間 */
      long end_time = System.nanoTime();
      /** 紀錄程式執行時間 */
      long duration = (end_time - start_time);
      
      /** 將SQL指令、花費時間、影響行數與所有老師會員資料之JSONArray，封裝成JSONObject回傳 */
      JSONObject response = new JSONObject();
      response.put("sql", exexcute_sql);
      response.put("row", row);
      response.put("time", duration);
      response.put("data", jsa);

      return response;
  }
  
  /*
	* *透過會員編號（ID）取得會員資料
	*
	* @param id 會員編號
	* @return the JSON object 回傳SQL執行結果與該會員編號之會員資料
	*/
  public JSONObject getByID(String id) {		//1212 4pm by min
      /** 新建一個 Teacher 物件之 t 變數，用於紀錄每一位查詢回之會員資料 */
      Teacher t = null;
      /** 用於儲存所有檢索回之會員，以JSONArray方式儲存 */
      JSONArray jsa = new JSONArray();
      /** 記錄實際執行之SQL指令 */
      String exexcute_sql = "";
      /** 紀錄程式開始執行時間 */
      long start_time = System.nanoTime();
      /** 紀錄SQL總行數 */
      int row = 0;
      /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
      ResultSet rs = null;
      
      try {
          /** 取得資料庫之連線 */
          conn = DBMgr.getConnection();
          /** SQL指令 */
          String sql = "SELECT * FROM `sa16`.`parents` WHERE `id` = ? LIMIT 1";
          
          /** 將參數回填至SQL指令當中 */
          pres = conn.prepareStatement(sql);
          pres.setString(1, id);
          /** 執行查詢之SQL指令並記錄其回傳之資料 */
          rs = pres.executeQuery();

          /** 紀錄真實執行的SQL指令，並印出 **/
          exexcute_sql = pres.toString();
          System.out.println(exexcute_sql);
          
          /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
          /** 正確來說資料庫只會有一筆該會員編號之資料，因此其實可以不用使用 while 迴圈 */
          while(rs.next()) {
              /** 每執行一次迴圈表示有一筆資料 */
              row += 1;
              
              /** 將 ResultSet 之資料取出 */
              int parent_id = rs.getInt("id");
              String name = rs.getString("name");
              String email = rs.getString("email");
              String password = rs.getString("password");
              String cellphone = rs.getString("cellphone");
              int gender = rs.getInt("gender");
              
              /** 將每一筆會員資料產生一名新Teacher物件 */
              t = new Teacher(parent_id, name, email, password, cellphone, gender);
              /** 取出該名會員之資料並封裝至 JSONsonArray 內 */
              jsa.put(t.getData());
          }
          
      } catch (SQLException e) {
          /** 印出JDBC SQL指令錯誤 **/
          System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
      } catch (Exception e) {
          /** 若錯誤則印出錯誤訊息 */
          e.printStackTrace();
      } finally {
          /** 關閉連線並釋放所有資料庫相關之資源 **/
          DBMgr.close(rs, pres, conn);
      }
      
      /** 紀錄程式結束執行時間 */
      long end_time = System.nanoTime();
      /** 紀錄程式執行時間 */
      long duration = (end_time - start_time);
      
      /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
      JSONObject response = new JSONObject();
      response.put("sql", exexcute_sql);
      response.put("row", row);
      response.put("time", duration);
      response.put("data", jsa);

      return response;
  }
  
  /*
	* *檢查該名老師會員之電子郵件信箱是否重複註冊
	*
	* @param m 一名老師會員之Teacher物件
	* @return boolean 若重複註冊回傳False，若該信箱不存在則回傳True
	*/
  public boolean checkDuplicate(Teacher t){	//1212 4pm by min
      /** 紀錄SQL總行數，若為「-1」代表資料庫檢索尚未完成 */
      int row = -1;
      /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
      ResultSet rs = null;
      
      try {
          /** 取得資料庫之連線 */
          conn = DBMgr.getConnection();
          /** SQL指令 */
          String sql = "SELECT count(*) FROM `sa16`.`teachers` WHERE `email` = ?";
          
          /** 取得所需之參數 */
          String email = t.getEmail();
          
          /** 將參數回填至SQL指令當中 */
          pres = conn.prepareStatement(sql);
          pres.setString(1, email);
          /** 執行查詢之SQL指令並記錄其回傳之資料 */
          rs = pres.executeQuery();

          /** 讓指標移往最後一列，取得目前有幾行在資料庫內 */
          rs.next();
          row = rs.getInt("count(*)");
          System.out.print(row);

      } catch (SQLException e) {
          /** 印出JDBC SQL指令錯誤 **/
          System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
      } catch (Exception e) {
          /** 若錯誤則印出錯誤訊息 */
          e.printStackTrace();
      } finally {
          /** 關閉連線並釋放所有資料庫相關之資源 **/
          DBMgr.close(rs, pres, conn);
      }
      
      /*
       * *判斷是否已經有一筆該電子郵件信箱之資料
       * *若無一筆則回傳False，否則回傳True 
       */
      return (row == 0) ? false : true;
  }
  
  /*
	 * *建立該名老師會員進DB
   *
   * @param p 一名老師會員之Parent物件(建構子1)
   * @return the JSON object 回傳SQL指令執行之結果
   */
  public JSONObject create(Teacher t) {	//1212 4pm by min
      /** 記錄實際執行之SQL指令 */
      String exexcute_sql = "";
      /** 紀錄程式開始執行時間 */
      long start_time = System.nanoTime();
      /** 紀錄SQL總行數 */
      int row = 0;
      
      try {
          /** 取得資料庫之連線 */
          conn = DBMgr.getConnection();
          /** SQL指令 */
          String sql = "INSERT INTO `sa16`.`teachers`(`name`,`email`,`password`,`cellphone`,`gender`,`modified`, `created`)"
                  + " VALUES(?, ?, ?, ?, ?, ?, ?)";
          
          /** 取得所需之參數 */
          String name = t.getName();
          String email = t.getEmail();
          String password = t.getPassword();
          String cellphone = t.getCellphone();
          int gender = t.getGender();
          
          /** 將參數回填至SQL指令當中 */
          pres = conn.prepareStatement(sql);
          pres.setString(1, name);
          pres.setString(2, email);
          pres.setString(3, password);
          pres.setString(4, cellphone);
          pres.setInt(5, gender);
          pres.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
          pres.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
          
          /** 執行新增之SQL指令並記錄影響之行數 */
          row = pres.executeUpdate();
          
          /** 紀錄真實執行的SQL指令，並印出 **/
          exexcute_sql = pres.toString();
          System.out.println(exexcute_sql);

      } catch (SQLException e) {
          /** 印出JDBC SQL指令錯誤 **/
          System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
      } catch (Exception e) {
          /** 若錯誤則印出錯誤訊息 */
          e.printStackTrace();
      } finally {
          /** 關閉連線並釋放所有資料庫相關之資源 **/
          DBMgr.close(pres, conn);
      }

      /** 紀錄程式結束執行時間 */
      long end_time = System.nanoTime();
      /** 紀錄程式執行時間 */
      long duration = (end_time - start_time);

      /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
      JSONObject response = new JSONObject();
      response.put("sql", exexcute_sql);
      response.put("time", duration);
      response.put("row", row);

      return response;
  }
  
  /**
	 * *更新一名老師會員之會員資料
   *
   * @param t 一名老師會員之Teacher物件
   * @return the JSONObject 回傳SQL指令執行結果與執行之資料
   */
  public JSONObject update(Teacher t) {	//1212 3pm, by min
      /** 紀錄回傳之資料 */ //下面都沒改道它，是在??? by min
      JSONArray jsa = new JSONArray();	
      /** 記錄實際執行之SQL指令 */
      String exexcute_sql = "";
      /** 紀錄程式開始執行時間 */ //這行應該不用 by min
      long start_time = System.nanoTime();
      /** 紀錄SQL總行數 */ //這行應該不用 by min
      int row = 0;
      
      try {
          /** 取得資料庫之連線 */
          conn = DBMgr.getConnection();
          /** SQL指令 */
          String sql = "Update `sa16`.`teachers` SET `password` = ? ,`cellphone` = ? , `modified` = ? WHERE `email` = ?";
          /** 取得所需之參數 */
          String email = t.getEmail();
          String password = t.getPassword();
          String cellphone = t.getCellphone();
          
          /** 將參數回填至SQL指令當中 */
          pres = conn.prepareStatement(sql);
          pres.setString(1, password);
          pres.setString(2, cellphone);
          pres.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
          pres.setString(4, email);
          /** 執行更新之SQL指令並記錄影響之行數 */ //這行應該也不用 by min
          row = pres.executeUpdate();
          
          /** 紀錄真實執行的SQL指令，並印出 **/
          exexcute_sql = pres.toString();
          System.out.println(exexcute_sql);

      } catch (SQLException e) {
      	/** 印出JDBC SQL指令錯誤 **/
      	System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
      } catch (Exception e) {
      	/** 若錯誤則印出錯誤訊息 */
      	e.printStackTrace();
      } finally {
      	/** 關閉連線並釋放所有資料庫相關之資源 **/
      	DBMgr.close(pres, conn);
      }
      
      /** 紀錄程式結束執行時間 */
      long end_time = System.nanoTime();
      /** 紀錄程式執行時間 */
      long duration = (end_time - start_time);
      
      /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
      JSONObject response = new JSONObject();
      response.put("sql", exexcute_sql);
      response.put("row", row);
      response.put("time", duration);
      response.put("data", jsa);
      
      return response;
  }
}