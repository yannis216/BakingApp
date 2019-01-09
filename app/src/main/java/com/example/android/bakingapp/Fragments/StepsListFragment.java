package com.example.android.bakingapp.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Activities.StepActivity;
import com.example.android.bakingapp.Adapters.IngredientsAdapter;
import com.example.android.bakingapp.Adapters.StepsAdapter;
import com.example.android.bakingapp.Models.Ingredient;
import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.Models.RecipeViewModel;
import com.example.android.bakingapp.Models.Step;
import com.example.android.bakingapp.R;

import java.util.List;

public class StepsListFragment extends Fragment implements StepsAdapter.StepOnClickHandler {
    private RecyclerView mRvSteps;
    private RecyclerView mRvIngredients;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mIngredientsLayoutManager;
    private RecyclerView.LayoutManager mStepsLayoutManager;
    private RecipeViewModel viewModel;
    private Recipe recipe;


    public StepsListFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps_list, container, false);

        TextView tvIngredientsHeader = rootView.findViewById(R.id.tv_ingredients_header);
        mRvIngredients = rootView.findViewById(R.id.rv_recipeIngredients);
        mRvSteps = rootView.findViewById(R.id.rv_recipeShortDescs);

        viewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        if(viewModel.getRecipe() == null) {
            Intent intent = getActivity().getIntent();
            recipe = (Recipe) intent.getSerializableExtra("requested");
            viewModel.setRecipe(recipe);
        }
        else{
            recipe = viewModel.getRecipe();
        }

        tvIngredientsHeader.setText("Ingredients"); //TODO Move to Strings.xml

        generateIngredientsList(recipe.getIngredients());
        generateStepsList(recipe.getSteps());

        return rootView;





    }

    private void generateIngredientsList(List<Ingredient> ingredients){
        mIngredientsLayoutManager = new LinearLayoutManager(getContext());
        mRvIngredients.setLayoutManager(mIngredientsLayoutManager);

        mAdapter = new IngredientsAdapter(getContext(), ingredients);
        Log.e("Ingredients", ""+ingredients);
        mRvIngredients.setAdapter(mAdapter);

    }

    private void generateStepsList(List<Step> steps){
        mStepsLayoutManager = new LinearLayoutManager(getContext());
        mRvSteps.setLayoutManager(mStepsLayoutManager);

        mAdapter = new StepsAdapter(getContext(), steps, this);
        Log.e("Steps", ""+steps);
        mRvSteps.setAdapter(mAdapter);

    }

    @Override
    public void onClick(Step requestedStep){
        Context context = getContext();

        Intent startStepActivityIntent = new Intent(context, StepActivity.class);
        startStepActivityIntent.putExtra("Recipe", recipe);
        startStepActivityIntent.putExtra("selectedStep", requestedStep.getId());
        startActivity(startStepActivityIntent);
    }
}

