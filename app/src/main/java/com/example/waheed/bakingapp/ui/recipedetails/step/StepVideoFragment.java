package com.example.waheed.bakingapp.ui.recipedetails.step;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.waheed.bakingapp.R;
import com.example.waheed.bakingapp.api.vo.RecipeStep;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepVideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepVideoFragment extends Fragment implements ExoPlayer.EventListener {

    private static final String ARG_STEP = "step";

    private static final String KEY_PLAYBACK_CURRENT_POSITION = "current_position";

    private static final String KEY_PLAY_WHEN_READY = "play_when_ready";

    private RecipeStep step;

    private long playbackCurrentPosition;

    private boolean playWhenReady = true;

    private SimpleExoPlayer exoPlayer;
    private SimpleExoPlayerView exoPlayerView;
    private ImageView thumbnailImageView;

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

        // restore playback position
        if (savedInstanceState != null) {
            playbackCurrentPosition = savedInstanceState.getLong(KEY_PLAYBACK_CURRENT_POSITION);
            playWhenReady = savedInstanceState.getBoolean(KEY_PLAY_WHEN_READY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step_video, container, false);
        thumbnailImageView = view.findViewById(R.id.thumbnailImageView);
        loadThumbnailImageIfExist(step.getThumbnailURL(), thumbnailImageView);

        exoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.playerView);
        return view;
    }

    private void loadThumbnailImageIfExist(String thumbnailURL, ImageView thumbnailImageView) {
        if (TextUtils.isEmpty(thumbnailURL)) {
            return;
        }
        Picasso.get().load(thumbnailURL).into(thumbnailImageView);
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer(Uri.parse(step.getVideoURL()));
    }

    private void initializePlayer(Uri mediaUri) {
        if (TextUtils.isEmpty(mediaUri.toString())) {
            exoPlayerView.setVisibility(View.GONE);
            thumbnailImageView.setVisibility(View.VISIBLE);
            return;
        }

        exoPlayerView.setVisibility(View.VISIBLE);
        thumbnailImageView.setVisibility(View.GONE);

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
            exoPlayer.seekTo(playbackCurrentPosition);
            exoPlayer.setPlayWhenReady(playWhenReady);
            exoPlayer.addListener(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        playbackCurrentPosition = exoPlayer.getCurrentPosition();
        releasePlayer();
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (exoPlayer != null) {
            playbackCurrentPosition = exoPlayer.getCurrentPosition();
            outState.putLong(KEY_PLAYBACK_CURRENT_POSITION, playbackCurrentPosition);
            outState.putBoolean(KEY_PLAY_WHEN_READY, playWhenReady);
        }
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        this.playWhenReady = playWhenReady;
        if (playbackState == exoPlayer.STATE_IDLE) {
            exoPlayerView.setVisibility(View.GONE);
            thumbnailImageView.setVisibility(View.VISIBLE);
        } else {
            exoPlayerView.setVisibility(View.VISIBLE);
            thumbnailImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {}

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {}

    @Override
    public void onLoadingChanged(boolean isLoading) {}

    @Override
    public void onPlayerError(ExoPlaybackException error) {}

    @Override
    public void onPositionDiscontinuity() {}
}
