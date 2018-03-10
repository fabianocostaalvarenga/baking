package com.udacity.baking.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import com.udacity.baking.R;
import com.udacity.baking.model.Step;

/**
 * Created by fabiano.alvarenga on 04/03/18.
 */

public class StepDetailFragment extends Fragment {

    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    private String urlVideo;
    private boolean playWhenReady = true;

    private long mCurrentVideoPosition = C.INDEX_UNSET;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootView =
                inflater.inflate(R.layout.fragment_step_details, container, false);

        final Bundle arguments = getArguments();
        final Step step = (Step) arguments.getParcelable(Step.class.getSimpleName());

        if(null != step) {
            urlVideo = step.getVideoUrl();
            final TextView description = (TextView) rootView.findViewById(R.id.tv_step_description);
            description.setText(step.getDescription());
        }

        simpleExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.sepv_step_visualization);

        if (null != urlVideo && !urlVideo.isEmpty()) {
            initializeVideoPlayer();
        } else {
            simpleExoPlayerView.setVisibility(View.GONE);
        }

        initializeImage(rootView, step);

        return rootView;

    }

    @Override
    public void onStart() {
        super.onStart();
        initializeVideoPlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeVideoPlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseVideoPlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseVideoPlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseVideoPlayer();
    }

    private void initializeImage(View view, Step step) {

        if(null == step) {
            return;
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.iv_step_visualization) ;

        final String urlImg = step.getThumbnailUrl();

        if(null != urlImg && !urlImg.isEmpty()) {
            imageView.setVisibility(View.VISIBLE);
            Picasso.with(getContext()).load(Uri.parse(urlImg)).into(imageView);
        } else {
            imageView.setVisibility(View.GONE);
        }
    }

    private void initializeVideoPlayer() {
        if (null == player && (null != urlVideo && !urlVideo.isEmpty()) && null != simpleExoPlayerView) {
            simpleExoPlayerView.setVisibility(View.VISIBLE);
            final BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            final TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            final TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);
            final Context context = getContext();
            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

            simpleExoPlayerView.setPlayer(player);

            final DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                    Util.getUserAgent(context, getString(R.string.user_agent_video_player)));
            final ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            final Uri videoUri = Uri.parse(urlVideo);
            final MediaSource videoSource = new ExtractorMediaSource(videoUri,
                    dataSourceFactory, extractorsFactory, null, null);
            player.prepare(videoSource);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(mCurrentVideoPosition);
            simpleExoPlayerView.requestFocus();
        }

    }

    private void releaseVideoPlayer() {
        if (null != simpleExoPlayerView) {
            simpleExoPlayerView.setPlayer(null);
        }

        if (null != player) {
            mCurrentVideoPosition = player.getCurrentPosition();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

}
