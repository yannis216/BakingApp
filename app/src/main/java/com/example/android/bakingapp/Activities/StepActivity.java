package com.example.android.bakingapp.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.Models.Step;
import com.example.android.bakingapp.Models.StepViewModel;
import com.example.android.bakingapp.R;

import java.util.List;

public class StepActivity extends AppCompatActivity {

    private Integer currentStepId;
    private Recipe recipe;
    private StepViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        TextView tvStepLongDesc = findViewById(R.id.tv_longDesc);


        // Get Recipe and CurrentStepId from ViewModel or Intent
        viewModel = ViewModelProviders.of(this).get(StepViewModel.class);
        if(viewModel.getRecipe() == null || viewModel.getCurrentStepId() ==null) {
            Intent intent = getIntent();
            recipe = (Recipe) intent.getSerializableExtra("Recipe");
            currentStepId = intent.getIntExtra("selectedStep", 0);
            viewModel.setRecipe(recipe);
        }
        else{
            recipe = viewModel.getRecipe();
            currentStepId = viewModel.getCurrentStepId();
        }

        List<Step> steps = recipe.getSteps();
        Step currentStep = steps.get(currentStepId);

        tvStepLongDesc.setText(currentStep.getDescription());



    }
}
