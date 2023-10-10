module com.example.imlogin {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.imlogin to javafx.fxml;
    exports com.example.imlogin;
}