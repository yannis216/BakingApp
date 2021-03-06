package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.Models.Step;
import com.example.android.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {
    private List<Step> steps;
    private LayoutInflater mInflater;
    private final StepOnClickHandler mClickHandler;
    Context context;

    public interface StepOnClickHandler {
        void onClick(Step requestedStep);
    }

    public StepsAdapter(Context context, List<Step> stepsList, StepOnClickHandler clickHandler){
        mInflater = LayoutInflater.from(context);
        this.steps = stepsList;
        mClickHandler = clickHandler;
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public StepsViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
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
        context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.activity_recipe_desc_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, attachImmediately);


        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        Step currentStep = steps.get(position);
        String stepShortDesc = currentStep.getShortDescription();

        ImageView thumbnailView = holder.itemView.findViewById(R.id.iv_thumbnail);
        if(currentStep.getThumbnailURL().isEmpty()){
            thumbnailView.setImageResource(R.drawable.baseline_not_interested_black_24);
        }
        else{
            Picasso.with(context)
                    .load(currentStep.getThumbnailURL())
                    .placeholder(R.drawable.baseline_not_interested_black_24)
                    .error(R.drawable.baseline_not_interested_black_24)
                    .into(thumbnailView);
        }

        TextView nameView = holder.itemView.findViewById(R.id.tv_recipe_desc_list_item);
        nameView.setText(stepShortDesc);

    }

    public int getItemCount(){
        return steps.size();
    }



}
