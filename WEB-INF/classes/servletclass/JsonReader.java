package servletclass;

import javax.servlet.*; 
import javax.servlet.http.*; 
import java.io.*; 
import java.util.*;
import org.json.*;

public class JsonReader {
	private JSONObject requestObj;

	public JsonReader(HttpServletRequest request) throws IOException, UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		// JavaScript need input JSON format string request
		StringBuilder sb = new StringBuilder();
        String s;
        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
		}
		
		//got the full request as string.
        System.out.println(sb.toString());

        this.requestObj = new JSONObject(sb.toString());
	}

	public JSONObject getObject() {
		return this.requestObj;
	}

	public void response(String resp_string, HttpServletResponse response) throws IOException, UnsupportedEncodingException {
		response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
		JSONObject responseObj = new JSONObject(resp_string);
		PrintWriter out = response.getWriter();
        out.println(responseObj);
	}
}