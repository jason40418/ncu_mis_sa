package ncu.im3069.demo.app;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import org.json.*;

public class Order {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String address;
    private String phone;
    private ArrayList<OrderItem> list = new ArrayList<OrderItem>();
    private Timestamp create;
    private Timestamp modify;
    private OrderItemHelper oph = OrderItemHelper.getHelper();
    
    public Order(String first_name, String last_name, String email, String address, String phone) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.create = Timestamp.valueOf(LocalDateTime.now());
        this.modify = Timestamp.valueOf(LocalDateTime.now());
    }
    
    public Order(int id, String first_name, String last_name, String email, String address, String phone, Timestamp create, Timestamp modify) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.create = create;
        this.modify = modify;
        getOrderProductFromDB();
    }
    
    public void addOrderProduct(Product pd, int quantity) {
        this.list.add(new OrderItem(pd, quantity));
    }
    
    public void addOrderProduct(OrderItem op) {
        this.list.add(op);
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getFirstName() {
        return this.first_name;
    }
    
    public String getLastName() {
        return this.last_name;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public Timestamp getCreateTime() {
        return this.create;
    }
    
    public Timestamp getModifyTime() {
        return this.modify;
    }
    
    public String getAddress() {
        return this.address;
    }
    
    public String getPhone() {
        return this.phone;
    }
    
    public ArrayList<OrderItem> getOrderProduct() {
        return this.list;
    }
    
    private void getOrderProductFromDB() {
        ArrayList<OrderItem> data = oph.getOrderProductByOrderId(this.id);
        this.list = data;
    }
    
    public JSONObject getOrderData() {
        JSONObject jso = new JSONObject();
        jso.put("id", getId());
        jso.put("first_name", getFirstName());
        jso.put("last_name", getLastName());
        jso.put("email", getEmail());
        jso.put("address", getAddress());
        jso.put("phone", getPhone());
        jso.put("create", getCreateTime());
        jso.put("modify", getModifyTime());
        
        return jso;
    }
    
    public JSONArray getOrderProductData() {
        JSONArray result = new JSONArray();
        
        for(int i=0 ; i < this.list.size() ; i++) {
            result.put(this.list.get(i).getData());
        }
        
        return result;
    }
    
    public JSONObject getOrderAllInfo() {
        JSONObject jso = new JSONObject();
        jso.put("order_info", getOrderData());
        jso.put("product_info", getOrderProductData());
        
        return jso;
    }
    
    public void setOrderProductId(JSONArray data) {
        for(int i=0 ; i < this.list.size() ; i++) {
            this.list.get(i).setId((int) data.getLong(i));
        }
    }

}
