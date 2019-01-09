package com.example.android.bakingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Data.Recipe;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.RecipeViewHolder> {

    private ArrayList<Recipe> mRecipeArrayList;

    public MainAdapter(ArrayList<Recipe> recipeArrayList){
        this.mRecipeArrayList = recipeArrayList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_card,viewGroup,false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeViewHolder recipeViewHolder, int position) {
        final Recipe currentRecipe = mRecipeArrayList.get(position);
        recipeViewHolder.mRecipeNameTextView.setText(currentRecipe.getName());
        recipeViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(recipeViewHolder.itemView.getContext(),RecipeDetailsActivity.class);
                intent.putExtra("Recipe",currentRecipe);
                startActivity(recipeViewHolder.itemView.getContext(),intent, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipeArrayList.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {

        private TextView mRecipeNameTextView;
        private RecipeViewHolder (View view){
            super(view);
            mRecipeNameTextView = view.findViewById(R.id.recipe_name_textView);
        }
    }

    public void setDataset(ArrayList<Recipe> recipeArrayList){
        mRecipeArrayList = recipeArrayList;
    }
}
