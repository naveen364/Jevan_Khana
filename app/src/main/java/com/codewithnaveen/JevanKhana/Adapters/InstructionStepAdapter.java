package com.codewithnaveen.JevanKhana.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codewithnaveen.JevanKhana.Models.Step;
import com.codewithnaveen.JevanKhana.R;

import java.util.List;

public class InstructionStepAdapter extends RecyclerView.Adapter<InstructionStepViewHolder> {
    Context context;
    List<Step> list;

    public InstructionStepAdapter(Context context, List<Step> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InstructionStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionStepViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instruction_steps,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionStepViewHolder holder, int position) {
        holder.Instruction_step_title.setText(list.get(position).step);
        holder.instruction_step_number.setText("Step:"+ list.get(position).number);
        holder.recycler_Instruction_ingredent.setHasFixedSize(true);
        holder.recycler_Instruction_ingredent.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        InstructionIngredientAdapter instructionIngredientAdapter = new InstructionIngredientAdapter(context,list.get(position).ingredients);
        holder.recycler_Instruction_ingredent.setAdapter(instructionIngredientAdapter);
        holder.recycler_Instruction_equipment.setHasFixedSize(true);
        holder.recycler_Instruction_equipment.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        InstructionEquipmentAdapter instructionEquipmentAdapter = new InstructionEquipmentAdapter(context,list.get(position).equipment);
        holder.recycler_Instruction_equipment.setAdapter(instructionEquipmentAdapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class InstructionStepViewHolder extends RecyclerView.ViewHolder{

    TextView instruction_step_number,Instruction_step_title;
    RecyclerView recycler_Instruction_ingredent, recycler_Instruction_equipment;
    public InstructionStepViewHolder(@NonNull View itemView) {
        super(itemView);
        instruction_step_number = itemView.findViewById(R.id.instruction_step_number);
        Instruction_step_title = itemView.findViewById(R.id.instruction_step_title);
        recycler_Instruction_equipment = itemView.findViewById(R.id.recycler_instruction_equipment);
        recycler_Instruction_ingredent = itemView.findViewById(R.id.recycler_instruction_ingredient);
    }
}