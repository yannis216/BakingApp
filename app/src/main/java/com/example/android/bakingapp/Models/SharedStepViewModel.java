package com.example.android.bakingapp.Models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;


public class SharedStepViewModel extends ViewModel {
    private Recipe recipe;
    private final MutableLiveData<Integer> currentStepId = new MutableLiveData<>();

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe newRecipe){
        recipe = newRecipe;
    }

    public LiveData<Integer> getCurrentStepId() {
        return currentStepId;
    }

    public void setCurrentStepId(int selectedStepId) {
        currentStepId.setValue(selectedStepId);
    }
}
