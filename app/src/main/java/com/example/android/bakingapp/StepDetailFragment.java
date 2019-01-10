package com.example.android.bakingapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Data.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * A fragment representing a single Step detail screen.
 * This fragment is either contained in a {@link RecipeDetailsActivity}
 * in two-pane mode (on tablets) or a {@link StepDetailActivity}
 * on handsets.
 */
public class StepDetailFragment extends Fragment {

    public static final String ARG_STEP_OBJECT = "step";
    private static final String PLAYER_POSITION = "player_position";
    private static final String PLAYER_STATE = "player_state" ;

    private Step mCurrentStep;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private Long mPlayerPosition;
    private Boolean mPlayerState;


    public StepDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_STEP_OBJECT)) {
            mCurrentStep = getArguments().getParcelable(ARG_STEP_OBJECT);

            Activity activity = this.getActivity();
            TextView stepTitleTextView = activity.findViewById(R.id.recipe_step_title_textView);
            if (stepTitleTextView != null) {
                stepTitleTextView.setText(mCurrentStep.getShortDescription());
            }
        }
        if (savedInstanceState != null){
            mPlayerState = savedInstanceState.getBoolean(PLAYER_STATE);
            mPlayerPosition = savedInstanceState.getLong(PLAYER_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail, container, false);
        mPlayerView = rootView.findViewById(R.id.exoPlayerView);

        if (mCurrentStep != null) {
            ((TextView) rootView.findViewById(R.id.step_description_textView)).setText(mCurrentStep.getDescription());
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Long playerPosition = mExoPlayer.getCurrentPosition();
        Boolean getPlayerWhenReady = mExoPlayer.getPlayWhenReady();
        outState.putBoolean(PLAYER_STATE, getPlayerWhenReady);
        outState.putLong(PLAYER_POSITION, playerPosition);
    }

    private void initializePlayer(String url){
        if (mExoPlayer == null){
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector,loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            DataSource.Factory datasourceFactory = new DefaultDataSourceFactory(getContext(),Util.getUserAgent(getContext(),"bakerap"));
            MediaSource mediaSource = new ExtractorMediaSource.Factory(datasourceFactory).createMediaSource(Uri.parse(url));
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
            if (mPlayerState != null && mPlayerPosition != null){
                mExoPlayer.setPlayWhenReady(mPlayerState);
                mExoPlayer.seekTo(mPlayerPosition);
            }
        }
    }

    private void releasePlayer(){
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mExoPlayer != null){
            if (Util.SDK_INT > 23) {
                releasePlayer();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23){
            if (mExoPlayer != null){
                releasePlayer();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || mExoPlayer == null){
            if (mCurrentStep != null){
                String videoUrl = mCurrentStep.getVideoURL();
                String thumbnailUrl = mCurrentStep.getThumbnailURL();
                if (!videoUrl.isEmpty()){
                    initializePlayer(videoUrl);
                } else if (!thumbnailUrl.isEmpty()){
                    initializePlayer(thumbnailUrl);
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23){
            if (mCurrentStep != null){
                String videoUrl = mCurrentStep.getVideoURL();
                String thumbnailUrl = mCurrentStep.getThumbnailURL();
                if (!videoUrl.isEmpty()){
                    initializePlayer(videoUrl);
                } else if (!thumbnailUrl.isEmpty()){
                    initializePlayer(thumbnailUrl);
                }
            }
        }
    }
}
