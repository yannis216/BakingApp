package com.example.android.bakingapp.Models;

import android.arch.lifecycle.ViewModel;

import java.util.List;


public class RecipeViewModel extends ViewModel {
    public List<Recipe> recipes;



    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> newRecipes){
        recipes = newRecipes;

    }

    }
