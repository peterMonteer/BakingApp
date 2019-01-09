package com.example.android.bakingapp.Data;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface RecipesApi {

    @GET("baking.json")
    Call <ArrayList<Recipe>> getRecipeList();

}
