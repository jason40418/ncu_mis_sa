package servletclass;

// Import required java libraries
import javax.servlet.*; 
import javax.servlet.http.*; 
import java.io.*; 
import java.util.*;
import org.json.*;

public class DoAjaxServlet extends HttpServlet {

    private String message;

    public void init() throws ServletException {
    // Do required initialization
        message = "Hello World 123";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
        
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String resp = "你好中文";

        PrintWriter out = response.getWriter();
        out.println(resp);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        String resp = "{\"class\": \'140\', \"name\": \'中文字\', \"obj\": {\"yes\" : 40,\"no\" : 50}}";
        jsr.response(resp, response);
    }
}