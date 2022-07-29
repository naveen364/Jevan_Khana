package com.codewithnaveen.JevanKhana;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.amrdeveloper.lottiedialog.LottieDialog;
import com.codewithnaveen.JevanKhana.Adapters.RandomRecipeAdapter;
import com.codewithnaveen.JevanKhana.Listeners.RandomRecipeResponseListener;
import com.codewithnaveen.JevanKhana.Listeners.RecipeClickListener;
import com.codewithnaveen.JevanKhana.Models.RandomRecipeApiResponse;

import java.util.ArrayList;
import java.util.List;

public class searchActivity extends AppCompatActivity {
    RecyclerView searched_meal;
    RequestManager managers;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    List<String> tags = new ArrayList<>();
    RandomRecipeAdapter randomRecipeAdapter;
    String query;
    LottieDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        dialog = new LottieDialog(this)
                .setAnimation(R.raw.food_loading)
                .setAnimationRepeatCount(LottieDialog.INFINITE)
                .setAutoPlayAnimation(true)
                .setMessage("Take a Profile Picture");
        dialog.setDialogBackground(Color.WHITE);
        dialog.show();
        searched_meal = findViewById(R.id.searched_meal);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        tags.clear();
        query = getIntent().getStringExtra("query");
        tags.add(query);
        managers = new RequestManager(this);
        managers.getRandomRecipes(randomRecipeResponseListener,tags);
    }
    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            searched_meal.setHasFixedSize(true);
            searched_meal.setLayoutManager(staggeredGridLayoutManager);
            randomRecipeAdapter = new RandomRecipeAdapter(searchActivity.this,response.recipes,recipeClickListener);
            searched_meal.setAdapter(randomRecipeAdapter);
            dialog.dismiss();
        }

        @Override
        public void didError(String message) {
        }
    };
    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(searchActivity.this,ReciepeDetailActivity.class)
                    .putExtra("id",id));

        }
    };
}