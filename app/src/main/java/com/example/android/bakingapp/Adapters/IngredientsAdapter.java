package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {
        public IngredientsViewHolder(View view){
            super(view);
        }
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.activity_recipe_ingredient_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, attachImmediately);

        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        Ingredient currentIngredient = ingredients.get(position);
        currentIngredient.getIngredient();

        TextView ingredientView = holder.itemView.findViewById(R.id.tv_ingredients_ingredient);
        TextView quantityView = holder.itemView.findViewById(R.id.tv_ingredients_quantity);
        TextView measureView = holder.itemView.findViewById(R.id.tv_ingredients_measure);


        quantityView.setText("" + currentIngredient.getQuantity());
        measureView.setText("" + currentIngredient.getMeasure() + " ");
        ingredientView.setText(currentIngredient.getIngredient());



    }

    public int getItemCount(){
        return ingredients.size();
    }

}
