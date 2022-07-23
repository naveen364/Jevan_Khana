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
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codewithnaveen.JevanKhana.Adapters.InstructionAdapter;
import com.codewithnaveen.JevanKhana.Adapters.SimilarRecipeAdapter;
import com.codewithnaveen.JevanKhana.Adapters.ViewPagerFragmentAdapter;
import com.codewithnaveen.JevanKhana.Adapters.ingredientsAdapter;
import com.codewithnaveen.JevanKhana.Fragments.SimilarMealFragment;
import com.codewithnaveen.JevanKhana.Fragments.StepsFragment;
import com.codewithnaveen.JevanKhana.Listeners.InstructionListener;
import com.codewithnaveen.JevanKhana.Listeners.RecipeClickListener;
import com.codewithnaveen.JevanKhana.Listeners.RecipeDetailsListner;
import com.codewithnaveen.JevanKhana.Listeners.SimilarRecipeListener;
import com.codewithnaveen.JevanKhana.Models.InstructionResponse;
import com.codewithnaveen.JevanKhana.Models.ReciepeDetailsResponse;
import com.codewithnaveen.JevanKhana.Models.SimilarRecipe;
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
    ProgressDialog dialog;
    ingredientsAdapter ingredientsAdapters;
    SimilarRecipeAdapter similarRecipeAdapter;
    InstructionAdapter instructionAdapter;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private String[] labels = new String[]{"Steps", "Similar"};

    ViewPagerFragmentAdapter adapter;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciepe_detail);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        id = Integer.parseInt(getIntent().getStringExtra("id"));
        findViews();

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            tab.setText(labels[position]);
        }).attach();
        // set default position to 1 instead of default 0
        viewPager2.setCurrentItem(1, true);

        Toast.makeText(this, "id==>"+id, Toast.LENGTH_SHORT).show();

        manager = new RequestManager(this);
        manager.getRecipeDetails(recipeDetailsListner,id);
        //manager.getSimilarRecipe(similarRecipeListener,id);
        //manager.getInstruction(instructionListener,id);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...");
        dialog.show();
    }

    private void findViews() {
        // initialize tabLayout
        tabLayout = findViewById(R.id.tab_layout);
        // initialize viewPager2
        viewPager2 = findViewById(R.id.view_pager2);
        // create adapter instance
        adapter = new ViewPagerFragmentAdapter(this, id);
        Toast.makeText(this, "ids from detail="+id, Toast.LENGTH_SHORT).show();
        // set adapter to viewPager2
        viewPager2.setAdapter(adapter);

        meal_name = findViewById(R.id.text_view_meal_name);
        meal_saurce = findViewById(R.id.meal_source);
        meal_summary = findViewById(R.id.meal_summarry);
        meal_image = findViewById(R.id.imageView_mealDetail);
        ingredientRecycler = findViewById(R.id.recycler_meal_ingredients);
        //similarMealRecycler = findViewById(R.id.meal_similar);
        //meal_Instruction_steps = findViewById(R.id.meal_Instruction_steps);
    }

    private final RecipeDetailsListner recipeDetailsListner = new RecipeDetailsListner() {
        @Override
        public void didFetch(ReciepeDetailsResponse response, String message) {
            dialog.dismiss();
            meal_name.setText(response.title);
            meal_saurce.setText(response.sourceName);
            meal_summary.setText(HtmlCompat.fromHtml(response.summary,0));
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

//    private final SimilarRecipeListener similarRecipeListener = new SimilarRecipeListener() {
//        @Override
//        public void didFetch(List<SimilarRecipe> response, String message) {
//            similarMealRecycler.setHasFixedSize(true);
//            similarMealRecycler.setLayoutManager(new LinearLayoutManager(ReciepeDetailActivity.this,LinearLayoutManager.HORIZONTAL,false));
//            similarRecipeAdapter = new SimilarRecipeAdapter(ReciepeDetailActivity.this,response,recipeClickListener);
//            similarMealRecycler.setAdapter(similarRecipeAdapter);
//        }
//
//        @Override
//        public void didError(String message) {
//            Toast.makeText(ReciepeDetailActivity.this,"message==>"+message,Toast.LENGTH_SHORT).show();
//        }
//    };
//    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
//        @Override
//        public void onRecipeClicked(String id) {
//            startActivity(new Intent(ReciepeDetailActivity.this,ReciepeDetailActivity.class)
//            .putExtra("id",id));
//
//        }
//    };

//    private final InstructionListener instructionListener = new InstructionListener() {
//        @Override
//        public void didFetch(List<InstructionResponse> response, String message) {
//            meal_Instruction_steps.setHasFixedSize(true);
//            meal_Instruction_steps.setLayoutManager(new LinearLayoutManager(ReciepeDetailActivity.this,LinearLayoutManager.VERTICAL,false));
//            instructionAdapter = new InstructionAdapter(ReciepeDetailActivity.this,response);
//            meal_Instruction_steps.setAdapter(instructionAdapter);
//        }
//
//        @Override
//        public void didError(String message) {
//
//        }
//    };

}