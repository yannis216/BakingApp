package com.example.android.bakingapp.Models;

import android.arch.lifecycle.ViewModel;


public class StepViewModel extends ViewModel {
    private Recipe recipe;
    private Integer currentStepId;



    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe newRecipe){
        recipe = newRecipe;

    }

    public Integer getCurrentStepId() {
        return currentStepId;
    }

    public void setCurrentStepId(int currentStepId) {
        this.currentStepId = currentStepId;
    }
}
