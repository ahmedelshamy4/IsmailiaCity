package com.example.ahmed.ismaliacityapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ahmed.ismaliacityapp.R;
import com.example.ahmed.ismaliacityapp.model.Coffes;
import com.example.ahmed.ismaliacityapp.ui.DetailCoffeesActivity;
import com.example.ahmed.ismaliacityapp.utils.Constant;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CoffeeRecyclerAdapter extends RecyclerView.Adapter<CoffeeRecyclerAdapter.CoffeesViewHolder> {
    private Context context;
    private ArrayList<Coffes> coffes;

    public CoffeeRecyclerAdapter(Context context, ArrayList<Coffes> coffes) {
        this.context = context;
        this.coffes = coffes;
    }

    @NonNull
    @Override
    public CoffeesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_coffees, viewGroup, false);
        return new CoffeesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoffeesViewHolder holder, int position) {
        holder.name.setText(coffes.get(position).getName());
        holder.ratingBar.setRating(coffes.get(position).getRating());
        Glide.with(context).load(coffes.get(position).getIcon()).into(holder.icone);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailCoffeesActivity.class);
                intent.putExtra(Constant.Intent_Detail_coffee, (Parcelable) coffes);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (coffes != null) return coffes.size();
        else return 0;
    }

    public class CoffeesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_thumb)
        CircleImageView icone;
        @BindView(R.id.tv_coffees)
        TextView name;
        @BindView(R.id.coffe_ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.car_view_coffees)
        CardView cardView;


        public CoffeesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
