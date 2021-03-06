package com.example.android.bakingapp.Fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
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
    long mPlayerPosition;
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
        nextButton.setText(getResources().getString(R.string.next));
        prevButton.setText(getResources().getString(R.string.prev));
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);

        if (savedInstanceState != null){
            mPlayerPosition = savedInstanceState.getLong("playerposition");
        }



        // Get Recipe and CurrentStepId from ViewModel or Intent
        viewModel = ViewModelProviders.of(getActivity()).get(SharedStepViewModel.class);
        if(viewModel.getRecipe() == null) {
            Intent intent = getActivity().getIntent();
            recipe = (Recipe) intent.getSerializableExtra("requested");
            viewModel.setRecipe(recipe);
        }
        else{
            recipe = viewModel.getRecipe();
            givenStepId = viewModel.getCurrentStepId();
        }

        steps = recipe.getSteps();

        //LiveData Observer to make sure Ui gets updated in two-pane mode. Could have done this with lambda expressions but somehow didnt work (Or I didnt try hard enough)
        final Observer<Integer> idObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                updateUi(integer);
            }
        };
        viewModel.getCurrentStepId().observe(this, idObserver);


        if(viewModel.getCurrentStepId().getValue() == null){
            viewModel.setCurrentStepId(0);
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
        updateUi(id);

    }

    private void updateUi(int id){
        Step requestedStep = findStepById(steps, id);
        tvStepLongDesc.setText(requestedStep.getDescription());
        adjustPrevNextButtons(id);
    }

    private void setupExoPlayer(String url){
        Log.e("setupExoPlayer", ""+url);
        if (!url.isEmpty()){
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
            if(mPlayerPosition != 0){
                player.seekTo(mPlayerPosition);

            }
        }else {
            if(player != null){
                player.release();
            }
            playerView.setVisibility(View.GONE);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        setupExoPlayer(findStepById(steps, viewModel.getCurrentStepId().getValue()).getVideoURL());

    }

    @Override
    public void onPause() {
        if(player != null){
            mPlayerPosition = player.getCurrentPosition();
            player.release();
            player = null;
        }
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(player != null){
            mPlayerPosition =player.getCurrentPosition();
            player.release();
            player = null;
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bn_next:
                Integer nextStepId = findNextStepId(viewModel.getCurrentStepId().getValue());
                updateModelWithNewId(nextStepId);
                Step nextStep = findStepById(viewModel.getRecipe().getSteps(), nextStepId);
                if(!nextStep.getVideoURL().isEmpty()){
                    setupExoPlayer(nextStep.getVideoURL());
                }else {
                    if (player != null) {
                        player.release();
                    }
                    playerView.setVisibility(View.GONE);
                }
                break;
            case R.id.bn_prev:
                int prevStepId = findPrevStepId(viewModel.getCurrentStepId().getValue());
                updateModelWithNewId(prevStepId);
                Step prevStep = findStepById(viewModel.getRecipe().getSteps(), prevStepId);
                if(!prevStep.getVideoURL().isEmpty()){
                    setupExoPlayer(prevStep.getVideoURL());
                }else {
                    if(player != null){
                        player.release();
                    }
                    playerView.setVisibility(View.GONE);
                }
        }

    }
    private void adjustPrevNextButtons(int id){
        if(findNextStepId(id)==null){
            nextButton.setVisibility(View.INVISIBLE);
        }
        else{
            nextButton.setVisibility(View.VISIBLE);
        }
        if(findPrevStepId(id)== -1){
            prevButton.setVisibility(View.INVISIBLE);
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
                nextStepId =step.getId();
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong("playerposition", mPlayerPosition);
        super.onSaveInstanceState(outState);
    }
}


