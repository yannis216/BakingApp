package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Models.Step;
import com.example.android.bakingapp.R;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {
    private List<Step> steps;
    private LayoutInflater mInflater;
    private final StepOnClickHandler mClickHandler;

    public interface StepOnClickHandler {
        void onClick(Step requestedStep);
    }

    public StepsAdapter(Context context, List<Step> stepsList, StepOnClickHandler clickHandler){
        mInflater = LayoutInflater.from(context);
        this.steps = stepsList;
        mClickHandler = clickHandler;
        Log.e("StepsAdapterConstructor", "wird gecalled");
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public StepsViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
            Log.e("StepsViewHolder", "wird gecalled");
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Step requestedStep = steps.get(adapterPosition);
            mClickHandler.onClick(requestedStep);
        }
    }



    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.activity_recipe_desc_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachImmediately = false;
        Log.e("onCreateViewHolder", "wird gecalled");

        View view = inflater.inflate(layoutIdForListItem, viewGroup, attachImmediately);


        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        Step currentStep = steps.get(position);
        String stepShortDesc = currentStep.getShortDescription();

        TextView nameView = holder.itemView.findViewById(R.id.tv_recipe_desc_list_item);
        Log.e("onBindViewHolder", "wird gecalled");
        nameView.setText(stepShortDesc);


    }

    public int getItemCount(){
        Log.e("getItemCount", "Steps:" + steps.size());
        return steps.size();
    }



}
