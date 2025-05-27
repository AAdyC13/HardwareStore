package mypos;

import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TableColumn;
import models.OrderItem;

public class TableViewExample {

    private TableView<OrderItem> table;

    public TableView getTable() {
        table = new TableView<>();

        // 創建列並設置為不可編輯
        table.setEditable(false);
        //表格最後一欄是空白，不要顯示!
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<OrderItem, Integer> idCol = new TableColumn<>("項次");
        idCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getOrderId()).asObject());
        idCol.setMaxWidth(1000);

        TableColumn<OrderItem, String> nameCol = new TableColumn<>("名稱");
        nameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));

        TableColumn<OrderItem, Integer> priceCol = new TableColumn<>("單價");
        priceCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getPrice()).asObject());
        priceCol.setMaxWidth(1000);

        TableColumn<OrderItem, Integer> countCol = new TableColumn<>("件數");
        countCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getCount()).asObject());
        countCol.setMaxWidth(1000);

        TableColumn<OrderItem, Integer> totalCol = new TableColumn<>("總價");
        totalCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getTotal()).asObject());

        table.getColumns().addAll(idCol, nameCol, priceCol, countCol, totalCol);

        return table;
    }
}
