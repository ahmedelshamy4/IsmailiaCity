package com.example.ahmed.ismaliacityapp.network;

import com.example.ahmed.ismaliacityapp.utils.Constant;
import com.example.ahmed.ismaliacityapp.model.RespondCoffee;
import com.example.ahmed.ismaliacityapp.model.Weather;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Service {

    @GET("json?key=501227138edb49009dd183118192403&q=ismailia")
    Call<Weather> getCurrentWeather();

    @GET("json?query=ismalia+coffe&key=AIzaSyDgChPwVs86RFhov7SZwCKNDU27XlBxCRM")
    Call<RespondCoffee> getCoffeesCall();

}
