package servletclass;

// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

// Extend HttpServlet class
public class member extends HttpServlet {
 
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/missa?useSSL=false&serverTimezone=UTC&characterEncoding=utf-8";

    static final String USER = "root";
    static final String PASS = "H9183149316";
    private String message;
    public String output = "";

    public void init() throws ServletException {
        // Do required initialization
        message = "Hello World";
    }

   public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
      
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            
            // open connect
            System.out.println("Connect to DB...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        
            // Excution
            System.out.println("SQL Commands...");
            stmt = conn.createStatement();
            String sql;
            
            sql = "SELECT id, name, url FROM websites";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                int id  = rs.getInt("id");
                String name = rs.getString("name");
                String url = rs.getString("url");
                output += name;
    
                System.out.print("ID: " + id);
                System.out.print(", 網站名稱: " + name);
                System.out.print(", 網站 URL: " + url);
                System.out.print("\n");
            }


        
            // finaish to close
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // JDBC ERROR
            se.printStackTrace();
        }catch(Exception e){
            // Class.forName ERROR
            e.printStackTrace();
        }finally{
            // Close Connection
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            } // Nothing to do
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
      // Actual logic goes here.
      PrintWriter out = response.getWriter();
      out.println("<h1>" + output + "</h1>");
   }

   public void destroy() {
      // do nothing.
   }
}