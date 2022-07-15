package com.codewithnaveen.JevanKhana.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codewithnaveen.JevanKhana.Models.mealType;
import com.codewithnaveen.JevanKhana.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MealTypeAdapter extends RecyclerView.Adapter<MealTypeAdapter.ViewHolder>{

    private ArrayList<mealType> mealTypeArrayList;
    private Context context;
    private final OnItemClickListener listener;

    public MealTypeAdapter(ArrayList<mealType> mealTypeArrayList, Context context, OnItemClickListener listener) {
        this.mealTypeArrayList = mealTypeArrayList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MealTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MealTypeAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_meal_type, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mealType meal = mealTypeArrayList.get(position);
        holder.Name.setText(meal.getName());
        Picasso.get().load(meal.getUrl()).into(holder.img);
        holder.bind(meal, listener);
    }


    @Override
    public int getItemCount() {
        return mealTypeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Name;
        private ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.mealname);
            img = itemView.findViewById(R.id.mealurl);
        }

        public void bind(mealType meal, OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(meal);
                }
            });
        }
    }
}
