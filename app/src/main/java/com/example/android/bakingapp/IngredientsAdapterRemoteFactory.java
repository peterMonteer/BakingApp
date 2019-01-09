package com.example.android.bakingapp;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.Data.Ingredient;

import java.util.ArrayList;

class IngredientsAdapterRemoteFactory implements RemoteViewsService.RemoteViewsFactory {


    private Context mContext;
    private ArrayList<Ingredient> mIngredientsArrayList;

    public IngredientsAdapterRemoteFactory(Context context) {
        mContext = context;

        if(RecipeWidgetProvider.mRecipe != null) {
            mIngredientsArrayList = RecipeWidgetProvider.mRecipe.getIngredients();
        }
    }

    @Override
    public void onDataSetChanged() {
        if(RecipeWidgetProvider.mRecipe != null) {
            mIngredientsArrayList = RecipeWidgetProvider.mRecipe.getIngredients();
        } else {
            mIngredientsArrayList = null;
        }
    }

    @Override
    public int getCount() {
        if(mIngredientsArrayList == null){
            return 0;
        }
        return mIngredientsArrayList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if(mIngredientsArrayList == null || mIngredientsArrayList.size() == 0) return null;

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_adapter_item_layout);
        Ingredient ingredient = mIngredientsArrayList.get(i);

        views.setTextViewText(R.id.ingredient_name_textView,ingredient.getIngredient().substring(0,1).toUpperCase() + ingredient.getIngredient().substring(1).toLowerCase() );
        views.setTextViewText(R.id.quantity_textView, mContext.getResources().getString(R.string.quantity, String.valueOf(ingredient.getQuantity()),ingredient.getMeasure()));

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public void onCreate() {
    }
    @Override
    public void onDestroy() {
    }
}