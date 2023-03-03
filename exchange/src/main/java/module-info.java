module com.awada.exchange {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.awada.exchange to javafx.fxml;
    exports com.awada.exchange;
}