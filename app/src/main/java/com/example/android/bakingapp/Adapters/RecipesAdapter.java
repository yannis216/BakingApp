package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.R;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {
    private List<Recipe> recipes;
    private LayoutInflater mInflater;

    public RecipesAdapter(Context context, List<Recipe> recipesList){
        mInflater = LayoutInflater.from(context);
        this.recipes = recipesList;
    }

    public static class RecipesViewHolder extends RecyclerView.ViewHolder{
        public RecipesViewHolder(View view){
            super(view);
        }
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.card_recipe;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, attachImmediately);


        return new RecipesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {
        Recipe currentRecipe = recipes.get(position);
        String recipeName = currentRecipe.getName();

        TextView nameView = holder.itemView.findViewById(R.id.tv_recipeTitle);

        nameView.setText(recipeName);


    }

    public int getItemCount(){
        return recipes.size();
    }



}
