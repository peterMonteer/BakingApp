package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * An activity representing a single Step detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeDetailsActivity}.
 */
public class StepDetailActivity extends AppCompatActivity {

    private ImageButton mBackImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);


        mBackImageButton = findViewById(R.id.back_button_imageButton);
        mBackImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StepDetailActivity.super.onBackPressed();
            }
        });

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putParcelable(StepDetailFragment.ARG_STEP_OBJECT, getIntent().getParcelableExtra(StepDetailFragment.ARG_STEP_OBJECT));
            StepDetailFragment fragment = new StepDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_detail_container, fragment)
                    .commit();
        }
    }
}
