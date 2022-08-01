package com.codewithnaveen.JevanKhana.Adapters;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.codewithnaveen.JevanKhana.Models.ExtendedIngredient;
import com.codewithnaveen.JevanKhana.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ingredientsAdapter extends RecyclerView.Adapter<ingredientsViewHolder>{

    Context context;
    List<ExtendedIngredient> list;
    TextView tfname,tdesc,consistency,unit,measures;
    ImageView tfpic;

    public ingredientsAdapter(Context context, List<ExtendedIngredient> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ingredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ingredientsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_meal_ingredient,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ingredientsViewHolder holder, int position) {
        holder.ingredient_name.setText(list.get(position).name);
        holder.ingredient_name.setSelected(true);
        holder.ingredient_quantity.setText(list.get(position).original);
        holder.ingredient_quantity.setSelected(true);
        Picasso.get().load("https://spoonacular.com/cdn/ingredients_500x500/"+list.get(position).image).into(holder.ingredient_img);
        holder.ingredient_img.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context,R.style.BottomSheetDialogTheme);
            View bottomsheetView = LayoutInflater.from(context).inflate(R.layout.bottomsheet_ingredient,(LinearLayout) v.findViewById(R.id.bottomSheetContainer));
            tfname = bottomsheetView.findViewById(R.id.name);
            tdesc = bottomsheetView.findViewById(R.id.description);
            consistency = bottomsheetView.findViewById(R.id.consistency);
            unit = bottomsheetView.findViewById(R.id.unit);
            tfpic = bottomsheetView.findViewById(R.id.fpic);
            measures = bottomsheetView.findViewById(R.id.measure);
            tfname.setText(list.get(position).name);
            Picasso.get().load("https://spoonacular.com/cdn/ingredients_250x250/"+list.get(position).image).into(tfpic);
            tdesc.setText(list.get(position).original);
            consistency.setText(list.get(position).consistency);
            unit.setText(list.get(position).unit);
            measures.setText(list.get(position).measures.metric.unitLong);
            bottomSheetDialog.setContentView(bottomsheetView);
            bottomSheetDialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class ingredientsViewHolder extends RecyclerView.ViewHolder{
    TextView ingredient_quantity,ingredient_name;
    ImageView ingredient_img;

    public ingredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        ingredient_img = itemView.findViewById(R.id.ingredient_img);
        ingredient_name = itemView.findViewById(R.id.ingredient_name);
        ingredient_quantity = itemView.findViewById(R.id.ingredient_quantity);

    }
}
