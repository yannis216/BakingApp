package com.example.android.bakingapp.Models;

import android.arch.lifecycle.ViewModel;


public class RecipeViewModel extends ViewModel {
    public Recipe recipe;



    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe newRecipe){
        recipe = newRecipe;

    }

    }
