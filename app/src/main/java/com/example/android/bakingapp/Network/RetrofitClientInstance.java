package com.example.android.bakingapp.Network;

import android.content.Context;

import com.example.android.bakingapp.Models.Recipe;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL ="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    public static Retrofit getRetrofitInstance(Context context) {

        //Debug Stuff
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(loggingInterceptor);

        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client.build())
                    .build();
        }
        return retrofit;
    }

    public interface GetRecipeService {

        @GET("baking.json")
        Call<List<Recipe>> getRecipe();
    }
}
