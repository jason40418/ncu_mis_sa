package ncu.im3069.demo.controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

import ncu.im3069.demo.app.Order;
import ncu.im3069.demo.app.Product;
import ncu.im3069.demo.app.ProductHelper;
import ncu.im3069.demo.app.OrderHelper;
import ncu.im3069.tools.JsonReader;

import javax.servlet.annotation.WebServlet;

@WebServlet("/api/order.do")
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ProductHelper ph =  ProductHelper.getHelper();
	private OrderHelper oh =  OrderHelper.getHelper();

    public OrderController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonReader jsr = new JsonReader(request);
        String id = jsr.getParameter("id");
        
        JSONObject resp = new JSONObject();
        if (!id.isEmpty()) {
          JSONObject query = oh.getById(id);
          resp.put("status", "200");
          resp.put("message", "單筆訂單資料取得成功");
          resp.put("response", query);
        }
        else {
          JSONObject query = oh.getAll();

          resp.put("status", "200");
          resp.put("message", "所有訂單資料取得成功");
          resp.put("response", query);
        }
        
        jsr.response(resp, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        String first_name = jso.getString("first_name");
        String last_name = jso.getString("last_name");
        String email = jso.getString("email");
        String address = jso.getString("address");
        String phone = jso.getString("phone");
        JSONArray item = jso.getJSONArray("item");
        JSONArray quantity = jso.getJSONArray("quantity");
        
        Order od = new Order(first_name, last_name, email, address, phone);
        
        for(int i=0 ; i < item.length() ; i++) {
            String product_id = item.getString(i);
            int amount = quantity.getInt(i);
            
            Product pd = ph.getById(product_id);
            od.addOrderProduct(pd, amount);
        }
        
        JSONObject result = oh.create(od);
        od.setId((int) result.getLong("order_id"));
        od.setOrderProductId(result.getJSONArray("order_product_id"));
        
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "訂單新增成功！");
        resp.put("response", od.getOrderAllInfo());
        
        jsr.response(resp, response);
	}

}
