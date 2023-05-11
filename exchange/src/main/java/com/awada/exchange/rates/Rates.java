package com.awada.exchange.rates;

import com.awada.exchange.Authentication;
import com.awada.exchange.api.ExchangeService;
import com.awada.exchange.api.model.ExchangeRates;
import com.awada.exchange.api.model.Transaction;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.DecimalFormat;

public class Rates {
    @FXML
    private Label buyUsdRateLabel;

    @FXML
    private Label sellUsdRateLabel;

    @FXML
    private TextField lbpTextField;

    @FXML
    private TextField usdTextField;

    @FXML
    private ToggleGroup transactionType;

    @FXML
    private TextField amountTextField;

    @FXML
    private ToggleGroup calcTransactionType;

    @FXML
    private Label calcResult;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public void initialize() {
        fetchRates();
    }

    private void fetchRates() {
        ExchangeService.exchangeApi().getExchangeRates().enqueue(new Callback<ExchangeRates>() {
            @Override
            public void onResponse(Call<ExchangeRates> call, Response<ExchangeRates> response) {
                ExchangeRates exchangeRates = response.body();
                Platform.runLater(() -> {
                    buyUsdRateLabel.setText(df.format(exchangeRates.lbpToUsd) + "  LBP");
                    sellUsdRateLabel.setText(df.format(exchangeRates.usdToLbp) + "  LBP");
                });
            }

            @Override
            public void onFailure(Call<ExchangeRates> call, Throwable throwable) {
                // Handle failure
            }
        });
    }

    @FXML
    private void addTransaction(ActionEvent event) {
        if (transactionType.getSelectedToggle() == null || usdTextField.getText().isEmpty() ||
                lbpTextField.getText().isEmpty() || (Double.parseDouble(usdTextField.getText()) <= 0) ||
                (Double.parseDouble(lbpTextField.getText()) <= 0)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please Fill All Required Fields with Appropriate Input!");
            alert.showAndWait();
            usdTextField.setText("");
            lbpTextField.setText("");
            return;
        }

        Transaction transaction = new Transaction(
                Float.parseFloat(usdTextField.getText()),
                Float.parseFloat(lbpTextField.getText()),
                ((RadioButton) transactionType.getSelectedToggle()).getText().equals("Sell USD")
        );

        String userToken = Authentication.getInstance().getToken();
        String authHeader = userToken != null ? "Bearer " + userToken : null;
        ExchangeService.exchangeApi().addTransaction(transaction, authHeader).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                fetchRates();
                Platform.runLater(() -> {
                    usdTextField.setText("");
                    lbpTextField.setText("");
                });
            }

            @Override
            public void onFailure(Call<Object> call, Throwable throwable) {
                // Handle failure
            }
        });
    }

    @FXML
    private void calculate(ActionEvent event) {
        if (calcTransactionType.getSelectedToggle() == null || amountTextField.getText().isEmpty() ||
                (Double.parseDouble(amountTextField.getText()) <= 0)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please Fill All Required Fields with Appropriate Input!");
            alert.showAndWait();
            amountTextField.setText("");
            calcResult.setText("Result");
            return;
        }

        String amount = amountTextField.getText();
        double rate;
        double convertedAmount;

        if (calcTransactionType.getSelectedToggle() != null) {
            RadioButton selectedRadioButton = (RadioButton) calcTransactionType.getSelectedToggle();
            String selectedValue = selectedRadioButton.getText();

            if (selectedValue.equals("LBP to USD")) {
                String buyUsdRateLabelString = buyUsdRateLabel.getText();
                rate = Double.parseDouble(buyUsdRateLabelString.substring(0, buyUsdRateLabelString.length() - 6));
                convertedAmount = Double.parseDouble(amount) / rate;
                calcResult.setText(df.format(convertedAmount) + " USD");
            } else if (selectedValue.equals("USD to LBP")) {
                String sellUsdRateLabelString = sellUsdRateLabel.getText();
                rate = Double.parseDouble(sellUsdRateLabelString.substring(0, sellUsdRateLabelString.length() - 4));
                convertedAmount = Double.parseDouble(amount) * rate;
                calcResult.setText(df.format(convertedAmount) + " LBP");
            }
        }
    }
}
