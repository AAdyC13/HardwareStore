package models;

public class OrderItem {
    private final Product product;
    private final OrderInfo info;
    private final int orderId;

    public OrderItem(Product product, OrderInfo info,int id) {
        this.product = product;
        this.info = info;
        this.orderId = id;
    }

    public int getOrderId() {
        return this.orderId;
    }

    public String getName() {
        return product.getName();
    }

    public Product getProduct() {
        return this.product;
    }

    public int getPrice() {
        return product.getPrice();
    }

    public int getCount() {
        return info.getQuantity();
    }

    public int getTotal() {
        return info.getTotalPrice();
    }
}
