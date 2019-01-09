package com.example.android.bakingapp;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.bakingapp.Data.ApiController;
import com.example.android.bakingapp.Data.Recipe;
import com.example.android.bakingapp.Data.RecipesApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<ArrayList<Recipe>> {

    private RecyclerView mRecipeRecyclerView;
    private MainAdapter mMainAdapter;
    private ArrayList<Recipe> mRecipeArrayList = new ArrayList<>();

    public static CountingIdlingResource mIdlingResource = new CountingIdlingResource("MainActivity");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mRecipeRecyclerView = findViewById(R.id.recipe_recyclerView);
        mRecipeRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        mRecipeRecyclerView.setHasFixedSize(true);

        mMainAdapter = new MainAdapter(mRecipeArrayList);

        mRecipeRecyclerView.setAdapter(mMainAdapter);

        RecipesApi recipesApi = new ApiController().create();
        Call<ArrayList<Recipe>> call = recipesApi.getRecipeList();
        mIdlingResource.increment();
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
        if (response.isSuccessful()){
            mRecipeArrayList = response.body();
            mMainAdapter.setDataset(mRecipeArrayList);
            mMainAdapter.notifyDataSetChanged();
            mIdlingResource.decrement();

            Log.d("mainStep", mRecipeArrayList.get(0).getSteps().get(0).getShortDescription());
        }
    }

    @Override
    public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
        Log.d("onFail", t.getMessage());
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource(){
        if (mIdlingResource == null){
            mIdlingResource = new CountingIdlingResource("MainActivity");
        }
        return  mIdlingResource;
    }
}
