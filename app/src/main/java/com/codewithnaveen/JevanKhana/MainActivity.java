package com.codewithnaveen.JevanKhana;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.amrdeveloper.lottiedialog.LottieDialog;
import com.codewithnaveen.JevanKhana.Adapters.MealTypeAdapter;
import com.codewithnaveen.JevanKhana.Adapters.OnItemClickListener;
import com.codewithnaveen.JevanKhana.Adapters.RandomRecipeAdapter;
import com.codewithnaveen.JevanKhana.Listeners.RandomRecipeResponseListener;
import com.codewithnaveen.JevanKhana.Listeners.RecipeClickListener;
import com.codewithnaveen.JevanKhana.Models.RandomRecipeApiResponse;
import com.codewithnaveen.JevanKhana.Models.mealType;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RequestManager requestManager,requestManager1;
    RandomRecipeAdapter randomRecipeAdapter, randomTypeRecipeAdapter;
    MealTypeAdapter mealTypeAdapter;
    RecyclerView recyclerView,mealTypeRecyclerView,infoRecyclerView;
    LinearLayoutManager HorizontalLayout,hlayout,hlayout1;
    ArrayList<mealType> mealTypeArrayList = new ArrayList<>();
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    List<String> tags = new ArrayList<>();
    SearchView searchView;
    TextView seemore,selected_meal_Type,meal_Trend,meal_type_textview;
    String mealtypename = null;
    Shader shader;
    LottieDialog progressDialog;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new LottieDialog(this)
                .setAnimation(R.raw.food_loading)
                .setAnimationRepeatCount(LottieDialog.INFINITE)
                .setAutoPlayAnimation(true)
                .setMessage("Take a Profile Picture");
        progressDialog.setTitle("Loding...");
        progressDialog.setDialogBackground(Color.WHITE);
        seemore = findViewById(R.id.more_detail_meal);
        seemore.setOnClickListener((v -> {
            startActivity(new Intent(MainActivity.this,seemoreActivity.class)
                    .putExtra("type",mealtypename));
        }));
        selected_meal_Type = findViewById(R.id.selected_meal_Type);
        meal_type_textview =findViewById(R.id.meal_type_textview);
        meal_Trend = findViewById(R.id.meal_Trend);
        searchView = findViewById(R.id.searchHome);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startActivity(new Intent(MainActivity.this,searchActivity.class)
                        .putExtra("query",query));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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
            randomRecipeAdapter = new RandomRecipeAdapter(MainActivity.this,response.recipes,recipeClickListener);
            recyclerView.setAdapter(randomRecipeAdapter);
            progressDialog.dismiss();
        }

        @Override
        public void didError(String message) {
            Toast.makeText(MainActivity.this, "message", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    };

    private final RandomRecipeResponseListener randomMealTypeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            infoRecyclerView = findViewById(R.id.mealList);
            infoRecyclerView.setHasFixedSize(true);
            infoRecyclerView.setLayoutManager( new LinearLayoutManager(
                    MainActivity.this,
                    LinearLayoutManager.HORIZONTAL,
                    false));
            randomRecipeAdapter = new RandomRecipeAdapter(MainActivity.this,response.recipes,recipeClickListener);
            infoRecyclerView.setAdapter(randomRecipeAdapter);
            progressDialog.dismiss();
        }

        @Override
        public void didError(String message) {
            Toast.makeText(MainActivity.this, "message", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
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
                staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                tags.clear();
                tags.add(mealTypes.getName());
                shader = new LinearGradient(
                        0, 0, 0, 100,
                        Color.BLUE, Color.parseColor("#E91E63"),
                        Shader.TileMode.CLAMP );
                meal_Trend.getPaint().setShader(shader);
                meal_type_textview.getPaint().setShader(shader);
                selected_meal_Type.setText(mealTypes.getName());
                selected_meal_Type.getPaint().setShader( shader );
                mealtypename = mealTypes.getName();
                requestManager1.getRandomRecipes(randomMealTypeResponseListener,tags);
                progressDialog.show();
                Toast.makeText(MainActivity.this, "mealType==>"+mealTypes.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        mealTypeRecyclerView.setAdapter(mealTypeAdapter);
    }
    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            //Toast.makeText(MainActivity.this,id,Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this,ReciepeDetailActivity.class)
            .putExtra("id",id));

        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        progressDialog.dismiss();
    }

    @Override
    protected void onStop() {
        super.onStop();
        progressDialog.dismiss();
    }
}