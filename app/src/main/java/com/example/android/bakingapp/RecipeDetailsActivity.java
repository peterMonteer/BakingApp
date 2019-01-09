package com.example.android.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.android.bakingapp.Data.Ingredient;
import com.example.android.bakingapp.Data.Recipe;
import com.example.android.bakingapp.Data.Step;
import java.util.ArrayList;



public class RecipeDetailsActivity extends AppCompatActivity {

    private RecyclerView mRecipeIngredientsRecyclerView;
    private RecipeIngredientsAdapter mRecipeIngredientsAdapter;
    private TextView mRecipeNameTextView;
    private ImageButton mBackImageButton;
    private TextView mIngredientsTextView;
    private RecyclerView mRecipeStepsRecyclerView;
    private RecipeStepsAdapter mRecipeStepsAdapter;
    private Button mWidgetButton;

    private ArrayList<Ingredient> mRecipeIngredientArrayList = new ArrayList<>();
    private ArrayList<Step> mRecipeStepsArrayList = new ArrayList<>();
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        mBackImageButton = findViewById(R.id.back_button_imageButton);
        mIngredientsTextView = findViewById(R.id.ingredients_textView);
        mRecipeNameTextView = findViewById(R.id.recipe_title_textView);
        mRecipeIngredientsRecyclerView = findViewById(R.id.recipe_ingredients_recyclerView);
        mRecipeStepsRecyclerView = findViewById(R.id.recipe_steps_recyclerView);
        mWidgetButton = findViewById(R.id.widget_button);

        if (findViewById(R.id.step_detail_container) != null) {
            mTwoPane = true;
        }


        mIngredientsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRecipeIngredientsRecyclerView.getVisibility() == View.GONE){
                    mRecipeIngredientsRecyclerView.setVisibility(View.VISIBLE);
                    mIngredientsTextView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.drop_up,0);
                } else {
                    mRecipeIngredientsRecyclerView.setVisibility(View.GONE);
                    mIngredientsTextView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.drop_down,0);
                }
            }
        });

        mBackImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecipeDetailsActivity.super.onBackPressed();
            }
        });

        Recipe recipe = getIntent().getParcelableExtra("Recipe");
        mRecipeIngredientArrayList = recipe.getIngredients();
        mRecipeStepsArrayList = recipe.getSteps();

        mRecipeNameTextView.setText(recipe.getName());
        mRecipeIngredientsAdapter = new RecipeIngredientsAdapter();
        mRecipeIngredientsAdapter.setIngredients(mRecipeIngredientArrayList);
        mRecipeIngredientsAdapter.notifyDataSetChanged();

        mRecipeStepsAdapter = new RecipeStepsAdapter(this, mRecipeStepsArrayList,mTwoPane);

        mRecipeIngredientsRecyclerView.setAdapter(mRecipeIngredientsAdapter);
        mRecipeStepsRecyclerView.setAdapter(mRecipeStepsAdapter);

        mWidgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getBaseContext());
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(getBaseContext(), RecipeWidgetProvider.class));

                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
                RecipeWidgetProvider.updateAppWidget(getBaseContext(), appWidgetManager, appWidgetIds, recipe );
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (findViewById(R.id.step_detail_container) != null) {
            mTwoPane = true;
        } else {
            FragmentManager manager = getSupportFragmentManager();
            if (manager.findFragmentByTag("STEPDETAIL") != null){
                manager.beginTransaction().remove(manager.findFragmentByTag("STEPDETAIL")).commit();
            }

        }
    }
}
