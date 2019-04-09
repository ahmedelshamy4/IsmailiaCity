package com.example.ahmed.ismaliacityapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ahmed.ismaliacityapp.R;
import com.example.ahmed.ismaliacityapp.model.Coffes;
import com.example.ahmed.ismaliacityapp.utils.Constant;

public class FragmentDetailCoffee extends Fragment {
    Coffes coffes;

    public static FragmentDetailCoffee newInstance(Coffes coffes) {
        FragmentDetailCoffee detailCoffee = new FragmentDetailCoffee();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.Intent_Detail_coffee, coffes);
        detailCoffee.setArguments(bundle);
        return detailCoffee;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            coffes = getArguments().getParcelable(Constant.Intent_Detail_coffee);
        } catch (Exception e) {
            Log.d("test", e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_coffee, container, false);
    }

}
