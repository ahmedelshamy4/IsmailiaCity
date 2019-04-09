package com.example.ahmed.ismaliacityapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RespondCoffee {
    @SerializedName("results")
    ArrayList<Coffes> coffes;

    public ArrayList<Coffes> getCoffees() {
        return coffes;
    }


}
