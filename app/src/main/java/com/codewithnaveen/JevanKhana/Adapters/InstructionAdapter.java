package com.codewithnaveen.JevanKhana.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codewithnaveen.JevanKhana.Models.InstructionResponse;
import com.codewithnaveen.JevanKhana.R;

import java.util.List;

public class InstructionAdapter extends RecyclerView.Adapter<InstructionViewHolder>{

    Context context;
    List<InstructionResponse> list;

    public InstructionAdapter(Context context, List<InstructionResponse> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InstructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instruction,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionViewHolder holder, int position) {
        holder.instruction_name.setText(list.get(position).name);
        holder.recycler_meal_Instruction.setHasFixedSize(true);
        holder.recycler_meal_Instruction.setNestedScrollingEnabled(false);
        holder.recycler_meal_Instruction.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        InstructionStepAdapter instructionStepAdapter = new InstructionStepAdapter(context,list.get(position).steps);
        holder.recycler_meal_Instruction.setAdapter(instructionStepAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class InstructionViewHolder extends RecyclerView.ViewHolder {

    TextView instruction_name;
    RecyclerView recycler_meal_Instruction;
    public InstructionViewHolder(@NonNull View itemView) {
        super(itemView);
        instruction_name = itemView.findViewById(R.id.Instruction_name);
        recycler_meal_Instruction = itemView.findViewById(R.id.recycler_meal_Instruction);
    }
}