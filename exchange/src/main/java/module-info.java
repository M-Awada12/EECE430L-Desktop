module com.awada.exchange {
    requires javafx.controls;
    requires javafx.fxml;
    requires retrofit2;
    requires java.sql;
    requires gson;
    requires retrofit2.converter.gson;

    opens com.awada.exchange to javafx.fxml;
    opens com.awada.exchange.api.model to javafx.base, gson;
    exports com.awada.exchange;
    exports com.awada.exchange.rates;
    opens com.awada.exchange.rates to javafx.fxml;
    requires java.prefs;
    opens com.awada.exchange.login to javafx.fxml;
    opens com.awada.exchange.register to javafx.fxml;
    opens com.awada.exchange.transactions to javafx.fxml;
}