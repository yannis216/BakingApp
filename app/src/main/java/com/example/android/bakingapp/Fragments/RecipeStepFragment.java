package com.example.android.bakingapp.Fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.Models.SharedStepViewModel;
import com.example.android.bakingapp.Models.Step;
import com.example.android.bakingapp.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

public class RecipeStepFragment extends Fragment implements View.OnClickListener {
    private LiveData<Integer> givenStepId;
    private Recipe recipe;
    private SharedStepViewModel viewModel;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    List<Step> steps;
    TextView tvStepLongDesc;
    Button nextButton;
    Button prevButton;
    public RecipeStepFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        tvStepLongDesc = rootView.findViewById(R.id.tv_longDesc);
        playerView = rootView.findViewById(R.id.pv_Step);
        nextButton = rootView.findViewById(R.id.bn_next);
        prevButton = rootView.findViewById(R.id.bn_prev);

        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);



        // Get Recipe and CurrentStepId from ViewModel or Intent
        viewModel = ViewModelProviders.of(getActivity()).get(SharedStepViewModel.class);
        if(viewModel.getRecipe() == null || viewModel.getCurrentStepId() == null) {
            Intent intent = getActivity().getIntent();
            recipe = (Recipe) intent.getSerializableExtra("requested");
            viewModel.setRecipe(recipe);
            viewModel.setCurrentStepId(0); //If no Info given, display first step
            // TODO May change this to displaying ingredients
            //TODO This MAY have to observe changes of the Livedata currentStepId to work properly in DoublePaneLayout. If not needed -> remove Live Data stuff
        }
        else{
            recipe = viewModel.getRecipe();
            givenStepId = viewModel.getCurrentStepId();
        }

        steps = recipe.getSteps();
        if(viewModel.getCurrentStepId().getValue() == null){
            viewModel.setCurrentStepId(1);
        }
        updateUi(viewModel.getCurrentStepId().getValue());
        return rootView;

    }

    private Step findStepById(List<Step> steps, int stepId){
        for(Step step : steps){
            if(step.getId() == stepId){
                return step;
            }
        }
        return null;
    }

    private void updateModelWithNewId(int id){
        viewModel.setCurrentStepId(id);
        //TODO Finish this (maybe build one fits all function? and implement with buttons
        updateUi(id);

    }

    private void updateUi(int id){
        Step requestedStep = findStepById(steps, id);
        String url = requestedStep.getVideoURL();
        if (url != ""){
            setupExoPlayer(url);
        }
        else{
            if(player != null){
                player.release();
            }
            playerView.setVisibility(View.GONE);
        }
        tvStepLongDesc.setText(requestedStep.getDescription());
        adjustPrevNextButtons(id);
    }

    private void setupExoPlayer(String url){
        playerView.setVisibility(View.VISIBLE);
        if(player != null){
            player.release();
        }
        Uri videoUri = Uri.parse(url);
        player = ExoPlayerFactory.newSimpleInstance(getContext());
        playerView.setPlayer(player);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "BakingApp"));
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(videoUri);
        player.prepare(videoSource);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(player != null){
            player.release();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(player != null){
            player.release();
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bn_next:
                Integer nextStepId = findNextStepId(viewModel.getCurrentStepId().getValue());
                updateModelWithNewId(nextStepId);
                break;
            case R.id.bn_prev:
                int prevStepId = findPrevStepId(viewModel.getCurrentStepId().getValue());
                updateModelWithNewId(prevStepId);
        }

    }
    private void adjustPrevNextButtons(int id){
        if(findNextStepId(id)==null){
            nextButton.setVisibility(View.GONE);
        }
        else{
            nextButton.setVisibility(View.VISIBLE);
        }
        if(findPrevStepId(id)== -1){
            prevButton.setVisibility(View.GONE);
        }
        else{
            prevButton.setVisibility(View.VISIBLE);
        }
    }

    private Integer findNextStepId(int id){
        int maxId = 0;
        int nextStepId = 0;
        for(Step step : steps){
            maxId =step.getId();
            //FindNextId
            if(step.getId() > id && nextStepId == 0){
                nextStepId =step.getId();  //TODO Simplify, I think maxId is not necessary but not sure
            }
        }
        if (id >= maxId){
            return null;
        }
        else{
            Log.e("Next Step Id", ""+nextStepId);
            return nextStepId;
        }
    }
    private Integer findPrevStepId(int id){
        int prevStepId = -1;
        for(Step step : steps) {
            //find prevId
            if (step.getId() < id) {
                prevStepId = step.getId();
            }
        }
        return prevStepId;
    }
}


