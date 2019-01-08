package com.example.android.bakingapp.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

public class StepActivity extends AppCompatActivity implements View.OnClickListener {

    private Integer givenStepId;
    private Recipe recipe;
    private StepViewModel viewModel;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    List<Step> steps;
    TextView tvStepLongDesc;
    Button nextButton;
    Button prevButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        tvStepLongDesc = findViewById(R.id.tv_longDesc);
        playerView = findViewById(R.id.pv_Step);
        nextButton = findViewById(R.id.bn_next);
        prevButton = findViewById(R.id.bn_prev);

        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);



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
        updateUi(viewModel.getCurrentStepId());

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
        player = ExoPlayerFactory.newSimpleInstance(this);
        playerView.setPlayer(player);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "BakingApp"));
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(videoUri);
        player.prepare(videoSource);
    }

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

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bn_next:
                int nextStepId = viewModel.getCurrentStepId()+1;
                updateModelWithNewId(nextStepId);
                break;
            case R.id.bn_prev:
                int prevStepId = viewModel.getCurrentStepId()-1;
                updateModelWithNewId(prevStepId);
        }

    }
    private void adjustPrevNextButtons(int id){
        if(findStepById(steps, id+1)==null){
            nextButton.setVisibility(View.GONE);
        }
        else{
            nextButton.setVisibility(View.VISIBLE);
        }
        if(findStepById(steps, id-1)==null){
            prevButton.setVisibility(View.GONE);
        }
        else{
            prevButton.setVisibility(View.VISIBLE);
        }
    }
}
