package com.codewithnaveen.JevanKhana;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.codewithnaveen.JevanKhana.Adapters.MealTypeAdapter;
import com.codewithnaveen.JevanKhana.Adapters.OnItemClickListener;
import com.codewithnaveen.JevanKhana.Adapters.RandomRecipeAdapter;
import com.codewithnaveen.JevanKhana.Listeners.RandomRecipeResponseListener;
import com.codewithnaveen.JevanKhana.Models.RandomRecipeApiResponse;
import com.codewithnaveen.JevanKhana.Models.mealType;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    RequestManager requestManager,requestManager1;
    RandomRecipeAdapter randomRecipeAdapter, randomTypeRecipeAdapter;
    MealTypeAdapter mealTypeAdapter;
    RecyclerView recyclerView,mealTypeRecyclerView,infoRecyclerView;
    LinearLayoutManager HorizontalLayout,hlayout,hlayout1;
    ArrayList<mealType> mealTypeArrayList = new ArrayList<>();
    List<String> tags = new ArrayList<>();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loding...");
        HorizontalLayout
                = new LinearLayoutManager(
                MainActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);

        requestManager = new RequestManager(this);
        requestManager.getRandomRecipes(randomRecipeResponseListener,null);
        progressDialog.show();
        loadMealType();
    }

    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            recyclerView = findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(HorizontalLayout);
            randomRecipeAdapter = new RandomRecipeAdapter(MainActivity.this,response.recipes);
            recyclerView.setAdapter(randomRecipeAdapter);
            progressDialog.hide();
        }

        @Override
        public void didError(String message) {
            Toast.makeText(MainActivity.this, "message", Toast.LENGTH_SHORT).show();
            progressDialog.hide();
        }
    };

    private final RandomRecipeResponseListener randomMealTypeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            infoRecyclerView = findViewById(R.id.mealList);
            infoRecyclerView.setHasFixedSize(true);
            infoRecyclerView.setLayoutManager(hlayout1);
            randomRecipeAdapter = new RandomRecipeAdapter(MainActivity.this,response.recipes);
            infoRecyclerView.setAdapter(randomRecipeAdapter);
            progressDialog.hide();
        }

        @Override
        public void didError(String message) {
            Toast.makeText(MainActivity.this, "message", Toast.LENGTH_SHORT).show();
            progressDialog.hide();
        }
    };

    public void loadMealType(){
        mealTypeRecyclerView = findViewById(R.id.mealType);
        String[] meals = {"main course","side dish","dessert","appetizer","salad","bread","breakfast","soup","beverage","sauce","marinade","snack","drink"};
        int[] url = {R.drawable.main100,R.drawable.side_dish100,R.drawable.ice_cream100,
                R.drawable.kebab100,R.drawable.lettuce100,R.drawable.bread100,R.drawable.breakfast100,
                R.drawable.soup100,R.drawable.soda100,R.drawable.sauce100,R.drawable.marinade,
                R.drawable.snack100,R.drawable.drink100};
        for(int i = 0; i < meals.length; i++) {
            mealTypeArrayList.add(new mealType(meals[i],url[i]));
        }
        hlayout
                = new LinearLayoutManager(
                MainActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);
        mealTypeRecyclerView.setHasFixedSize(true);
        mealTypeRecyclerView.setLayoutManager(hlayout);
        mealTypeAdapter = new MealTypeAdapter(mealTypeArrayList, MainActivity.this, new OnItemClickListener() {
            @Override
            public void onItemClick(mealType mealTypes) {
                requestManager1 = new RequestManager(MainActivity.this);
                hlayout1 = new LinearLayoutManager(
                        MainActivity.this,
                        LinearLayoutManager.HORIZONTAL,
                        false);
                tags.clear();
                tags.add(mealTypes.getName());
                requestManager1.getRandomRecipes(randomMealTypeResponseListener,tags);
                progressDialog.show();
                Toast.makeText(MainActivity.this, "mealType==>"+mealTypes.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        mealTypeRecyclerView.setAdapter(mealTypeAdapter);
    }
}