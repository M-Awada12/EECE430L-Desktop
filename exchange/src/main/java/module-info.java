module com.mohammadawada.exchange {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.mohammadawada.exchange to javafx.fxml;
    exports com.mohammadawada.exchange;
}