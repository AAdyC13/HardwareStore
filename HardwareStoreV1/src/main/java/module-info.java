module mypos {
    requires javafx.controls;
    requires javafx.base;
    requires javafx.graphics;
    
    //Database
    requires java.sql; //資料庫
    
    //專案內部的套件模組必須開放給其他模組使用
    exports models;
    exports db;
    exports mypos;
    

}
