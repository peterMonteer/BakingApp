package com.example.android.bakingapp;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Data.Ingredient;

import java.util.ArrayList;

class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.ViewHolder> {

    private ArrayList<Ingredient> mIngredientsArrayList;

    public RecipeIngredientsAdapter(){}

    public void setIngredients(ArrayList<Ingredient> ingredientArrayList){
        this.mIngredientsArrayList = ingredientArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ingredient_adapter_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Ingredient currentIngredient = mIngredientsArrayList.get(position);
        Resources resources= viewHolder.itemView.getContext().getResources();


        viewHolder.mRecipeIngredientNameTextView.setText(currentIngredient.getIngredient().substring(0,1).toUpperCase() + currentIngredient.getIngredient().substring(1).toLowerCase());
        viewHolder.mRecipeIngredientQuantityTextView.setText(resources.getString(R.string.quantity, String.valueOf(currentIngredient.getQuantity()),currentIngredient.getMeasure()));
    }

    @Override
    public int getItemCount() {
        return mIngredientsArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mRecipeIngredientNameTextView;
        private TextView mRecipeIngredientQuantityTextView;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRecipeIngredientNameTextView = itemView.findViewById(R.id.ingredient_name_textView);
            mRecipeIngredientQuantityTextView = itemView.findViewById(R.id.quantity_textView);
        }
    }
}
