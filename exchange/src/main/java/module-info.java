module com.awada.exchange {
    requires javafx.controls;
    requires javafx.fxml;
    requires retrofit2;
    requires java.sql;
    requires gson;
    requires retrofit2.converter.gson;
    opens com.awada.exchange to javafx.fxml;
    opens com.awada.exchange.api.model to gson;
    exports com.awada.exchange;
}
