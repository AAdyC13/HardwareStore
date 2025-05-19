package models;

import db.ProductDbManagerSimple;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class ReadProductFromDB {

    public static TreeMap<String, Product> readProduct() {

     
        
                // 創建資料庫管理類別實例
        ProductDbManagerSimple productManager = new ProductDbManagerSimple();
        
        // 測試獲取所有產品
        System.out.println("===== 測試獲取所有產品 =====");
        List<Product> allProducts = productManager.getAllProducts();
        System.out.println("總共取得 " + allProducts.size() + " 個產品");
        
        // 列印前5個產品作為範例
        int count = 0;
        for (Product product : allProducts) {
            if (count++ < 5) {
                System.out.println("產品ID: " + product.getProduct_id() + 
                                  ", 名稱: " + product.getName() + 
                                  ", 類別: " + product.getCategory() + 
                                  ", 價格: " + product.getPrice());
            }
        }
        
        // 測試獲取所有產品類別
        System.out.println("\n===== 測試獲取所有產品類別 =====");
        String[] categories = productManager.getCategories();
        System.out.println("總共取得 " + categories.length + " 個產品類別");
        for (String category : categories) {
            System.out.println("類別: " + category);
        }
        
        // 測試以TreeMap獲取所有產品
        System.out.println("\n===== 測試以TreeMap獲取所有產品 =====");
        TreeMap<String, Product> productMap = productManager.getProducts();
        System.out.println("TreeMap中的產品數: " + productMap.size());
        
        // 列印TreeMap中的第一個和最後一個產品
        if (!productMap.isEmpty()) {
            String firstKey = productMap.firstKey();
            String lastKey = productMap.lastKey();
            
            System.out.println("第一個產品 (ID: " + firstKey + "): " + 
                              productMap.get(firstKey).getName());
            System.out.println("最後一個產品 (ID: " + lastKey + "): " + 
                              productMap.get(lastKey).getName());
        }
        
        
        
        
        return productMap;
    }

    public static void main(String[] args) {

        System.out.println(ReadProductFromDB.readProduct());

    }

}
