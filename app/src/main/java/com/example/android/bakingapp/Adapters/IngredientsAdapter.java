package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Models.Ingredient;
import com.example.android.bakingapp.R;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {
    private List<Ingredient> ingredients;
    private LayoutInflater mInflater;

    public IngredientsAdapter(Context context, List<Ingredient> ingredientList){
        mInflater = LayoutInflater.from(context);
        this.ingredients = ingredientList;
        Log.e("AdapterConstructor", "DOES GET CALLED");
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {
        public IngredientsViewHolder(View view){
            super(view);
            Log.e("IngredientsViewHolder", "DOES GET CALLED");
        }
    }



    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.activity_recipe_ingredient_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachImmediately = false;
        Log.e("onCreateViewHolder", "DOES GET CALLED");

        View view = inflater.inflate(layoutIdForListItem, viewGroup, attachImmediately);

        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        Log.e("onBindViewHolder", "DOES GET CALLED");
        Ingredient currentIngredient = ingredients.get(position);
        String ingredientName = currentIngredient.getIngredient();
        Log.e("Current Ingredient", "" + ingredientName);

        TextView nameView = holder.itemView.findViewById(R.id.tv_ingredients_ingredient);

        nameView.setText(ingredientName);


    }

    public int getItemCount(){
        Log.e("ItemCount", "" + ingredients.size());
        return ingredients.size();
    }



}
