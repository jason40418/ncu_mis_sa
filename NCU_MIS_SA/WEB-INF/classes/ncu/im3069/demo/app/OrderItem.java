package ncu.im3069.demo.app;

import org.json.JSONObject;
import ncu.im3069.demo.util.Arith;

public class OrderItem {
    private int id;
    private Product pd;
    private int quantity;
    private double price;
    private double subtotal;
    
    private ProductHelper ph =  ProductHelper.getHelper();
    
    public OrderItem(Product pd, int quantity) {
        this.pd = pd;
        this.quantity = quantity;
        this.price = this.pd.getPrice();
        this.subtotal = Arith.mul((double) this.quantity, this.price);
    }
    
    public OrderItem(int order_product_id, int order_id, int product_id, double price, int quantity, double subtotal) {
        this.id = order_product_id;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
        getProductFromDB(product_id);
    }
    
    private void getProductFromDB(int product_id) {
        String id = String.valueOf(product_id);
        this.pd = ph.getById(id);
    }
    
    public Product getProduct() {
        return this.pd;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    
    public double getPrice() {
        return this.price;
    }
    
    public double getSubTotal() {
        return this.subtotal;
    }
    
    public int getQuantity() {
        return this.quantity;
    }
    
    public JSONObject getData() {
        JSONObject data = new JSONObject();
        data.put("id", getId());
        data.put("product", getProduct().getData());
        data.put("price", getPrice());
        data.put("quantity", getQuantity());
        data.put("subtotal", getSubTotal());
        
        return data;
    }
}
