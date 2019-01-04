package com.example.android.bakingapp.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.Models.RecipeViewModel;
import com.example.android.bakingapp.Network.RetrofitClientInstance;
import com.example.android.bakingapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesActivity extends AppCompatActivity {

    private RecyclerView mRvRecipeCards;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecipeViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        mRvRecipeCards =(RecyclerView) findViewById(R.id.rv_recipecards);

        mLayoutManager = new LinearLayoutManager(this);
        mRvRecipeCards.setLayoutManager(mLayoutManager);

        viewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        getRecipesFromNetworkResource();





    }


    public void getRecipesFromNetworkResource(){
        if(viewModel.getRecipes() ==null){
            RetrofitClientInstance.GetRecipeService service = RetrofitClientInstance.getRetrofitInstance(getApplication()).create(RetrofitClientInstance.GetRecipeService.class);
            Call<List<Recipe>> call = service.getRecipe();

            call.enqueue(new Callback<List<Recipe>>() {

                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    viewModel.setRecipes(response.body());
                    Log.e("Retrofit Response", "" + response.body());

                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    Toast.makeText(RecipesActivity.this, "Please turn on Internet Connection :-)" , Toast.LENGTH_SHORT).show();
                    Log.e("Tag", "This is the Throwable:", t);

                }
            });
        }

    }
}
