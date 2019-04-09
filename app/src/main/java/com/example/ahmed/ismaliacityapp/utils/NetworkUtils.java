package com.example.ahmed.ismaliacityapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.ahmed.ismaliacityapp.network.Service;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Chaitanya Pinapaka on 06-Dec-18.
 */

public class NetworkUtils {

    public static boolean checkConnectivity(Context context) {
        ConnectivityManager connection = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connection.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
