package ncu.im3069.demo.app;

import org.json.*;

public class Product {
	private int id;
	private String name;
	private double price;
	private String image;
	private String describe;
	
	public Product(int id) {
		this.id = id;
	}
	
	public Product(String name, double price, String image) {
		this.name = name;
		this.price = price;
		this.image = image;
	}
	
	public Product(int id, String name, double price, String image, String describe) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.image = image;
		this.describe = describe;
	}
	
	public int getID() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public String getImage() {
		return this.image;
	}
	
	public String getDescribe() {
		return this.describe;
	}
	
	public JSONObject getData() {
        /** 透過JSONObject將該項產品所需之資料全部進行封裝*/ 
        JSONObject jso = new JSONObject();
        jso.put("id", getID());
        jso.put("name", getName());
        jso.put("price", getPrice());
        jso.put("image", getImage());
        jso.put("describe", getDescribe());
        
        return jso;
    }
}
