package com.example.android.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.data.Steps;
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

import java.util.ArrayList;

import static com.example.android.bakingapp.MainActivity.mTwoPane;
import static com.example.android.bakingapp.MainActivity.recipesArrayList;
import static com.example.android.bakingapp.StepsAndIngredientsActivity.index;


/**
 * Created by Mahmoud on 7/3/2017.
 */

public class StepsDetailsFragment extends Fragment implements ExoPlayer.EventListener {

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private static long position = 0;
    private int mIndex=0;
    Toast toast;
    TextView longDescription;
    Button prev, next;
    private ArrayList<Steps> steps;
    private static final String TAG = StepsDetailsActivity.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps_details, container, false);

        longDescription = (TextView) rootView.findViewById(R.id.long_description);
        prev = (Button) rootView.findViewById(R.id.prev);
        next = (Button) rootView.findViewById(R.id.next);
        steps = recipesArrayList.get(index).getSteps();
        Log.v("stepsSize: " , steps.size()+"");

        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);
        initializeMediaSession();
        Log.v("mIndex:" ,+ mIndex +"\n"+ steps.get(mIndex).getVideoURL()+""+"\nindex: "+index);

        //mIndex = getActivity().getIntent().getExtras().getInt("position");
        //toast.makeText(getActivity() , "position is: "+ position, Toast.LENGTH_LONG).show();
        initializePlayer(Uri.parse(steps.get(mIndex).getVideoURL()));
        if (!mTwoPane) {
            mIndex = getActivity().getIntent().getExtras().getInt("position");
            Log.v("mIndex2: ", mIndex+"");
        }

        //getActivity().setTitle(steps.get(index).getShortDescription());
        longDescription.setText(steps.get(mIndex).getDescription());
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIndex > 0) {
                    mIndex--;
                    longDescription.setText(steps.get(mIndex).getDescription());
                    restExoPlayer(0, false);
                    initializePlayer(Uri.parse(steps.get(mIndex).getVideoURL()));
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIndex < steps.size() - 1) {
                    mIndex++;
                    longDescription.setText(steps.get(mIndex).getDescription());
                    restExoPlayer(0, false);
                    initializePlayer(Uri.parse(steps.get(mIndex).getVideoURL()));
                }
            }
        });
        return rootView;
    }

    private void initializePlayer(Uri videoMediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "");
            MediaSource mediaSource = new ExtractorMediaSource(videoMediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
            restExoPlayer(position, false);
        }
    }

    private void restExoPlayer(long position, boolean playWhenReady) {
        this.position = position;
        mExoPlayer.seekTo(position);
        mExoPlayer.setPlayWhenReady(playWhenReady);
    }

    private void initializeMediaSession() {
        // Create a MediaSessionCompat
        mMediaSession = new MediaSessionCompat(getContext(), TAG);

        // set the flags.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mMediaSession.setPlaybackState(mStateBuilder.build());

        // set the callBacks.
        mMediaSession.setCallback(new MySessionCallback());

        // start the session.
        mMediaSession.setActive(true);
    }



    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            restExoPlayer(0, false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        mMediaSession.setActive(false);
    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMediaSession.setActive(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        mExoPlayer.setPlayWhenReady(false);
        mMediaSession.setActive(false);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == PlaybackStateCompat.STATE_PLAYING) {
            position = mExoPlayer.getCurrentPosition();
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }
}