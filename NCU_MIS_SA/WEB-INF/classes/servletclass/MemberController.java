package servletclass;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public String output = "";
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // 取回Request Json
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        // 取出JSON Object的值
        String email = jso.getString("email");
        String password = jso.getString("password");
        String name = jso.getString("name");

        Member m = new Member(email, password, name);
        DBMgr dbmgr = new DBMgr();
        
        if(email.isEmpty() || password.isEmpty() || name.isEmpty()) {
            String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
            jsr.response(resp, response);
        }
        // 判斷會員信箱是否有重複
        else if (!dbmgr.checkDuplicateMember(m)) {
            JSONObject data = dbmgr.createMember(m);
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "成功! 註冊會員資料...");
            resp.put("response", data);
            // 回傳可直接給定JSONObject
            jsr.response(resp, response);
        }
        else {
            // 回傳可直接組成JSON格式字串
            String resp = "{\"status\": \'400\', \"message\": \'新增帳號失敗，帳號重複！\', \'response\': \'\'}";
            jsr.response(resp, response);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        JsonReader jsr = new JsonReader(request);
        String id = jsr.getParameter("id");
        
        // 判斷以GET方式傳遞字串是否存在
        if (id.isEmpty()) {
            DBMgr dbmgr = new DBMgr();
            JSONObject query = dbmgr.getAllMembers();
    
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "所有會員資料取得成功");
            resp.put("response", query);
    
            // 回傳可直接給定JSONObject
            jsr.response(resp, response);
        }
        else {
            DBMgr dbmgr = new DBMgr();
            JSONObject query = dbmgr.getMember(id);
    
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "會員資料取得成功");
            resp.put("response", query);
    
            // 回傳可直接給定JSONObject
            jsr.response(resp, response);
        }
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        DBMgr dbmgr = new DBMgr();
        int id = jso.getInt("id");
        JSONObject query = dbmgr.deleteMember(id);

        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "會員移除成功！");
        resp.put("response", query);

        // 回傳可直接給定JSONObject
        jsr.response(resp, response);
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // 取回Request Json
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        // 取出JSON Object的值
        String email = jso.getString("email");
        String password = jso.getString("password");
        String name = jso.getString("name");

        Member m = new Member(email, password, name);
        DBMgr dbmgr = new DBMgr();
        
        JSONObject data = dbmgr.updateMember(m);
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "成功! 更新會員資料...");
        resp.put("response", data);
        // 回傳可直接給定JSONObject
        jsr.response(resp, response);
    }
}