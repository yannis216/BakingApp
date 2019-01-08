package com.example.android.bakingapp.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.Models.Step;
import com.example.android.bakingapp.Models.StepViewModel;
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

public class StepActivity extends AppCompatActivity {

    private Integer givenStepId;
    private Recipe recipe;
    private StepViewModel viewModel;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    List<Step> steps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        TextView tvStepLongDesc = findViewById(R.id.tv_longDesc);
        playerView = findViewById(R.id.pv_Step);


        // Get Recipe and CurrentStepId from ViewModel or Intent
        viewModel = ViewModelProviders.of(this).get(StepViewModel.class);
        if(viewModel.getRecipe() == null || viewModel.getCurrentStepId() ==null) {
            Intent intent = getIntent();
            recipe = (Recipe) intent.getSerializableExtra("Recipe");
            givenStepId = intent.getIntExtra("selectedStep", 0);
            viewModel.setRecipe(recipe);
            viewModel.setCurrentStepId(givenStepId);
        }
        else{
            recipe = viewModel.getRecipe();
            givenStepId = viewModel.getCurrentStepId();
        }

        steps = recipe.getSteps();
        Step requestedStep = findStepById(steps, givenStepId);
        String url = requestedStep.getVideoURL();

        if (url != ""){
            setupExoPlayer(url);
        }
        else{
            playerView.setVisibility(View.GONE);
        }

        tvStepLongDesc.setText(requestedStep.getDescription());

    }

    private Step findStepById(List<Step> steps, int stepId){
        for(Step step : steps){
            if(step.getId() == stepId){
                return step;
            }
        }
        return null;
    }

    private void updateWithNewId(int id){
        viewModel.setCurrentStepId(id);
        Step newStep = findStepById(steps, id);
        String url = newStep.getVideoURL();
        if (url != ""){
            setupExoPlayer(url);
        }
        else{
            playerView.setVisibility(View.GONE);
        }
        tvStepLongDesc.setText(requestedStep.getDescription());  //TODO Finish this (maybe build one fits all function? and implement with buttons

    }

    private void setupExoPlayer(String url){
        Uri videoUri = Uri.parse(url);
        player = ExoPlayerFactory.newSimpleInstance(this);
        playerView.setPlayer(player);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "BakingApp"));
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(videoUri);
        player.prepare(videoSource);
    }

    //TODO Call ExoPlayer.release somewhere (but where?)


    @Override
    protected void onPause() {
        super.onPause();
        if(player != null){
            player.release();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(player != null){
            player.release();
        }
    }
}
