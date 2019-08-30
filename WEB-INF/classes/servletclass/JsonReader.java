package servletclass;

import javax.servlet.*; 
import javax.servlet.http.*; 
import java.io.*; 
import java.util.*;
import org.json.*;

public class JsonReader {
	private String request_string;
	private HttpServletRequest request;

	public JsonReader(HttpServletRequest request) throws IOException, UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		this.request = request;
		// JavaScript need input JSON format string request
		StringBuilder sb = new StringBuilder();
        String s;
        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
		}

		this.request_string = sb.toString();
		System.out.println(request_string);
	}

	public String getParameter(String key) {
		if (this.request.getParameter(key) != null) {
			return this.request.getParameter(key);
		}
		else {
			return "";
		}
	}

	public JSONArray getArray() {
		JSONArray request_array = new JSONArray(this.request_string);
		return request_array;
	}

	public JSONObject getObject() {
		JSONObject request_object = new JSONObject(this.request_string);
		return request_object;
	}

	public void response(String resp_string, HttpServletResponse response) throws IOException, UnsupportedEncodingException {
		response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
		JSONObject responseObj = new JSONObject(resp_string);
		PrintWriter out = response.getWriter();
        out.println(responseObj);
	}

	public void response(JSONObject resp, HttpServletResponse response) throws IOException, UnsupportedEncodingException {
		response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
        out.println(resp);
	}
}