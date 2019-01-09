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

import com.example.android.bakingapp.Adapters.IngredientsAdapter;
import com.example.android.bakingapp.Adapters.StepsAdapter;
import com.example.android.bakingapp.Models.Ingredient;
import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.Models.SharedStepViewModel;
import com.example.android.bakingapp.Models.Step;
import com.example.android.bakingapp.R;

import java.util.List;

public class StepsListFragment extends Fragment implements StepsAdapter.StepOnClickHandler {
    private RecyclerView mRvSteps;
    private RecyclerView mRvIngredients;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mIngredientsLayoutManager;
    private RecyclerView.LayoutManager mStepsLayoutManager;
    private SharedStepViewModel viewModel;
    private Recipe recipe;
    OnStepClickListener mCallback;

    public interface  OnStepClickListener{
        void onStepSelected();
    }

    public StepsListFragment(){

    }

    //This whole Override makes sure that I (or other developers) in future always implement
    //OnStepClickListener when using this Fragment in an Activity.
    //So for this project its actually useless but I should think about it in the future
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mCallback = (OnStepClickListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    +"must implement OnStepClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps_list, container, false);

        TextView tvIngredientsHeader = rootView.findViewById(R.id.tv_ingredients_header);
        mRvIngredients = rootView.findViewById(R.id.rv_recipeIngredients);
        mRvSteps = rootView.findViewById(R.id.rv_recipeShortDescs);

        viewModel = ViewModelProviders.of(getActivity()).get(SharedStepViewModel.class);

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
        mCallback.onStepSelected();
        viewModel.setCurrentStepId(requestedStep.getId());

    }
}

