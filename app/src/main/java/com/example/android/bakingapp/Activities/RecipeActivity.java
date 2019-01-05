package com.example.android.bakingapp.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.Models.RecipeViewModel;
import com.example.android.bakingapp.R;

public class RecipeActivity extends AppCompatActivity {

    private RecyclerView mRvRecipeSteps;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecipeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        viewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        Intent intent = getIntent();
        Recipe recipe = (Recipe) intent.getSerializableExtra("requested");
        viewModel.setRecipe(recipe);






    }
}
