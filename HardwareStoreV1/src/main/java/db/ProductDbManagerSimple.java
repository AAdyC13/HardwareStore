package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import models.Product;

/**
 * 產品資料表管理類別
 * 專門負責產品資料的 CRUD 操作
 */
public class ProductDbManagerSimple extends DatabaseConnection {
    
    
    /**
     * 獲取所有產品
     */
    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM products";
        List<Product> products = new ArrayList<>();
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Product product = new Product();
                product.setProduct_id(rs.getString("product_id"));
                product.setCategory(rs.getString("category"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getInt("price"));
                product.setPhoto(rs.getString("image_url"));
                product.setDescription(rs.getString("description"));
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println("獲取所有產品錯誤: " + e.getMessage());
        }
        return products;
    }
    
    /**
     * 從資料庫獲取所有產品類別
     * @return 產品類別陣列
     */
    public String[] getCategories() {
        List<Product> products = this.getAllProducts();
        List<String> categories = new ArrayList<>();
        
        // 使用 stream 收集不重複的類別
        categories = products.stream()
                .map(Product::getCategory)
                .distinct()
                .collect(Collectors.toList());
        
        return categories.toArray(new String[0]);
    }
    
    /**
     * 從資料庫獲取所有產品，並轉換為 TreeMap 格式
     * @return 以產品ID為鍵的產品 TreeMap
     */
    public TreeMap<String, Product> getProducts() {
        List<Product> productList = this.getAllProducts();
        TreeMap<String, Product> productMap = new TreeMap<>();
        
        // 將列表轉換為 TreeMap
        for (Product product : productList) {
            productMap.put(product.getProduct_id(), product);
        }
        
        return productMap;
    }
    
}
