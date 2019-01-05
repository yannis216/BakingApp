package com.example.android.bakingapp.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.android.bakingapp.Adapters.IngredientsAdapter;
import com.example.android.bakingapp.Models.Ingredient;
import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.Models.RecipeViewModel;
import com.example.android.bakingapp.Models.Step;
import com.example.android.bakingapp.R;

import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    private RecyclerView mRvRecipeSteps;
    private RecyclerView mRvIngredients;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecipeViewModel viewModel;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        TextView tvIngredientsHeader = (TextView) findViewById(R.id.tv_ingredients_header);
        mRvIngredients = findViewById(R.id.rv_recipeIngredients);

        viewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        if(viewModel.getRecipe() == null) {
            Intent intent = getIntent();
            recipe = (Recipe) intent.getSerializableExtra("requested");
            viewModel.setRecipe(recipe);
        }
        else{
            recipe = viewModel.getRecipe();
        }

        tvIngredientsHeader.setText("Ingredients"); //TODO Move to Strings

        generateIngredientsList(recipe.getIngredients());
        generateStepsList(recipe.getSteps());







    }

    private void generateIngredientsList(List<Ingredient> ingredients){
        mLayoutManager = new LinearLayoutManager(this);
        mRvIngredients.setLayoutManager(mLayoutManager);
        
        mAdapter = new IngredientsAdapter(this, ingredients);
        Log.e("Ingredients", ""+ingredients);
        mRvIngredients.setAdapter(mAdapter);

    }

    private void generateStepsList(List<Step> steps){

    }
}
