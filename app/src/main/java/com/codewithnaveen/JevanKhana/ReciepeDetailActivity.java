package com.codewithnaveen.JevanKhana;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amrdeveloper.lottiedialog.LottieDialog;
import com.codewithnaveen.JevanKhana.Adapters.InstructionAdapter;
import com.codewithnaveen.JevanKhana.Adapters.SimilarRecipeAdapter;
import com.codewithnaveen.JevanKhana.Adapters.ViewPagerFragmentAdapter;
import com.codewithnaveen.JevanKhana.Adapters.ingredientsAdapter;
import com.codewithnaveen.JevanKhana.Listeners.RecipeDetailsListner;
import com.codewithnaveen.JevanKhana.Models.ReciepeDetailsResponse;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReciepeDetailActivity extends AppCompatActivity {
    int id;
    TextView meal_name,meal_saurce,meal_summary;
    ImageView meal_image;
    RecyclerView ingredientRecycler,similarMealRecycler;//,meal_Instruction_steps;
    RequestManager manager;
    LottieDialog dialog;
    ingredientsAdapter ingredientsAdapters;
    SimilarRecipeAdapter similarRecipeAdapter;
    InstructionAdapter instructionAdapter;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private final String[] labels = new String[]{"Steps", "Similar"};

    ViewPagerFragmentAdapter adapter;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciepe_detail);

        id = Integer.parseInt(getIntent().getStringExtra("id"));
        findViews();

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            tab.setText(labels[position]);
        }).attach();
        // set default position to 1 instead of default 0
        viewPager2.setCurrentItem(0, true);
        viewPager2.setUserInputEnabled(false);
        manager = new RequestManager(this);

        manager.getRecipeDetails(recipeDetailsListner,id);
        dialog = new LottieDialog(this)
                .setAnimation(R.raw.food_loading)
                .setAnimationRepeatCount(LottieDialog.INFINITE)
                .setAutoPlayAnimation(true)
                .setMessage("Take a Profile Picture");
        dialog.setTitle("Loading...");
        dialog.setDialogBackground(Color.WHITE);
        dialog.show();
    }

    private void findViews() {
        // initialize tabLayout
        tabLayout = findViewById(R.id.tab_layout);
        // initialize viewPager2
        viewPager2 = findViewById(R.id.view_pager2);
        // create adapter instance
        adapter = new ViewPagerFragmentAdapter(this, id);
        // set adapter to viewPager2
        viewPager2.setAdapter(adapter);

        meal_name = findViewById(R.id.text_view_meal_name);
        meal_summary = findViewById(R.id.meal_summarry);
        meal_image = findViewById(R.id.imageView_mealDetail);
        ingredientRecycler = findViewById(R.id.recycler_meal_ingredients);
    }

    private final RecipeDetailsListner recipeDetailsListner = new RecipeDetailsListner() {
        @Override
        public void didFetch(ReciepeDetailsResponse response, String message) {
            dialog.dismiss();
            meal_name.setText(response.title);
           // meal_saurce.setText(response.sourceName);
            meal_summary.setText(HtmlCompat.fromHtml(response.summary,0));
            Picasso.get().load(response.image).into(meal_image);

            ingredientRecycler.setHasFixedSize(true);
            ingredientRecycler.setLayoutManager(new LinearLayoutManager(ReciepeDetailActivity.this,LinearLayoutManager.HORIZONTAL,false));
            ingredientsAdapters = new ingredientsAdapter(ReciepeDetailActivity.this,response.extendedIngredients);
            ingredientRecycler.setAdapter(ingredientsAdapters);
        }

        @Override
        public void didError(String message) {
        }
    };

}