package com.awada.exchange.api;

import com.awada.exchange.api.model.ExchangeRates;
import com.awada.exchange.api.model.Transaction;
import com.awada.exchange.api.model.User;
import com.awada.exchange.api.model.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

import java.util.List;

public interface Exchange {
    @POST("/user")
    Call<User> addUser(@Body User user);
    @POST("/authentication")
    Call<Token> authenticate(@Body User user);
    @GET("/exchangeRate")
    Call<ExchangeRates> getExchangeRates();
    @POST("/transaction")
    Call<Object> addTransaction(@Body Transaction transaction,
                                @Header("Authorization") String authorization);
    @GET("/transactionAndr")
    Call<List<Transaction>> getTransactions(@Header("Authorization") String authorization);
}
