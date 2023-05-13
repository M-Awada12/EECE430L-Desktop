package com.awada.exchange.login;

import com.awada.exchange.Authentication;
import com.awada.exchange.OnPageCompleteListener;
import com.awada.exchange.PageCompleter;
import com.awada.exchange.api.ExchangeService;
import com.awada.exchange.api.model.Token;
import com.awada.exchange.api.model.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login implements PageCompleter {
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    private OnPageCompleteListener onPageCompleteListener;

    // Default constructor
    public Login() {
        // Initialize any necessary variables or dependencies
    }

    public Login(TextField usernameTextField, TextField passwordTextField) {
        this.usernameTextField = usernameTextField;
        this.passwordTextField = passwordTextField;
    }

    public void login(ActionEvent actionEvent) {
        User user = new User(usernameTextField.getText(), passwordTextField.getText());
        ExchangeService.exchangeApi().authenticate(user).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                Authentication.getInstance().saveToken(response.body().getToken());
                Platform.runLater(onPageCompleteListener::onPageCompleted);
            }

            @Override
            public void onFailure(Call<Token> call, Throwable throwable) {
                // Handle failure
            }
        });
    }

    public void setOnPageCompleteListener(OnPageCompleteListener onPageCompleteListener) {
        this.onPageCompleteListener = onPageCompleteListener;
    }
}
