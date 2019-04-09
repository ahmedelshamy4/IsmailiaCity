package com.example.ahmed.ismaliacityapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ahmed.ismaliacityapp.R;
import com.example.ahmed.ismaliacityapp.ui.CoffeeActivity;


public class CategoryFragment extends Fragment implements View.OnClickListener {
    private ImageView Coffee, Hospital, Restaurant, Schools, Hotels, Cinema, Shoping;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        Coffee = view.findViewById(R.id.image_coffe);
        Hospital = view.findViewById(R.id.image_hospital);
        Restaurant = view.findViewById(R.id.image_resturant);
        Schools = view.findViewById(R.id.image_school);
        Hotels = view.findViewById(R.id.image_hotels);
        Cinema = view.findViewById(R.id.image_cinema);
        Shoping = view.findViewById(R.id.image_shoping);

        Coffee.setOnClickListener(this);
        Hospital.setOnClickListener(this);
        Restaurant.setOnClickListener(this);
        Schools.setOnClickListener(this);
        Hospital.setOnClickListener(this);
        Hotels.setOnClickListener(this);
        Cinema.setOnClickListener(this);
        Shoping.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_coffe:
                Intent intent = new Intent(getContext(), CoffeeActivity.class);
                startActivity(intent);
                break;
            case R.id.image_hospital:
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, new BlankFragment());
                transaction.addToBackStack(null);
                transaction.commit();
                ;
                break;
        }
    }
}
