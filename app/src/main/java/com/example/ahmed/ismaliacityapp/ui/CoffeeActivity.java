package com.example.ahmed.ismaliacityapp.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ahmed.ismaliacityapp.network.SetupService;
import com.example.ahmed.ismaliacityapp.R;
import com.example.ahmed.ismaliacityapp.adapter.CoffeeRecyclerAdapter;
import com.example.ahmed.ismaliacityapp.model.Coffes;
import com.example.ahmed.ismaliacityapp.model.RespondCoffee;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoffeeActivity extends AppCompatActivity {
    @BindView(R.id.progress_bar)
    AVLoadingIndicatorView progressBar;
    @BindView(R.id.coffees_RV)
    RecyclerView coffeeRecyclerView;

    private CoffeeRecyclerAdapter adapter;
    private ArrayList<Coffes> coffes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        recyclerViewInit();

        if (coffes != null) {
            fetchData();
        }


    }

    private void fetchData() {
        SetupService.service.getCoffeesCall().enqueue(new Callback<RespondCoffee>() {
            @Override
            public void onResponse(Call<RespondCoffee> call, Response<RespondCoffee> response) {
                if (response.body() != null) {
                    coffes = response.body().getCoffees();
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<RespondCoffee> call, Throwable t) {
                Toast.makeText(CoffeeActivity.this, t + "Error", Toast.LENGTH_SHORT).show();
                Log.d("Errror", t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void recyclerViewInit() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        coffeeRecyclerView.setLayoutManager(layoutManager);
        adapter = new CoffeeRecyclerAdapter(this, coffes);
        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(adapter);
        coffeeRecyclerView.setAdapter(alphaInAnimationAdapter);
    }
}

