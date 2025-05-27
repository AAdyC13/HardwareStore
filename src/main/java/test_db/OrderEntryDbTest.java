

package test_db;

import java.sql.Timestamp;

import db.OrderDbManagerSimple;
import models.OrderDetail;
import models.SaleOrder;

/**
 * 測試訂單資料庫操作的簡單程式
 */
public class OrderEntryDbTest {
    
    public static void main(String[] args) {
        // 建立資料庫管理器實例
        OrderDbManagerSimple dbManager = new OrderDbManagerSimple();
        
        try {
            System.out.println("開始測試 OrderEntryDbManagerSimple...");
            
            // 使用固定ID建立訂單
            String orderId = "TEST-001";
            
            // 建立訂單
            SaleOrder order = new SaleOrder();
            order.setOrderId(orderId);
            order.setOrderDate(new Timestamp(System.currentTimeMillis()));
            order.setTotalAmount(500.0);
            order.setCustomerId("CUST-001");
            
            // 插入訂單
            boolean orderInserted = dbManager.insertSaleOrder(order);
            System.out.println("訂單插入結果: " + (orderInserted ? "成功" : "失敗"));
            
            // 插入第一筆訂單明細
            OrderDetail detail1 = new OrderDetail();
            detail1.setOrderId(orderId);
            detail1.setProductId("PROD-001");
            detail1.setQuantity(2);
            boolean detail1Inserted = dbManager.insertOrderDetail(detail1);
            System.out.println("訂單明細1插入結果: " + (detail1Inserted ? "成功" : "失敗"));
            
            // 插入第二筆訂單明細
            OrderDetail detail2 = new OrderDetail();
            detail2.setOrderId(orderId);
            detail2.setProductId("PROD-002");
            detail2.setQuantity(3);
            boolean detail2Inserted = dbManager.insertOrderDetail(detail2);
            System.out.println("訂單明細2插入結果: " + (detail2Inserted ? "成功" : "失敗"));
            
            System.out.println("測試完成！");
            
        } catch (Exception e) {
            System.err.println("測試過程中發生錯誤: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 關閉資料庫連接
            try {
                dbManager.close();
                System.out.println("資料庫連接已關閉");
            } catch (Exception e) {
                System.err.println("關閉資料庫連接時發生錯誤: " + e.getMessage());
            }
        }
    }
}
