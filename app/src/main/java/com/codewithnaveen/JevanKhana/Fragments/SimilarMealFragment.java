package com.codewithnaveen.JevanKhana.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codewithnaveen.JevanKhana.Adapters.SimilarRecipeAdapter;
import com.codewithnaveen.JevanKhana.Listeners.RecipeClickListener;
import com.codewithnaveen.JevanKhana.Listeners.SimilarRecipeListener;
import com.codewithnaveen.JevanKhana.Models.SimilarRecipe;
import com.codewithnaveen.JevanKhana.R;
import com.codewithnaveen.JevanKhana.ReciepeDetailActivity;
import com.codewithnaveen.JevanKhana.RequestManager;

import java.util.List;

public class SimilarMealFragment extends Fragment {

    SimilarRecipeAdapter similarRecipeAdapter;
    RecyclerView similarMealRecycler;
    int id;
    RequestManager manager;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SimilarMealFragment() {
        // Required empty public constructor
    }

    public SimilarMealFragment(int id) {
        this.id = id;
    }

    public static SimilarMealFragment newInstance(String param1, String param2) {
        SimilarMealFragment fragment = new SimilarMealFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_similar_meal, container, false);
        manager = new RequestManager(getContext());
        similarMealRecycler = view.findViewById(R.id.meal_similar);
        manager.getSimilarRecipe(similarRecipeListener,id);
        return view;
    }
        private final SimilarRecipeListener similarRecipeListener = new SimilarRecipeListener() {
        @Override
        public void didFetch(List<SimilarRecipe> response, String message) {
            similarMealRecycler.setHasFixedSize(true);
            similarMealRecycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            similarRecipeAdapter = new SimilarRecipeAdapter(getContext(),response,recipeClickListener);
            similarMealRecycler.setAdapter(similarRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(getContext(),"message==>"+message, Toast.LENGTH_SHORT).show();
        }
    };
    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(getContext(),ReciepeDetailActivity.class)
            .putExtra("id",id));

        }
    };
}