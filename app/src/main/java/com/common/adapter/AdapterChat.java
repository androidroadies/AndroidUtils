package com.common.adapter;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.common.utilcode.util.NetworkUtils;
import com.common.utils.DateTimeUtils;
import com.common.utils.LogUtils;
import com.common.utils.R;
import com.common.views.CustomTextView;
import com.common.views.ZoomableImageView;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.xmpp.library.model.ModelChat;
import com.xmpp.library.utils.XmppConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ln-141 on 21/4/16.
 */
public class AdapterChat extends RecyclerView.Adapter<AdapterChat.RecyclerViewHolders>
        implements ExoPlayer.EventListener, SeekBar.OnSeekBarChangeListener {

    private static final int VIEW_TYPE_LEFT = 0;
    private static final int VIEW_TYPE_RIGHT = 1;

    private Context context;
    private ArrayList<ModelChat> data;
    private AlertDialog dialogPreviewImage, dialogPreviewAudio;
    private TrackSelector mTrackSelector;
    private SimpleExoPlayer exoPlayer;
    private SimpleExoPlayerView simpleExoPlayerView;
    private long mPosition;
    private ProgressBar progressBarBuffering;
    private String mVideoUrl = "";
    private boolean isInternetAvailable = true;
    private MediaSource videoSource = null;
    private MediaPlayer mediaPlayer;
    private SeekBar mSeekBar;
    private Handler mHandler = new Handler();
    private Button btnImageClose;
    private ImageView img_audio;
    private ProgressBar progressBarAudioBuffering;
    private CustomTextView tvAudioTimeCurrentDuration, tvAudioTimeTotalDuration;
    /**
     * Background Runnable thread for update music playing SeekBar
     */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mediaPlayer.getDuration();
            long currentDuration = mediaPlayer.getCurrentPosition();

            // Updating progress bar
            int progress = getProgressPercentage(currentDuration, totalDuration);
            LogUtils.LOGE("Progress", "" + progress);
            if (progress < 0) {
                mSeekBar.setProgress(0);
            } else {
                mSeekBar.setProgress(progress);
            }
            tvAudioTimeTotalDuration.setText(milliSecondsToTimer(totalDuration));
            tvAudioTimeCurrentDuration.setText(milliSecondsToTimer(currentDuration));
            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };

    public AdapterChat(Context context, ArrayList<ModelChat> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).isSender() ? VIEW_TYPE_RIGHT : VIEW_TYPE_LEFT;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView;
        if (viewType == VIEW_TYPE_LEFT) {
            layoutView = View.inflate(parent.getContext(), R.layout.item_chat_left2, null);
        } else {
            layoutView = View.inflate(parent.getContext(), R.layout.item_chat_right2, null);
        }
        return new RecyclerViewHolders(context, layoutView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        ModelChat modelChat = data.get(position);
        bindViewHolderData(holder, modelChat, position);
        LogUtils.LOGE("IS_SENDER", String.valueOf(data.get(position).isSender()));
        if (modelChat.getType().equals(XmppConstants.CHAT_TYPE.CHAT_AUDIO)) {
            if (modelChat.isPlaying()) {
                holder.icPlayDownload.setImageResource(R.drawable.exo_controls_pause);
            } else {
                holder.icPlayDownload.setImageResource(R.drawable.ic_caret);
            }
        }
    }

    /**
     * Set View holder data as per message Type
     *
     * @param holder    : View Holder object
     * @param modelChat : chat data model
     * @param position  : View Holder Position
     */
    private void bindViewHolderData(final RecyclerViewHolders holder, final ModelChat modelChat, final int position) {
        // Time and message status
        holder.tvMsgTime.setText(String.valueOf(DateTimeUtils.parseChatDate(modelChat.getTime(), "h:mm a")));
        //holder.tvChatMessageStatus.setText(modelChat.getStatus());
        holder.ivAttachImage.setOnClickListener(null);//By default on click null
        holder.cvChatAudio.setOnClickListener(null);// By default on click null
        holder.icPlayDownload.setOnClickListener(null);// By default on click null

        holder.cvChatText.setVisibility(View.GONE);
        holder.cvChatImage.setVisibility(View.GONE);
        holder.cvChatAudio.setVisibility(View.GONE);
        holder.cvChatVideo.setVisibility(View.GONE);

        switch (modelChat.getType()) {
            case XmppConstants.CHAT_TYPE.CHAT_TEXT: // TEXT
                holder.cvChatText.setVisibility(View.VISIBLE);
                holder.tvChatText.setText(modelChat.getValue());
                break;
            case XmppConstants.CHAT_TYPE.CHAT_IMAGE: // IMAGE
                holder.cvChatImage.setVisibility(View.VISIBLE);
                holder.ivAttachImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openPreviewDialog(modelChat); // Image Preview on click
                    }
                });
                holder.progressBar.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(modelChat.getThumb())
                        .placeholder(R.drawable.ic_place_holder)
                        .error(R.drawable.ic_place_holder)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                holder.progressBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                holder.progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(holder.ivAttachImage);
                break;
            case XmppConstants.CHAT_TYPE.CHAT_VIDEO: // VIDEO
                holder.cvChatVideo.setVisibility(View.VISIBLE);
                holder.ivPlayVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openPreviewDialog(modelChat); // Video Preview on click
                    }
                });
                holder.progressBarVideo.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(modelChat.getThumb()) // If video then display video thumb
                        .placeholder(R.drawable.ic_place_holder)
                        .error(R.drawable.ic_place_holder)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                holder.progressBarVideo.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                holder.progressBarVideo.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(holder.ivAttachVideo);
                break;
            case XmppConstants.CHAT_TYPE.CHAT_AUDIO: // AUDIO
                holder.cvChatAudio.setVisibility(View.VISIBLE);
                holder.cvChatAudio.setTag(position);
                holder.icPlayDownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startMediaPlayer(modelChat.getValue());
                    }
                });
                break;

            /*case XmppConstants.CHAT_TYPE.CHAT_LOCATION:
                //holder.layoutImage.setVisibility(View.VISIBLE);
                holder.progressBar.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(modelChat.getValue()) // LocationMap Image
                        .placeholder(R.drawable.ic_place_holder)
                        .error(R.drawable.ic_place_holder)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                holder.progressBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                holder.progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(holder.imgFile);
                holder.layoutImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            String uriBegin = "geo:" + modelChat.getValue();
                            String query = modelChat.getValue() + "( )";
                            String encodedQuery = Uri.encode(query);
                            String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                            Intent intent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(uriString));
                            context.startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;*/

        }

    }

    /**
     * Reset
     */
    private void clearAllPlayingAudio() {
        for (ModelChat chat : data) {
            chat.setPlaying(false);
        }
    }

    /**
     * Open Preview dialog to display image and video
     *
     * @param mediaDetails
     */
    private void openPreviewDialog(ModelChat mediaDetails) {
        dialogPreviewImage = new AlertDialog.Builder(context, R.style.StyleDialog).create();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.dialog_image_preview, null);
        dialogPreviewImage.setView(view);
        dialogPreviewImage.setCancelable(true);
        final ZoomableImageView orders_bid_image = ButterKnife.findById(view, R.id.ivPreviewImage);
        ImageView btnImageClose = ButterKnife.findById(view, R.id.btnImageClose);
        simpleExoPlayerView = ButterKnife.findById(view, R.id.video_preview);
        progressBarBuffering = ButterKnife.findById(view, R.id.progressBarBuffering);

        if (mediaDetails.getType().equals(XmppConstants.CHAT_TYPE.CHAT_VIDEO)) { // If video then play video else display image preview
            orders_bid_image.setVisibility(View.GONE);
            simpleExoPlayerView.setVisibility(View.VISIBLE);
            mVideoUrl = mediaDetails.getValue();
            previewVideo();
        } else {
            progressBarBuffering.setVisibility(View.VISIBLE);
            simpleExoPlayerView.setVisibility(View.GONE);
            orders_bid_image.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(mediaDetails.getValue())
                    .dontAnimate()
                    .placeholder(R.drawable.ic_place_holder)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            progressBarBuffering.setVisibility(View.GONE);
                            showToast(context.getString(R.string.error_something_went_wrong));
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            progressBarBuffering.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .error(R.drawable.ic_place_holder)
                    .into(orders_bid_image);


        }
        dialogPreviewImage.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPreviewImage.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialogPreviewImage.show();
        btnImageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //releasePlayer();
                dialogPreviewImage.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addItem(ModelChat modelChat) {
        data.add(data.size(), modelChat);
        notifyItemInserted(data.size());
        //notifyDataSetChanged();
    }

    public void addItems(List<ModelChat> dataList) {
        data.clear();
        data.addAll(dataList);
        notifyDataSetChanged();
    }

    private void previewVideo() {
        initializePlayer();
        preparePlayer(Uri.parse(mVideoUrl));

    }

    /**
     * init exo player to view video
     */
    private void initializePlayer() {
        simpleExoPlayerView.setUseController(true);

        simpleExoPlayerView.requestFocus();

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
        mTrackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        // Create a default LoadControl
        LoadControl loadControl = new DefaultLoadControl();
        exoPlayer = ExoPlayerFactory.newSimpleInstance(context, mTrackSelector, loadControl);

        exoPlayer.addListener(this);
        exoPlayer.setPlayWhenReady(true);
    }

    // prepare the player
    private void preparePlayer(Uri uri) {
        // Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
// Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, context.getString(R.string.app_name)), bandwidthMeter);
// Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
// This is the MediaSource representing the media to be played.
        videoSource = new ExtractorMediaSource(uri,
                dataSourceFactory, extractorsFactory, null, null);
// Prepare the player with the source.
        exoPlayer.prepare(videoSource);
        simpleExoPlayerView.setPlayer(exoPlayer);
        exoPlayer.seekTo(mPosition);
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
            mTrackSelector = null;
        }
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
        LogUtils.LOGE("STATE-->", String.valueOf(playbackState));
        if (playbackState == ExoPlayer.STATE_BUFFERING) {
            progressBarBuffering.setVisibility(View.VISIBLE);
        } else {
            progressBarBuffering.setVisibility(View.GONE);
        }

        if (playbackState == ExoPlayer.STATE_ENDED) {
            //releasePlayer();
            exoPlayer.stop();
            exoPlayer.seekTo(0L);
            exoPlayer.prepare(videoSource);
            exoPlayer.setPlayWhenReady(false);
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        if (!com.common.utils.NetworkUtils.checkNetwork(context)) {
            if (isInternetAvailable) {
                showToast(context.getString(R.string.error_network_connectivity));
                isInternetAvailable = false;
                return;
            }
        }

        if (exoPlayer != null) {
            mPosition = exoPlayer.getCurrentPosition();
        }
        LogUtils.LOGE("error", error.toString());
        String errorString = null;
        if (error.type == ExoPlaybackException.TYPE_RENDERER) {
            Exception cause = error.getRendererException();
            if (cause instanceof MediaCodecRenderer.DecoderInitializationException) {
                // Special case for decoder initialization failures.
                MediaCodecRenderer.DecoderInitializationException decoderInitializationException =
                        (MediaCodecRenderer.DecoderInitializationException) cause;
                if (decoderInitializationException.decoderName == null) {
                    if (decoderInitializationException.getCause() instanceof MediaCodecUtil.DecoderQueryException) {
                        errorString = context.getString(R.string.error_querying_decoders);
                    } else if (decoderInitializationException.secureDecoderRequired) {
                        errorString = context.getString(R.string.error_no_secure_decoder,
                                decoderInitializationException.mimeType);
                    } else {
                        errorString = context.getString(R.string.error_no_decoder,
                                decoderInitializationException.mimeType);
                    }
                } else {
                    errorString = context.getString(R.string.error_instantiating_decoder,
                            decoderInitializationException.decoderName);
                }
            }
        }
        if (errorString != null) {
            showToast(errorString);
        }

        if (com.common.utils.NetworkUtils.checkNetwork(context)) {
            releasePlayer();
            initializePlayer();
            preparePlayer(Uri.parse(mVideoUrl));
        }
    }

    private void showToast(String errorString) {
        Toast.makeText(context, errorString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPositionDiscontinuity() {

    }

    /**
     * @param url    : Source URL
     * @param holder
     */
    private void startMediaPlayer(String url, final RecyclerViewHolders holder) {

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        //this.mSeekBar = null;

        try {
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(url);
            if (mSeekBar != null) {
                mSeekBar.setProgress(0);
                mSeekBar.setMax(100);
                mSeekBar.setOnSeekBarChangeListener(this);
            }
            final int pos = holder.getAdapterPosition();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    notifyItemChanged(pos);
                    mediaPlayer.start();
                    updateProgressBar();
                }
            });

            LogUtils.LOGE("HOLDER_CLICK@", String.valueOf(pos));
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    notifyItemChanged(pos);
                    mSeekBar.setProgress(0);
                    mHandler.removeCallbacks(mUpdateTimeTask);

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    /**
     * When user starts moving the progress handler
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // remove message Handler from updating progress bar
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    /**
     * When user stops moving the progress hanlder
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mediaPlayer.getDuration();
        int currentPosition = progressToTimer(seekBar.getProgress(), totalDuration);

        // forward or backward to certain seconds
        mediaPlayer.seekTo(currentPosition);

        // update timer progress again
        updateProgressBar();
    }

    /**
     * Function to change progress to timer
     *
     * @param progress      -
     * @param totalDuration returns current duration in milliseconds
     */
    public int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }

    /**
     * Function to get Progress percentage
     *
     * @param currentDuration
     * @param totalDuration
     */
    public int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        // calculating percentage
        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        // return percentage
        return percentage.intValue();
    }

    private void stopMediaPlayer() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     * @param url : Source URL
     */
    private void startMediaPlayer(String url) {

        if (dialogPreviewAudio == null) {
            dialogPreviewAudio = new AlertDialog.Builder(context, R.style.DialogStyleChatPreview).create();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.dialog_audio_preview, null);
            dialogPreviewAudio.setView(view);
            dialogPreviewAudio.setCancelable(false);
            mSeekBar = ButterKnife.findById(view, R.id.seekBar);
            btnImageClose = ButterKnife.findById(view, R.id.btnImageClose);
            img_audio = ButterKnife.findById(view, R.id.img_audio);
            progressBarAudioBuffering = ButterKnife.findById(view, R.id.progressBarBuffering);
            tvAudioTimeCurrentDuration = ButterKnife.findById(view, R.id.tvAudioTimeCurrentDuration);
            tvAudioTimeTotalDuration = ButterKnife.findById(view, R.id.tvAudioTimeTotalDuration);

        }
        tvAudioTimeCurrentDuration.setText(context.getString(R.string.str_init_time));
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        try {
            img_audio.setTag(0);
            img_audio.setEnabled(false);
            progressBarAudioBuffering.setVisibility(View.VISIBLE);
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(url);
            if (mSeekBar != null) {
                mSeekBar.setProgress(0);
                mSeekBar.setMax(100);
                mSeekBar.setOnSeekBarChangeListener(this);
            }
            //final int pos = holder.getAdapterPosition();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    //notifyItemChanged(pos);
                    img_audio.setTag(1);
                    img_audio.setEnabled(true);
                    img_audio.setImageResource(android.R.drawable.ic_media_pause);
                    progressBarAudioBuffering.setVisibility(View.GONE);
                    mediaPlayer.start();
                    updateProgressBar();
                }
            });

            mediaPlayer.prepareAsync();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    //notifyItemChanged(pos);
                    tvAudioTimeCurrentDuration.setText(context.getString(R.string.str_init_time));
                    img_audio.setTag(0);
                    img_audio.setEnabled(true);
                    progressBarAudioBuffering.setVisibility(View.GONE);
                    img_audio.setImageResource(android.R.drawable.ic_media_play);
                    mSeekBar.setProgress(0);
                    //mediaPlayer.stop();
                    mHandler.removeCallbacks(mUpdateTimeTask);

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnImageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPreviewAudio.dismiss();
                progressBarAudioBuffering.setVisibility(View.GONE);
                img_audio.setImageResource(android.R.drawable.ic_media_play);
                mSeekBar.setProgress(0);
                mHandler.removeCallbacks(mUpdateTimeTask);
                releaseMediaPlayer();
            }
        });

        img_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((int) img_audio.getTag() == 1) {
                    img_audio.setTag(0);
                    mHandler.removeCallbacks(mUpdateTimeTask);
                    mediaPlayer.pause();
                    img_audio.setImageResource(android.R.drawable.ic_media_play);

                } else {
                    img_audio.setTag(1);
                    mHandler.removeCallbacks(mUpdateTimeTask);
                    int totalDuration = mediaPlayer.getDuration();
                    int currentPosition = progressToTimer(mSeekBar.getProgress(), totalDuration);
                    // forward or backward to certain seconds
                    if (currentPosition < 0) {
                        mediaPlayer.seekTo(currentPosition);
                    }
                    {
                        mediaPlayer.seekTo(currentPosition);
                    }
                    mediaPlayer.start();
                    img_audio.setImageResource(android.R.drawable.ic_media_pause);
                    // update timer progress again
                    updateProgressBar();
                }
            }
        });

        dialogPreviewAudio.show();
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_chat_text)
        CustomTextView tvChatText;
        @BindView(R.id.cv_chat_text)
        CardView cvChatText;
        @BindView(R.id.iv_attach_image)
        AppCompatImageView ivAttachImage;
        @BindView(R.id.cv_chat_image)
        CardView cvChatImage;
        @BindView(R.id.iv_attach_video)
        AppCompatImageView ivAttachVideo;
        @BindView(R.id.iv_play_video)
        AppCompatImageView ivPlayVideo;
        @BindView(R.id.cv_chat_video)
        CardView cvChatVideo;
        @BindView(R.id.ic_play_download)
        ImageView icPlayDownload;
        @BindView(R.id.progress_bar_chat)
        ProgressBar progressBarChat;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @BindView(R.id.progressBarVideo)
        ProgressBar progressBarVideo;
        @BindView(R.id.ic_red_headphone)
        ImageView icRedHeadphone;
        @BindView(R.id.tv_audio_mb)
        CustomTextView tvAudioMb;
        @BindView(R.id.cv_chat_audio)
        CardView cvChatAudio;
        @BindView(R.id.tv_msg_time)
        CustomTextView tvMsgTime;


        private Context mContext;


        public RecyclerViewHolders(final Context context, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mContext = context;
        }

        @Override
        public void onClick(View v) {

        }
    }

    public String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString;

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }
}
