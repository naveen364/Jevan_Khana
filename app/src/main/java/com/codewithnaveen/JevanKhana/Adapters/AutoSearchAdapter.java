package com.codewithnaveen.JevanKhana.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.codewithnaveen.JevanKhana.Listeners.AutoCompleteSearchListener;
import com.codewithnaveen.JevanKhana.Models.searchItem;
import com.codewithnaveen.JevanKhana.R;
import com.codewithnaveen.JevanKhana.ReciepeDetailActivity;
import com.codewithnaveen.JevanKhana.searchActivity;

import java.util.ArrayList;
import java.util.List;

public class AutoSearchAdapter extends RecyclerView.Adapter<AutoSearchViewHolder>{
    Context context;
    List<searchItem> list ;

    public AutoSearchAdapter(Context context, List<searchItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AutoSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AutoSearchViewHolder(LayoutInflater.from(context).inflate(R.layout.searchlist,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AutoSearchViewHolder holder, int position) {
        holder.text.setText(list.get(position).title);
        holder.text.setOnClickListener(v -> {
            context.startActivity(new Intent(context, ReciepeDetailActivity.class)
                    .putExtra("id",String.valueOf(list.get(position).id)));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class AutoSearchViewHolder extends RecyclerView.ViewHolder {

    TextView text;

    public AutoSearchViewHolder(@NonNull View itemView) {
        super(itemView);
        text = itemView.findViewById(R.id.autosearchitem);
    }
}
