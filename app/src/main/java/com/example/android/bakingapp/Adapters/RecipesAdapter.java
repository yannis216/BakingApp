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
    private final RecipeOnClickHandler mClickHandler;
    Context context2;

    public interface RecipeOnClickHandler {
        void onClick(Recipe requestedRecipe);
    }

    public RecipesAdapter(Context context, List<Recipe> recipesList, RecipeOnClickHandler clickHandler){
        mInflater = LayoutInflater.from(context);
        context2 = context;
        this.recipes = recipesList;
        mClickHandler = clickHandler;
    }

    public class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public RecipesViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Recipe requestedRecipe = recipes.get(adapterPosition);
            mClickHandler.onClick(requestedRecipe);
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
        String recipeServings = context2.getResources().getString(R.string.servings)+currentRecipe.getServings();

        TextView nameView = holder.itemView.findViewById(R.id.tv_recipeTitle);
        TextView servingsView = holder.itemView.findViewById(R.id.tv_recipeServings);

        nameView.setText(recipeName);
        servingsView.setText(recipeServings);


    }

    public int getItemCount(){
        return recipes.size();
    }



}
