package com.example.android.bakingapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.Models.Step;
import com.example.android.bakingapp.Models.StepViewModel;
import com.example.android.bakingapp.R;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.List;

public class StepActivity extends AppCompatActivity {

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


    }
}
