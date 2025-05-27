package models;

import java.util.Map;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Comparator;

public class OrderBook {

    private Map<Product, OrderInfo> orderMap;

    public OrderBook() {
        this.orderMap = new TreeMap<>();
    }

    public void addNewProduct(Product e) {
        int total = e.getPrice();
        orderMap.put(e, new OrderInfo(1, total)); // 1件物品，總價 total
    }

    public int getTotal() {
        int totalCount = 0;
        for (OrderInfo i : orderMap.values()) {
            totalCount += i.getTotalPrice();
        }
        return totalCount;
    }
    
    public int getAllQuantity(){
        int counter = 0;
        for  (OrderInfo i : orderMap.values()) {
            counter += i.getQuantity();
            
        }
        return counter;
    }

    public boolean delProduct(Product e) {
        OrderInfo obj = orderMap.get(e);
        int num = obj.getQuantity() - 1;
        if (num <= 0) {
            orderMap.remove(e);
            return true;
        } else {
            obj.setQuantity(num);
            obj.setTotalPrice(num * e.getPrice());
            return false;
        }

    }

    public void addProduct(Product e) {
        if (this.orderMap.containsKey(e)) {
            OrderInfo obj = orderMap.get(e);
            int num = obj.getQuantity() + 1;
            obj.setQuantity(num);
            obj.setTotalPrice(num * e.getPrice());
        } else {
            this.addNewProduct(e);
        }
    }

    public ObservableList<OrderItem> toOrderItemList() {
        ObservableList<OrderItem> list = FXCollections.observableArrayList();
        int counter = 1;
        for (Map.Entry<Product, OrderInfo> entry : orderMap.entrySet()) {
            list.add(new OrderItem(entry.getKey(), entry.getValue(), counter));
            counter++;
        }
        // 排序，根據 OrderItem 的 orderId 值
        FXCollections.sort(list, new Comparator<OrderItem>() {
            @Override
            public int compare(OrderItem o1, OrderItem o2) {
                return Integer.compare(o1.getOrderId(), o2.getOrderId()); // 按 orderId 升序排序
            }
        });
        return list;
    }

}
