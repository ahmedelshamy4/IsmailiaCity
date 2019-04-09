package com.example.ahmed.ismaliacityapp.network;

import android.app.Application;

import com.example.ahmed.ismaliacityapp.utils.Constant;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SetupService extends Application {
    public static Service service;

    @Override
    public void onCreate() {
        super.onCreate();
//        OkHttpClient.Builder client = new OkHttpClient.Builder();
//
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        client.addInterceptor(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
           //     .client(client.build())
                .build();

        service = retrofit.create(Service.class);
    }
}
