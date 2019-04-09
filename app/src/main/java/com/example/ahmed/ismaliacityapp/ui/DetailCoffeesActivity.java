package com.example.ahmed.ismaliacityapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ahmed.ismaliacityapp.R;
import com.example.ahmed.ismaliacityapp.fragment.FragmentDetailCoffee;
import com.example.ahmed.ismaliacityapp.model.Coffes;
import com.example.ahmed.ismaliacityapp.utils.Constant;

public class DetailCoffeesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_coffees);
        Coffes coffes = getIntent().getParcelableExtra(Constant.Intent_Detail_coffee);
        getSupportFragmentManager().beginTransaction().replace(R.id.detailContainer
                , FragmentDetailCoffee.newInstance(coffes)).commit();
    }
}
