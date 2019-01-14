package com.example.android.bakingapp.Activities;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.Fragments.RecipeStepFragment;
import com.example.android.bakingapp.Fragments.StepsListFragment;
import com.example.android.bakingapp.R;

public class RecipeActivity extends AppCompatActivity implements StepsListFragment.OnStepClickListener{
    private Boolean mTwoPane;
    FragmentManager fragmentManager;
    public static final String STATE_STEPS = "StepsListFragment";
    public static final String STATE_STEP = "RecipeStepFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);


        if(findViewById(R.id.dp600_recipe_layout) != null)
        {
            mTwoPane =true;
        }
        else {
            mTwoPane = false;
        }

        if(mTwoPane) {
            StepsListFragment stepsListFragment = new StepsListFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.sw600dp_steps_list_container, stepsListFragment)
                    .commit();


            RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
            // FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.sw600dp_recipe_step_container, recipeStepFragment)
                    .commit();

        }else{

            if(savedInstanceState == null) {
                StepsListFragment stepsListFragment = new StepsListFragment();
                fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.recipe_step_container, stepsListFragment)
                        .commit();
            }
        }


    }


    public void onStepSelected(){
        if(!mTwoPane) {
            RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_step_container, recipeStepFragment)
                    .addToBackStack("Steps")
                    .commit();
        }

    }


}
