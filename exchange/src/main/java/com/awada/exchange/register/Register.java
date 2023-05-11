package com.awada.exchange.register;

import com.awada.exchange.Authentication;
import com.awada.exchange.OnPageCompleteListener;
import com.awada.exchange.api.ExchangeService;
import com.awada.exchange.api.model.Token;
import com.awada.exchange.api.model.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register {
    public TextField usernameTextField;
    public TextField passwordTextField;
    private OnPageCompleteListener onPageCompleteListener;

    public void handleRegistration(ActionEvent actionEvent) {
        User user = new User(usernameTextField.getText(), passwordTextField.getText());
        ExchangeService.exchangeApi().addUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                ExchangeService.exchangeApi().authenticate(user).enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        if (response.isSuccessful()) {
                            Token token = response.body();
                            Authentication.getInstance().saveToken(token.getToken());
                            Platform.runLater(onPageCompleteListener::onPageCompleted);
                        } else {
                            // Handle authentication failure
                        }
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable throwable) {
                        // Handle authentication failure
                    }
                });
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                // Handle user registration failure
            }
        });
    }

    public void setOnPageCompleteListener(OnPageCompleteListener onPageCompleteListener) {
        this.onPageCompleteListener = onPageCompleteListener;
    }
}
