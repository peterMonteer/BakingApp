package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.android.bakingapp.Data.Step;


import java.util.ArrayList;

class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.ViewHolder> {

    private final ArrayList<Step> mStepsArrayList;
    private final boolean mTwoPane;
    private final RecipeDetailsActivity mParentActivity;

    public RecipeStepsAdapter(RecipeDetailsActivity parentActivity, ArrayList<Step> stepArrayList, boolean twoPane){
        this.mParentActivity = parentActivity;
        this.mStepsArrayList = stepArrayList;
        this.mTwoPane = twoPane;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.step_adapter_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Step currentStep = mStepsArrayList.get(position);

        viewHolder.mRecipeStepShortDescriptionTextView.setText(currentStep.getShortDescription());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("click","aqui");
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(StepDetailFragment.ARG_STEP_OBJECT, currentStep);
                    StepDetailFragment fragment = new StepDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.step_detail_container, fragment,"STEPDETAIL")
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, StepDetailActivity.class);
                    intent.putExtra(StepDetailFragment.ARG_STEP_OBJECT, currentStep);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mStepsArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mRecipeStepShortDescriptionTextView;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRecipeStepShortDescriptionTextView = itemView.findViewById(R.id.recipe_step_short_description_textView);
        }
    }
}
