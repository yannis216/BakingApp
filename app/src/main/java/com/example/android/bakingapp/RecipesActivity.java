package com.example.android.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class RecipesActivity extends AppCompatActivity {

    private RecyclerView mRvRecipeCards;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        mRvRecipeCards =(RecyclerView) findViewById(R.id.rv_recipecards);
        


    }
}
