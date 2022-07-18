package com.codewithnaveen.JevanKhana;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codewithnaveen.JevanKhana.Adapters.SimilarRecipeAdapter;
import com.codewithnaveen.JevanKhana.Adapters.ingredientsAdapter;
import com.codewithnaveen.JevanKhana.Listeners.RecipeClickListener;
import com.codewithnaveen.JevanKhana.Listeners.RecipeDetailsListner;
import com.codewithnaveen.JevanKhana.Listeners.SimilarRecipeListener;
import com.codewithnaveen.JevanKhana.Models.ReciepeDetailsResponse;
import com.codewithnaveen.JevanKhana.Models.SimilarRecipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReciepeDetailActivity extends AppCompatActivity {
    int id;
    TextView meal_name,meal_saurce,meal_summary;
    ImageView meal_image;
    RecyclerView ingredientRecycler,similarMealRecycler;
    RequestManager manager;
    ProgressDialog dialog;
    ingredientsAdapter ingredientsAdapters;
    SimilarRecipeAdapter similarRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciepe_detail);

        findViews();
        id = Integer.parseInt(getIntent().getStringExtra("id"));
        manager = new RequestManager(this);
        manager.getRecipeDetails(recipeDetailsListner,id);
        manager.getSimilarRecipe(similarRecipeListener,id);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...");
        dialog.show();
    }

    private void findViews() {
        meal_name = findViewById(R.id.text_view_meal_name);
        meal_saurce = findViewById(R.id.meal_source);
        meal_summary = findViewById(R.id.meal_summarry);
        meal_image = findViewById(R.id.imageView_mealDetail);
        ingredientRecycler = findViewById(R.id.recycler_meal_ingredients);
        similarMealRecycler = findViewById(R.id.meal_similar);
    }

    private final RecipeDetailsListner recipeDetailsListner = new RecipeDetailsListner() {
        @Override
        public void didFetch(ReciepeDetailsResponse response, String message) {
            dialog.dismiss();
            meal_name.setText(response.title);
            meal_saurce.setText(response.sourceName);
            meal_summary.setText(response.summary);
            Picasso.get().load(response.image).into(meal_image);

            ingredientRecycler.setHasFixedSize(true);
            ingredientRecycler.setLayoutManager(new LinearLayoutManager(ReciepeDetailActivity.this,LinearLayoutManager.HORIZONTAL,false));
            ingredientsAdapters = new ingredientsAdapter(ReciepeDetailActivity.this,response.extendedIngredients);
            ingredientRecycler.setAdapter(ingredientsAdapters);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(ReciepeDetailActivity.this, "message"+message, Toast.LENGTH_SHORT).show();
        }
    };

    private final SimilarRecipeListener similarRecipeListener = new SimilarRecipeListener() {
        @Override
        public void didFetch(List<SimilarRecipe> response, String message) {
            similarMealRecycler.setHasFixedSize(true);
            similarMealRecycler.setLayoutManager(new LinearLayoutManager(ReciepeDetailActivity.this,LinearLayoutManager.HORIZONTAL,false));
            similarRecipeAdapter = new SimilarRecipeAdapter(ReciepeDetailActivity.this,response,recipeClickListener);
            similarMealRecycler.setAdapter(similarRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(ReciepeDetailActivity.this,"message==>"+message,Toast.LENGTH_SHORT).show();
        }
    };
    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(ReciepeDetailActivity.this,ReciepeDetailActivity.class)
            .putExtra("id",id));

        }
    };
}