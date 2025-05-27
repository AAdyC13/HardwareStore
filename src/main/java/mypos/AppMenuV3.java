package mypos;

import java.util.TreeMap;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ScrollPane;

import models.Product;
import models.ReadProductFromDB;
import models.OrderBook;
import models.OrderItem;
import db.OrderDbManagerSimple;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import models.OrderDetail;
import models.SaleOrder;

public class AppMenuV3 extends Application {

    //宣告一些顯示參數
    int fitWidth = 200;
    int fitHeight = 200;
    int preRow = 3;
    TableViewExample tableView = new TableViewExample();
    OrderBook orderBook = new OrderBook();
    TableView table = tableView.getTable();
    TextArea display = new TextArea();
    OrderDbManagerSimple dbManager = new OrderDbManagerSimple();
    String orderId = "訂單ID-";

    //1宣告全域變數)並取得三種菜單的磁磚窗格，隨時被取用。
    ScrollPane menuCPU = getProductCategoryMenu("CPU");
    ScrollPane menuGPU = getProductCategoryMenu("顯示卡");
    ScrollPane menuMB = getProductCategoryMenu("主機板");

    //2.宣告一個容器(全域變數) menuContainerPane，裝不同種類的菜單，菜單類別選擇按鈕被按下，立即置放該種類的菜單。
    VBox menuContainerPane = new VBox();

    // 取得類別產品菜單磁磚窗格，置放你需要用到的菜單
    public ScrollPane getProductCategoryMenu(String category) {

        //取得產品清單(呼叫靜態方法取得)
        TreeMap<String, Product> product_dict = ReadProductFromDB.readProduct();
        //磁磚窗格
        TilePane category_menu = new TilePane(); //
        category_menu.setVgap(10);  //垂直間隙
        category_menu.setHgap(10);
        //設定一個 row有X個columns
        category_menu.setPrefColumns(preRow);

        //將產品清單內容一一置放入產品菜單磁磚窗格
        for (String item_id : product_dict.keySet()) {
            //用if選擇產品類別
            if (product_dict.get(item_id).getCategory().equals(category)) {

                //定義新增一筆按鈕
                Button btn = new Button();

                //width, height 按鈕外框的大小，你要自行調整，讓它美觀。沒有設定外框會大小不一不好看
                btn.setPrefSize(fitWidth, fitHeight);
                //btn.setText(product_dict.get(item_id).getName()); //不要顯示文字，顯示圖片就好

                // 創建 Canvas 來繪製圖片
                Canvas canvas = new Canvas(fitWidth, fitHeight);
                GraphicsContext gc = canvas.getGraphicsContext2D();

                //按鈕元件顯示圖片Creating a graphic (image)
                //讀出圖片
                Image img = new Image("/img3/" + product_dict.get(item_id).getPhoto());

                // 開啟平滑縮放，提升圖片品質
                gc.setImageSmoothing(true);
                // 取得原圖尺寸
                double imageWidth = img.getWidth();
                double imageHeight = img.getHeight();

                // 計算縮放比例
                double scaleX = fitWidth / imageWidth;
                double scaleY = fitHeight / imageHeight;
                double scale = Math.min(scaleX, scaleY);

                // 計算縮放後的尺寸
                double scaledWidth = imageWidth * scale;
                double scaledHeight = imageHeight * scale;

                // 計算居中起點
                double x = (fitWidth - scaledWidth) / 2;
                double y = (fitHeight - scaledHeight) / 2;

                // 繪製縮放後且居中的圖片
                gc.drawImage(img, x, y, scaledWidth, scaledHeight);

                // 設置按鈕顯示圖片的 Canvas
                btn.setGraphic(canvas);
                category_menu.getChildren().add(btn);  //放入菜單磁磚窗格

                //定義按鈕事件-->點選一次，就加入購物車，再點選一次，數量要+1
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        //新增一筆訂單
                        //System.out.println(product_dict.get(item_id).getName());

                        // 讓 table 顯示 orderBook 的內容
                        orderBook.addProduct(product_dict.get(item_id));
                        table.setItems(orderBook.toOrderItemList());
                        checkTotal(orderBook.getTotal());
                    }
                });
            }
        }
        // 將 TilePane 放入 ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(category_menu);  // 設定 TilePane 為內容
        scrollPane.setFitToWidth(true);             // 可選：讓內容自動適應寬度
        scrollPane.setFitToHeight(true);            // 可選：讓內容自動適應高度
//        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // 垂直滾動條（需要時顯示）
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // 水平滾動條（需要時顯示）

        return scrollPane;
    }//getProductCategoryMenu()

    //3.多一個窗格(可以用磁磚窗格最方便)置放菜單類別選擇按鈕，置放於主視窗的最上方區域。
    public TilePane getMenuSelectionContainer() {

        //定義"CPU"按鈕
        Button btnJuice = new Button();
        btnJuice.setText("CPU");
        btnJuice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menuContainerPane.getChildren().clear();//先刪除原有的窗格再加入新的類別窗格
                menuContainerPane.getChildren().add(menuCPU);
            }
        });
        //定義"顯示卡"按鈕
        Button btnTea = new Button("顯示卡");
        btnTea.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                menuContainerPane.getChildren().clear();//先刪除原有的窗格再加入新的類別窗格
                menuContainerPane.getChildren().add(menuGPU);
            }
        });
        //定義"主機板"按鈕
        Button btnCoffee = new Button("主機板");
        btnCoffee.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //可以使用呼叫select_category_menu()方式切換菜單類別
                select_category_menu(e);
            }
        });

        //使用磁磚窗格，放置前面三個按鈕
        TilePane conntainerCategoryMenuBtn = new TilePane();
        //conntainerCategoryMenuBtn.setAlignment(Pos.CENTER_LEFT);
        //conntainerCategoryMenuBtn.setPrefColumns(6); //
        conntainerCategoryMenuBtn.setVgap(10);
        conntainerCategoryMenuBtn.setHgap(10);

        conntainerCategoryMenuBtn.getChildren().add(btnJuice);
        conntainerCategoryMenuBtn.getChildren().add(btnTea);
        conntainerCategoryMenuBtn.getChildren().add(btnCoffee);

        return conntainerCategoryMenuBtn;
    } // getMenuSelectionContainer()方法

    // 前述三個類別按鈕可以呼叫以下事件，好處:當類別按鈕有很多可寫程式自動布置
    public void select_category_menu(ActionEvent event) {
        String category = ((Button) event.getSource()).getText();
        menuContainerPane.getChildren().clear();//先刪除原有的窗格再加入新的類別窗格
        switch (category) {
            case "CPU":
                menuContainerPane.getChildren().add(menuCPU);
                break;
            case "顯示卡":
                menuContainerPane.getChildren().add(menuGPU);
                break;
            case "主機板":
                menuContainerPane.getChildren().add(menuMB);
                break;
            default:
                break;
        }
    }// select_category_menu()

    public TilePane getTableMenu() {

        Button delSelected = new Button();
        delSelected.setText("刪除1件選擇的商品");
        delSelected.getStyleClass().addAll("button", "danger");
        delSelected.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                delSelectedProduct(e);
            }
        });

        Button checkout = new Button();
        checkout.setText("結帳");
        checkout.getStyleClass().addAll("btn", "btn-success");
        checkout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    // 建立訂單
                    LocalDateTime nowTime = LocalDateTime.now();
                    SaleOrder order = new SaleOrder();
                    String orderId_local = orderId + nowTime.format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
                    order.setOrderId(orderId_local);
                    order.setOrderDate(java.sql.Timestamp.valueOf(nowTime));
                    order.setTotalAmount(orderBook.getTotal());
                    order.setCustomerId("CUST-001");

                    // 插入訂單
                    boolean orderInserted = dbManager.insertSaleOrder(order);
                    System.out.println("訂單插入結果: " + (orderInserted ? "成功" : "失敗"));

                    // 插入第一筆訂單明細
                    OrderDetail detail1 = new OrderDetail();
                    detail1.setOrderId(orderId_local);
                    detail1.setProductId("PROD-001");
                    detail1.setQuantity(orderBook.getAllQuantity());
                    boolean detail1Inserted = dbManager.insertOrderDetail(detail1);
                    System.out.println("訂單明細1插入結果: " + (detail1Inserted ? "成功" : "失敗"));

                } catch (Exception err) {
                    System.err.println("測試過程中發生錯誤: " + err.getMessage());
                    err.printStackTrace();
                } finally {
                    // 關閉資料庫連接
//                    try {
//                        dbManager.close();
//                        System.out.println("資料庫連接已關閉");
//                    } catch (Exception err) {
//                        System.err.println("關閉資料庫連接時發生錯誤: " + err.getMessage());
//                    }
                }
            }
        });

        TilePane conntainerCategoryMenuBtn = new TilePane();
        conntainerCategoryMenuBtn.setVgap(10);
        conntainerCategoryMenuBtn.setHgap(10);

        conntainerCategoryMenuBtn.getChildren().add(delSelected);
        conntainerCategoryMenuBtn.getChildren().add(checkout);

        return conntainerCategoryMenuBtn;
    }

    public void delSelectedProduct(ActionEvent event) {

        OrderItem selectedItem = (OrderItem) table.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            boolean clear = orderBook.delProduct(selectedItem.getProduct());

            table.setItems(orderBook.toOrderItemList());
            if (clear) {
                table.getSelectionModel().clearSelection();
            } else {
                table.getSelectionModel().select(selectedItem.getOrderId() - 1);
            }
            checkTotal(orderBook.getTotal());
        }
    }

    public TextArea textArea() {
        TextArea display = new TextArea();
        display.setEditable(false);
        display.setText("總金額：請選擇欲購買商品\n");
        display.setPrefWidth(50);         // 設定寬度
        display.setPrefHeight(50);
        return display;

    }

    //計算總金額
    public void checkTotal(int money) {
        //顯示總金額於display
        if (money <= 0) {
            display.setText("總金額：請選擇欲購買商品\n");
        } else {
            String totalmsg = String.format("%s %d\n", "總金額：", Math.round(money));
            display.setText(totalmsg);
        }
    }

    @Override
    public void start(Stage stage) {

        HBox root = new HBox();
        root.getStylesheets().add("/css/bootstrap3.css");
        root.setSpacing(5);
        root.setPadding(new Insets(5, 5, 5, 5));

        //菜單選擇區塊
        VBox selectionPlace = new VBox();
        selectionPlace.setSpacing(5);
        selectionPlace.setPadding(new Insets(5, 1, 5, 5)); //外框內墊片
        selectionPlace.setPrefWidth(650);
        selectionPlace.setPrefHeight(600);
        selectionPlace.getChildren().add(getMenuSelectionContainer());
        // 塞入菜單區塊 預設為menuCPU
        menuContainerPane.getChildren().add(menuCPU);
        //取得菜單磁磚窗格並放入根容器
        selectionPlace.getChildren().add(menuContainerPane);

        //table區塊
        VBox tablePlace = new VBox();
        tablePlace.setSpacing(5);
        tablePlace.setPadding(new Insets(5, 5, 5, 1));
        tablePlace.setPrefWidth(550);
        tablePlace.setPrefHeight(600);
        tablePlace.getChildren().add(getTableMenu());
        tablePlace.getChildren().add(table);

        display.setEditable(false);
        display.setText("總金額：請選擇欲購買商品計算\n");
        tablePlace.getChildren().add(display);

        root.getChildren().add(selectionPlace);
        root.getChildren().add(tablePlace);

        Scene scene = new Scene(root);

        stage.setTitle("硬體商城");
        stage.setScene(scene);
        stage.show();
    }//start()

    public static void main(String[] args) {
        launch(args);
    }//main()
} //class
