package com.codewithnaveen.JevanKhana.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codewithnaveen.JevanKhana.Models.mealType;
import com.codewithnaveen.JevanKhana.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MealTypeAdapter extends RecyclerView.Adapter<MealTypeAdapter.ViewHolder>{

    private final ArrayList<mealType> mealTypeArrayList;
    private final Context context;
    private OnItemClickListener listener;
    private int row_index = -1;
    Boolean stopRecursion = true;

    public MealTypeAdapter(ArrayList<mealType> mealTypeArrayList, Context context) {
        this.mealTypeArrayList = mealTypeArrayList;
        this.context = context;
    }

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
        holder.bind(meal, listener, position);
    }


    @Override
    public int getItemCount() {
        return mealTypeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView Name;
        private final ImageView img;
        LinearLayout row_linearlayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.mealname);
            img = itemView.findViewById(R.id.mealurl);
            row_linearlayout = itemView.findViewById(R.id.mealtypelayout);
        }

        @SuppressLint("ResourceAsColor")
        public void bind(mealType meal, OnItemClickListener listener, int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    row_index = position;
                    notifyDataSetChanged();
                    listener.onItemClick(meal);
                }
            });
            if(position == 0 && stopRecursion){
                row_linearlayout.setBackground(context.getDrawable(R.drawable.selected_circle));
                listener.onItemClick(meal);
                stopRecursion = false;
            }else {
                if (row_index == position) {
                    if(position == 0){
                        row_linearlayout.setBackground(context.getDrawable(R.drawable.circle));
                    }
                    row_linearlayout.setBackground(context.getDrawable(R.drawable.selected_circle));
                    Name.setTextColor(R.color.black);
                } else {
                    Name.setTextColor(R.color.white);
                    row_linearlayout.setBackground(context.getDrawable(R.drawable.circle));
                }
            }

        }
    }
}
