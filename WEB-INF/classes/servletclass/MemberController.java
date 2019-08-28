package servletclass;

// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;
import java.sql.*;

// Extend HttpServlet class
public class member extends HttpServlet {
 
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/missa?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8";

    static final String USER = "root";
    static final String PASS = "H9183149316";
    private String message;
    public String output = "";

    public void init() throws ServletException {
        // Do required initialization
        message = "Hello World";
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // 取回Request Json
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        // 取出JSON Object的值
        String account = jso.getString("account");
        String password = jso.getString("password");
        String name = jso.getString("name");
        int gender = jso.getInt("gender");
        JSONArray ja_favorite = jso.getJSONArray("favorite");
        
        // 將JSONArray轉換成String
        String favorite = "";
        if (ja_favorite != null) { 
            for (int i=0 ; i<ja_favorite.length() ; i++){ 
                favorite += ja_favorite.getString(i);
                if (i != ja_favorite.length()-1)
                    favorite += "、";
            }
        } 

        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            // open connect
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        
            // Excution
            stmt = conn.createStatement();
            String sql;
            sql = "INSERT INTO `user`(account, password, name, male, favorite) VALUES(\'" + account + "\',\'" + password + "\',\'" + name + "\',\'" + Integer.toString(gender) + "\',\'" + favorite + "\')";
            System.out.println(sql);
            stmt.executeUpdate(sql);

            // finaish to close
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            // JDBC ERROR
            se.printStackTrace();
        } catch(Exception e) {
            // Class.forName ERROR
            e.printStackTrace();
        } finally{
            // Close Connection
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {

            } // Nothing to do
            
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            }
        }
        
        String resp = "{\"status\": \'200\', \"name\": \'" + account + "\', \"message\": 新增成功}";
        jsr.response(resp, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
      
        Connection conn = null;
        Statement stmt = null;
        JSONArray jsa = new JSONArray();

        try {
            Class.forName(JDBC_DRIVER);
            // open connect
            System.out.println("Connect to DB...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        
            // Excution
            System.out.println("SQL Commands...");
            stmt = conn.createStatement();
            String sql;
            
            sql = "SELECT * FROM `user`";
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()) {
                JSONObject jso = new JSONObject();
                int id  = rs.getInt("id");
                System.out.println(id);
                jso.put("ID", id);

                String account = rs.getString("account");
                System.out.println(account);
                jso.put("account", account);

                String name = rs.getString("name");
                System.out.println(name);
                jso.put("name", name);

                String male = rs.getString("male");
                System.out.println(male);
                jso.put("male", male);

                String favorite = rs.getString("favorite");
                System.out.println(favorite);
                jso.put("favorite", favorite);

                jsa.put(jso);
            }
            
            // finaish to close
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch(Exception e) {
            // Class.forName ERROR
            e.printStackTrace();
        } finally{
            // Close Connection
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {

            } // Nothing to do
            
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            }
        }

        JSONObject answer = new JSONObject();
        answer.put("status", "200");
        answer.put("data", jsa);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        out.println(answer);
    }

   public void destroy() {
      // do nothing.
   }
}