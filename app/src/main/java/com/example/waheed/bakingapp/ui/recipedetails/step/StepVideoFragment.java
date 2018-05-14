package com.example.waheed.bakingapp.ui.recipedetails.step;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.waheed.bakingapp.R;
import com.example.waheed.bakingapp.api.vo.RecipeStep;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepVideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepVideoFragment extends Fragment {

    private static final String ARG_STEP = "step";

    private RecipeStep step;

    private SimpleExoPlayer exoPlayer;
    private SimpleExoPlayerView exoPlayerView;

    public StepVideoFragment() {
        // Required empty public constructor
    }

    public static StepVideoFragment newInstance(RecipeStep step) {
        StepVideoFragment fragment = new StepVideoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_STEP, step);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            step = (RecipeStep) getArguments().getSerializable(ARG_STEP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step_video, container, false);
        exoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.playerView);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer(Uri.parse(step.getVideoURL()));
    }

    private void initializePlayer(Uri mediaUri) {
        if (exoPlayer == null) {

            // Create an instance of the ExoPlayer.
            TrackSelector defaultTrackSelector = new DefaultTrackSelector();
            LoadControl defaultLoadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(
                    getContext(), defaultTrackSelector, defaultLoadControl);

            exoPlayerView.setPlayer(exoPlayer);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "Baking app");

            MediaSource mediaSource = new ExtractorMediaSource(mediaUri,
                    new DefaultDataSourceFactory(getContext(), userAgent),
                    new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer() {
        exoPlayer.stop();
        exoPlayer.release();
        exoPlayer = null;
    }

}
