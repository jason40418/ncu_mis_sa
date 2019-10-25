# 中央大學資管系—系統分析與設計範例專案

- Version : 1.0.0
- Last Update Date : 2019/10/25

> 本專案為中央大學資管系系統分析與設計課程之期末範例專案，本專案實做管理員後台管理模組之會員管理模組之功能，其中JsonReader.java是協助使用者能便於取得前端所傳入之JSON格式Request與協助使用者將封裝完成之JSON格式資料回傳Response至前端。

## 相關Package

- 此專案透過Servlet進行實作，前端透過AJAX發送Http Request，後端則是使用Java作為主要程式語言。

- 與資料庫之連線採用JDBC進行連線。

- 以下是本專案所會使用到之相關Package與Class。

    ```java
    /** 若要操作Servlet相關Class */
    import java.io.*;
    import javax.servlet.*;
    import javax.servlet.http.*;
    
    /** 若要操作JSON格式相關物件 e.g. JSONObject和JSONArray*/
    import org.json.*;
    
    /** 若要操作JDBC */
    import java.sql.*;
    import java.util.Properties;
    
    /** 若要取回與發送Http Request和Response（必要） */
    import ncu.im3069.tools.JsonReader;
    ```

## Servlet

- 對於Servlet來說，一個method對應一個Http method

    - doPost()對應POST方法

- 在Eclipse EE版本當中可以直接從上方功能列「File/New/Servlet 」新增Class

    ```java
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // do something in here
    }
    ```

- 在本專案當中，範例檔案實作一項工具協助使用者能快速將JSON格式之資料取回，並且將經商業邏輯處理後之資料以JSON格式回傳至前端「JsonReader.java」

    - 請記得一定要import此工具**「import ncu.im3069.tools.JsonReader;」**才得以使用

    ```java
    import ncu.im3069.tools.JsonReader;
    
    /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
    JsonReader jsr = new JsonReader(request);
    JSONObject jso = jsr.getObject();
    
    /** 方法一：以字串組出JSON格式之資料，並回傳回前端 */
    String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
    jsr.response(resp, response);
    
    /** 方法二：新建一個JSONObject用於將回傳之資料進行封裝，並回傳前端 */
    JSONObject resp = new JSONObject();
    resp.put("status", "200");
    resp.put("message", "成功! 註冊會員資料...");
    resp.put("response", data);
    
    jsr.response(resp, response);
    ```

- Ajax請求之路徑設定

    - 主要是指定哪個Url要由哪個Servlet Class進行處理

    - 可以多個路徑指向相同之class

    - 兩個指定方法

        - 直接在Servlet Class在class名稱上方以「@webservlet("/api/Test.do")」直接指定
            - 可直接透過Eclipse新增之圖形化介面指定
        - 透過「WEB-INF/web.xml」檔案指定

        ```java
        /** 方法一：直接在Servlet Class內指定 */
        @webservlet("/api/Test.do")
        public class Test extends HttpServlet {
            ...
        }
        
        /** 方法二：透過修改web.xml指定 */
        /** 
          * 此兩個為一組，上方為指定處理之class名稱，下方為指定之路徑
          * 其中<servlet-name>必須相同
          */
        <servlet>
            <servlet-name>member</servlet-name>
            <servlet-class>ncu.im3069.demo.controller.MemberController</servlet-class>
        </servlet>
        
        <servlet-mapping>
            <servlet-name>member</servlet-name>
            <url-pattern>/api/member.do</url-pattern>
        </servlet-mapping>
        ```

## MySQL

- 由於MySQL在安裝後預設root僅能透過本地端（localhost）進行連線，無法跨越區域網路，因此必須修改資料表之連線權限，使其能在透過不同IP進行連線

    - 允許外部連線，請盡量使用較複雜之帳號密碼
    - 本範例僅就root帳號進行範例，實際上應使用新建之帳號並賦予對應之操作權限

- 使用者必須執行以下指令：

    ```mysql
    -- 為資料庫操作安全性，預設無法更動相關資料表，因此必須開啟權限
    -- 預設為 on 因此必須更改為 off
    show variables like '%safe_updates%'
    
    -- 將安全性更動關閉
    SET SQL_SAFE_UPDATES=0
    
    -- 更新root使用者連線之host為「%」，允許來自各地IP連線
    UPDATE `user` SET `host` = '%' WHERE `user` = 'root';
    
    -- 檢視該資料表檢視更新後之結果
    SELECT `host`, `user` FROM `user`;
    
    -- 將安全性更動重新開啟
    SET SQL_SAFE_UPDATES=1
    ```

- 設定完成後，請記得重新開機以套用更新

- 建議在實作資料庫連線DBMgr時，可直接採用本專案提供之內容（ ncu.im3069.demo.util.DBMgr.java）

    - 僅需修改連線所需之資料與參數即可

- 透過DBMgr.java之資料庫連線範例，可透過「*Helper」之命名方式區分不同之功能（執行刪除之範例）

    - 執行「新增/刪除/修改指令」其應使用executeUpdate()之方法
    - 其回傳值為整數（int）影響之行數

    ```java
    try {
        /** 取得資料庫之連線 */
        conn = DBMgr.getConnection();
    
        /** SQL指令 */
        String sql = "DELETE FROM `missa`.`members` WHERE `id` = ? LIMIT 1";
    
        /** 將參數回填至SQL指令當中 */
        pres = conn.prepareStatement(sql);
        pres.setInt(1, id);
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
    ```

- 透過DBMgr.java之資料庫連線範例，可透過「*Helper」之命名方式區分不同之功能（執行查詢之範例）

    - 執行「查詢指令」其應使用executeQuery()之方法
    - 其回傳值為ResultSet

    ```java
    try {
        /** 取得資料庫之連線 */
        conn = DBMgr.getConnection();
        /** SQL指令 */
        String sql = "SELECT * FROM `missa`.`members`";
    
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
    
            /** 將 ResultSet 之資料取出 */
            int member_id = rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String password = rs.getString("password");
            int login_times = rs.getInt("login_times");
            String status = rs.getString("status");
    
            /** 將每一筆會員資料產生一名新Member物件 */
            m = new Member(member_id, email, password, name, login_times, status);
            /** 取出該名會員之資料並封裝至 JSONsonArray 內 */
            jsa.put(m.getData());
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
    ```

- 若想要觀看更多範例可查閱DBMgr.java檔案。

## FAQ

1. 錯誤訊息**「java.sql.SQLNonTransientConnectionException: Public Key Retrieval is not allowed」**
    - 表示資料庫之帳號密碼未被正確設定
