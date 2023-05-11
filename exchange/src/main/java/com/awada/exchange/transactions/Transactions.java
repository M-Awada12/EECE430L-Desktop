package com.awada.exchange.transactions;

import com.awada.exchange.Authentication;
import com.awada.exchange.api.ExchangeService;
import com.awada.exchange.api.model.Transaction;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Transactions implements Initializable {
    public TableColumn<Transaction, Long> lbpAmount;
    public TableColumn<Transaction, Long> usdAmount;
    public TableColumn<Transaction, String> transactionDate;
    public TableView<Transaction> tableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbpAmount.setCellValueFactory(new PropertyValueFactory<>("lbpAmount"));
        usdAmount.setCellValueFactory(new PropertyValueFactory<>("usdAmount"));
        transactionDate.setCellValueFactory(new PropertyValueFactory<>("addedDate"));

        ExchangeService.exchangeApi().getTransactions("Bearer " + Authentication.getInstance().getToken())
                .enqueue(new Callback<List<Transaction>>() {
                    @Override
                    public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                        if (response.isSuccessful()) {
                            List<Transaction> transactions = response.body();
                            if (transactions != null) {
                                tableView.getItems().setAll(transactions);
                            }
                        } else {
                            // Handle API response failure
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Transaction>> call, Throwable throwable) {
                        // Handle network or API call failure
                    }
                });
    }
}
