package com.example.ahmed.ismaliacityapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ahmed.ismaliacityapp.R;
import com.example.ahmed.ismaliacityapp.model.Weather;
import com.example.ahmed.ismaliacityapp.network.Service;
import com.example.ahmed.ismaliacityapp.network.SetupService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    public static final String BASE_URL = "https://api.apixu.com/v1/";

    @BindView(R.id.day_text)
    TextView day_text;
    @BindView(R.id.month_text)
    TextView month_text;
    @BindView(R.id.temp_text)
    TextView temp_text;
    @BindView(R.id.city_text)
    TextView city_text;
    @BindView(R.id.weather_icon)
    ImageView weather_icon;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(getActivity());

        getWeatherUpdateCall();
        return view;
    }

    private void getWeatherUpdateCall() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<Weather> call = service.getCurrentWeather();
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if (response.body() != null) {
                    TimeZone t = TimeZone.getDefault();
                    Calendar c = Calendar.getInstance(t);
                    Locale locale = Locale.getDefault();
                    day_text.setText(c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale));
                    month_text.setText(c.getDisplayName(Calendar.MONTH, Calendar.LONG, locale));
                    day_text.setText(String.valueOf(response.body().getText()));
                    month_text.setText(String.valueOf(response.body().getTime()));

                    Double temp = response.body().getTemp();
                    temp = temp * (9.0 / 5) - 459.67;   //Convert temp to fahrenheit
                    String tempConv = String.format("%.0f", temp);
                    temp_text.setText(String.valueOf(tempConv) + "˚");

                    city_text.setText(String.valueOf(response.body().getText()));
                    if (weather_icon != null) {
                        Glide.with(getContext()).load(response.body().getIcon()).into(weather_icon);
                    }


                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Toast.makeText(getContext(), "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
