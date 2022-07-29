package com.codewithnaveen.JevanKhana;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
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

public class seemoreActivity extends AppCompatActivity {

    LottieDialog progressDialog1;
    RequestManager requestManager11;
    StaggeredGridLayoutManager staggeredGridLayoutManager1;
    List<String> tags = new ArrayList<>();
    RecyclerView infoRecyclerView1,mealTypeRecyclerView1;
    MealTypeAdapter mealTypeAdapter1;
    RandomRecipeAdapter randomRecipeAdapter1;
    ArrayList<mealType> mealTypeArrayList1 = new ArrayList<>();
    TextView typeofmeal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seemore);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressDialog1 = new LottieDialog(this)
                .setAnimation(R.raw.food_loading)
                .setAnimationRepeatCount(LottieDialog.INFINITE)
                .setAutoPlayAnimation(true)
                .setMessage("Take a Profile Picture");
        progressDialog1.setTitle("Loding...");
        progressDialog1.setDialogBackground(Color.WHITE);
        typeofmeal = findViewById(R.id.typeofmeal);
        typeofmeal.setText("main course");
        loadMealType();
        infoRecyclerView1 = findViewById(R.id.seemorerecyclerview);
    }

    private final RandomRecipeResponseListener randomMealTypeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            infoRecyclerView1.setHasFixedSize(true);
            infoRecyclerView1.setLayoutManager(staggeredGridLayoutManager1);
            randomRecipeAdapter1 = new RandomRecipeAdapter(seemoreActivity.this,response.recipes,recipeClickListener);
            infoRecyclerView1.setAdapter(randomRecipeAdapter1);
            progressDialog1.dismiss();
        }

        @Override
        public void didError(String message) {
            Toast.makeText(seemoreActivity.this, "message", Toast.LENGTH_SHORT).show();
            progressDialog1.dismiss();
        }
    };
    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            //Toast.makeText(MainActivity.this,id,Toast.LENGTH_SHORT).show();
            startActivity(new Intent(seemoreActivity.this,ReciepeDetailActivity.class)
                    .putExtra("id",id));

        }
    };
    public void loadMealType(){
        mealTypeRecyclerView1 = findViewById(R.id.mealType_seemore);
        String[] meals = {"main course","side dish","dessert","appetizer","salad","bread","breakfast","soup","beverage","sauce","marinade","snack","drink"};
        int[] url = {R.drawable.main100,R.drawable.side_dish100,R.drawable.ice_cream100,
                R.drawable.kebab100,R.drawable.lettuce100,R.drawable.bread100,R.drawable.breakfast100,
                R.drawable.soup100,R.drawable.soda100,R.drawable.sauce100,R.drawable.marinade,
                R.drawable.snack100,R.drawable.drink100};
        for(int i = 0; i < meals.length; i++) {
            mealTypeArrayList1.add(new mealType(meals[i],url[i]));
        }
        mealTypeRecyclerView1.setHasFixedSize(true);
        mealTypeRecyclerView1.setLayoutManager(new LinearLayoutManager(
                seemoreActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false));
        mealTypeAdapter1 = new MealTypeAdapter(mealTypeArrayList1, seemoreActivity.this, new OnItemClickListener() {
            @Override
            public void onItemClick(mealType mealTypes) {
                requestManager11 = new RequestManager(seemoreActivity.this);
                staggeredGridLayoutManager1 = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                tags.clear();
                tags.add(mealTypes.getName());
                typeofmeal.setText(mealTypes.getName());
                requestManager11.getRandomRecipes(randomMealTypeResponseListener,tags);
                progressDialog1.show();
                Toast.makeText(seemoreActivity.this, "mealType==>"+mealTypes.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        mealTypeRecyclerView1.setAdapter(mealTypeAdapter1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        progressDialog1.dismiss();
    }
}