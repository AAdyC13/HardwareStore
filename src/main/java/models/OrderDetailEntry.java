package models;

public class OrderDetailEntry {

    private int id;
    private String order_id;
    private String product_id;
    private int quantity;
    private int product_price;
    private String product_name;

    public OrderDetailEntry(String product_id, String product_name, int product_price, int quantity) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.quantity = quantity;
    }

    
    public OrderDetailEntry() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return order_id;
    }

    public void setOrderId(String order_id) {
        this.order_id = order_id;
    }

    public String getProductId() {
        return product_id;
    }

    public void setProductId(String product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductPrice() {
        return product_price;
    }

    public void setProductPrice(int product_price) {
        this.product_price = product_price;
    }

    public String getProductName() {
        return product_name;
    }

    public void setProductName(String product_name) {
        this.product_name = product_name;
    }


}
