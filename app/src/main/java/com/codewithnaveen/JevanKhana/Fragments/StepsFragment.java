package com.codewithnaveen.JevanKhana.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codewithnaveen.JevanKhana.Adapters.InstructionAdapter;
import com.codewithnaveen.JevanKhana.Listeners.InstructionListener;
import com.codewithnaveen.JevanKhana.Models.InstructionResponse;
import com.codewithnaveen.JevanKhana.R;
import com.codewithnaveen.JevanKhana.ReciepeDetailActivity;
import com.codewithnaveen.JevanKhana.RequestManager;

import java.util.List;

public class StepsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RequestManager manager4;
    InstructionAdapter instructionAdapter4;
    RecyclerView meal_Instruction_steps4;
    private int id;

    private String mParam1;
    private String mParam2;

    public StepsFragment(int id) {
        this.id = id;
    }

    public StepsFragment() {
        // Required empty public constructor
    }


    public static StepsFragment newInstance(String param1, String param2) {
        StepsFragment fragment = new StepsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steps, container, false);
        Toast.makeText(getContext(), "id==="+id, Toast.LENGTH_SHORT).show();
        manager4 = new RequestManager(getContext());
        meal_Instruction_steps4 = view.findViewById(R.id.meal_Instruction_steps);
        manager4.getInstruction(instructionListener,id);
        return view;
    }

    private final InstructionListener instructionListener = new InstructionListener() {
        @Override
        public void didFetch(List<InstructionResponse> response, String message) {
            Toast.makeText(getContext(), "inside listner", Toast.LENGTH_SHORT).show();
            meal_Instruction_steps4.setHasFixedSize(true);
            meal_Instruction_steps4.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            instructionAdapter4 = new InstructionAdapter(getContext(),response);
            meal_Instruction_steps4.setAdapter(instructionAdapter4);
        }

        @Override
        public void didError(String message) {

        }
    };
}